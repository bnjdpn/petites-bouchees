# Petites Bouchées 🍼

App Android gratuite et open source pour suivre la diversification alimentaire de bébé.

## Fonctionnalités

- **Catalogue d'aliments** — 100+ aliments classés par catégorie (🥦 Légumes, 🍎 Fruits, 🍞 Féculents, 🍗 Protéines, 🧀 Produits laitiers, 🫒 Matières grasses, 🌿 Épices & Aromates)
- **Suivi des introductions** — enregistrer la date, la réaction de bébé (aimé/pas aimé), les problèmes digestifs ou allergiques
- **Journal alimentaire** — historique chronologique de tous les aliments introduits
- **Progression** — statistiques par catégorie et par tranche d'âge (4, 6, 8, 9, 12 mois+)
- **Fiche détaillée** — âge recommandé, notes et historique pour chaque aliment

## Installation

Télécharger l'APK depuis la [dernière release](https://github.com/bnjdpn/petites-bouchees/releases/latest) et l'installer sur un appareil Android 8.0+ (SDK 26).

## Stack technique

- Kotlin + Jetpack Compose
- Room (base de données locale)
- Material 3
- Architecture MVVM
- Pas de compte, pas de serveur — toutes les données restent sur l'appareil

## Build

```bash
# Java 17 requis
export JAVA_HOME=/opt/homebrew/opt/openjdk@17
export PATH="$JAVA_HOME/bin:$PATH"

./gradlew assembleDebug
```

L'APK est généré dans `app/build/outputs/apk/debug/app-debug.apk`.

## Licence

[MIT](LICENSE)
