# Skill /release - Deploiement App Store

Workflow 100% automatise pour publier sur l'App Store.

## Usage

```
/release         → Release complete (screenshots + metadata + build)
/release beta    → TestFlight uniquement (rapide)
/release quick   → App Store sans screenshots (metadata + build)
```

---

## Workflow Standard `/release`

### Etape 0 : Gate de verification (OBLIGATOIRE)

Executer `/verify pre-release` et attendre que le verdict soit PRET ou PRET (warnings).

**Si BLOQUE : STOP - corriger les problemes avant de continuer.**

### Etape 1 : Validation des prerequis

```bash
cd ios
test -f fastlane/asc_api_key.json && echo "API Key OK" || echo "API Key manquante - STOP"
test -z "$(git status --porcelain)" && echo "Repo propre OK" || echo "Changements non commites - STOP"
```

**IMPORTANT : Les deux conditions doivent etre OK. Si repo non propre, demander a l'utilisateur de committer ou stash avant de continuer.**

### Etape 2 : Analyser les nouveautes

1. Lire la version dans project.yml :
   ```bash
   grep -E "MARKETING_VERSION|CURRENT_PROJECT_VERSION" ios/project.yml
   ```

2. Lister les commits recents :
   ```bash
   git log --oneline -15
   ```

### Etape 3 : Mettre a jour les release notes

**IMPORTANT : Apple n'accepte PAS les emojis dans les release notes.**

Modifier `fastlane/metadata/fr-FR/release_notes.txt` :

```
Nouvelle fonctionnalite
Description courte de la nouveaute.

Performances optimisees
Autre amelioration.
```

### Etape 4 : Valider les limites metadata

| Champ | Limite |
|-------|--------|
| name | 30 car |
| subtitle | 30 car |
| promotional_text | 170 car |
| description | 4000 car |
| keywords | 100 car |
| release_notes | 4000 car |

```bash
wc -c ios/fastlane/metadata/fr-FR/*.txt
```

### Etape 5 : Validation des screenshots (OBLIGATOIRE)

**AVANT tout upload, verifier les screenshots existants pour eviter doublons et erreurs.**

#### 5.1 Verifier la configuration Snapfile
```bash
cat ios/fastlane/Snapfile | grep -A 10 "devices("
```

#### 5.2 Compter les screenshots par device/langue
```bash
echo "=== Screenshots par dossier ===" && \
echo "fr-FR: $(ls ios/app_store_screenshots/fr-FR/*.png 2>/dev/null | wc -l) fichiers"
```

#### 5.3 Detecter les doublons par taille de fichier (CRITIQUE)
```bash
echo "=== Detection doublons (meme taille = suspect) ===" && \
echo "--- fr-FR ---" && \
ls -la ios/app_store_screenshots/fr-FR/*.png 2>/dev/null | awk '{print $5, $9}' | sort
```

**REGLE : Si plusieurs screenshots ont EXACTEMENT la meme taille en octets, ce sont probablement des doublons !**

#### 5.4 Verification visuelle OBLIGATOIRE
**Lire chaque screenshot avec l'outil Read pour verifier visuellement que chaque capture est unique et correspond a un ecran different de l'app.**

#### 5.5 Afficher le rapport de validation
```
VALIDATION SCREENSHOTS

Devices configures : X
Total fichiers : X (attendu: Y)

Verification tailles fichiers : [liste avec statut unique/doublon]

Verification visuelle : [OK tous differents / DOUBLONS DETECTES]

[PRET pour upload / BLOQUE - corriger les tests UI]
```

#### 5.6 Si doublons detectes : STOP
- **NE PAS UPLOADER** tant que les screenshots ne sont pas tous uniques
- Corriger les tests UI pour naviguer correctement entre les ecrans
- Regenerer avec `bundle exec fastlane screenshots`
- Re-verifier avant upload

### Etape 6 : Demander confirmation

```
PRET POUR LE DEPLOIEMENT

Version : X.Y
Nouveautes mises a jour
Screenshots : X screenshots valides (pas de doublons)

Cette action va :
1. Incrementer le build number
2. Compiler en Release
3. Uploader screenshots + build + metadata

Confirmer le deploiement ? (oui/non)
```

### Etape 7 : Lancer le deploiement

```bash
cd ios && bundle exec fastlane release
```

Le workflow Fastlane fait tout automatiquement :
- Upload screenshots vers App Store Connect
- Build Release signe
- Upload build + metadata

### Etape 8 : Resultat

```
DEPLOIEMENT REUSSI

PetitesBouchees vX.Y (build Z)
https://appstoreconnect.apple.com/apps

Prochaines etapes :
1. Verifier sur App Store Connect
2. Cliquer "Ajouter pour verification"
3. Attendre la review Apple (24-48h)
```

---

## Mode `/release beta`

Pour TestFlight uniquement (pas de metadata, pas de screenshots).

Execute `/verify quick` avant le build, puis :

```bash
cd ios && bundle exec fastlane beta
```

---

## Mode `/release quick`

Release App Store sans regenerer les screenshots.

Execute `/verify pre-release` avant le build, puis :

```bash
cd ios && bundle exec fastlane release_quick
```

---

## Lanes Fastlane disponibles

| Lane | Description |
|------|-------------|
| `release` | Complet : screenshots + build + metadata |
| `release_quick` | Build + metadata (sans screenshots) |
| `beta` | TestFlight uniquement |
| `screenshots` | Generer screenshots seulement |
| `upload_screenshots` | Uploader screenshots existants |
| `metadata` | Uploader metadata seulement |

---

## Screenshots

Les screenshots sont generes automatiquement par Fastlane (lane `screenshots`).

**Devices configures (3 essentiels) :**
- iPhone 17 Pro Max (6.9") - requis, plus grand iPhone
- iPhone 11 Pro Max (6.5") - legacy App Store requirement
- iPad Pro 13-inch (M5) - requis pour iPad

**Langue :** FR-FR

**Output :** `app_store_screenshots/`

Pour generer les screenshots manuellement :
```bash
cd ios && bundle exec fastlane screenshots
```

---

## Troubleshooting

### Erreur "No value found for username"
Le Fastfile utilise maintenant l'API Key automatiquement.

### Emojis refuses dans release notes
Apple n'accepte pas certains emojis. Utiliser du texte simple.

### Screenshots path incorrect
Les screenshots sont dans `../app_store_screenshots/` (un niveau au-dessus de fastlane/).

### Build echoue apres edit project.yml
Toujours regenerer avec `xcodegen generate` apres modification.

### Doublons dans les screenshots
1. Verifier `fastlane/Snapfile` - ne garder que 3 devices essentiels
2. Supprimer `app_store_screenshots/` et regenerer
3. Verifier sur App Store Connect et supprimer manuellement si necessaire

### Erreurs 500 lors de l'upload screenshots
App Store Connect peut etre instable. Les screenshots sont souvent uploades meme si des erreurs 500 apparaissent. Verifier sur App Store Connect avant de re-essayer.
