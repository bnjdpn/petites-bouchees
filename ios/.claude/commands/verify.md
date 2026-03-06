# Project Verification

Build, test et verification rapide du projet.

## Usage

```
/verify
/verify quick       → Build sim uniquement (pas de tests)
/verify pre-release → Build sim + device + tests
```

## Workflow

### Etape 1 - Regeneration projet

```bash
cd ios && xcodegen generate
```

### Etape 2 - Build + Tests

1. Compiler simulator :
   ```bash
   xcodebuild -scheme PetitesBouchees -destination 'platform=iOS Simulator,name=iPhone 17 Pro' build 2>&1 | tail -5
   ```

2. Compiler device (si pre-release, detecte erreurs `#if targetEnvironment`) :
   ```bash
   xcodebuild -scheme PetitesBouchees -destination 'generic/platform=iOS' build CODE_SIGN_IDENTITY="" CODE_SIGNING_REQUIRED=NO 2>&1 | tail -5
   ```

3. Lancer les tests (sauf mode quick) :
   ```bash
   xcodebuild test -scheme PetitesBouchees -destination 'platform=iOS Simulator,name=iPhone 17 Pro' 2>&1 | tail -20
   ```

### Etape 3 - Rapport

```
BUILD SIM:    [OK/FAILED]
BUILD DEVICE: [OK/FAILED/SKIPPED]
TESTS:        X passed, Y failed [ou SKIPPED]
VERDICT:      [PRET / BLOQUE]
```

Si BLOQUE, lister les erreurs.

## Notes

- Utilise xcodebuild directement (incremental, pas de clean build)
- Le build device est optionnel sauf en pre-release
- Mode `quick` : build sim uniquement, pour verifier que ca compile
