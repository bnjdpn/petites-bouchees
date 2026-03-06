# Push securise vers main

Effectue un push securise du code local vers la branche main distante.

## Instructions

1. **Analyser les fichiers a committer**
   - Executer `git status` pour voir les fichiers modifies et non-trackes
   - Identifier les fichiers suspects qui ne devraient pas etre committes

2. **Fichiers suspects a detecter** (patterns a verifier) :
   - Dossiers de build : `DerivedData/`, `build/`, `.build/`
   - Artefacts Xcode : `*.xcresult`, `*.xcarchive`, `*.ipa`, `*.dSYM`
   - Settings utilisateur : `xcuserdata/`, `.swiftpm/xcode/`
   - Dependances : `Pods/`, `Carthage/Build/`, `node_modules/`
   - Fichiers temporaires : `*.log`, `*.tmp`, `*.swp`, `*.bak`
   - Fichiers sensibles : `.env`, `*.pem`, `*.p12`, credentials
   - Fichiers volumineux (> 5 MB) : verifier avec `find . -size +5M`

3. **Si des fichiers suspects sont detectes** :
   - Lister les fichiers problematiques a l'utilisateur
   - Proposer d'ajouter les patterns manquants au `.gitignore`
   - Attendre la confirmation de l'utilisateur avant de modifier
   - Ne PAS continuer le push tant que les fichiers ne sont pas geres

4. **Build local avant commit** (OBLIGATOIRE) :
   - Regenerer le projet : `cd ios && xcodegen generate`
   - Compiler simulator :
     ```bash
     xcodebuild -scheme PetitesBouchees -destination 'platform=iOS Simulator,name=iPhone 17 Pro' build 2>&1 | tail -5
     ```
   - Compiler device (detecte erreurs #if targetEnvironment) :
     ```bash
     xcodebuild -scheme PetitesBouchees -destination 'generic/platform=iOS' build CODE_SIGN_IDENTITY="" CODE_SIGNING_REQUIRED=NO 2>&1 | tail -5
     ```
   - Si un build echoue : STOP - afficher les erreurs et ne pas continuer
   - Lancer les tests :
     ```bash
     xcodebuild test -scheme PetitesBouchees -destination 'platform=iOS Simulator,name=iPhone 17 Pro' 2>&1 | tail -20
     ```
   - Si les tests echouent : STOP - afficher les erreurs et ne pas continuer
   - Seulement si builds ET tests passent : continuer vers le commit

5. **Si builds et tests OK** :
   - Analyser les changements avec `git diff --staged` (apres staging) ou `git diff` + `git status`
   - Generer un message de commit concis et descriptif base sur :
     - La nature des changements (feat, fix, refactor, docs, test, chore)
     - Les fichiers modifies et leur contexte
     - Le format : type court en minuscules, description claire
     - Exemples : "feat: add new feature", "fix: correct edge case", "refactor: simplify view model"
   - Stager les fichiers individuellement avec `git add <file1> <file2> ...` (ne PAS utiliser `git add -A`)
   - Executer : `git commit` avec Co-Authored-By: Claude Opus 4.6 <noreply@anthropic.com>
   - Executer : `git push origin main`
   - Confirmer le succes du push avec le message utilise

6. **En cas d'erreur** :
   - Si le build echoue, afficher les erreurs de compilation
   - Si les tests echouent, afficher les tests en echec
   - Si le push echoue, expliquer l'erreur
   - Si c'est un conflit git, suggerer de faire un pull d'abord
