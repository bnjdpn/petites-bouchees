<div align="center">

# 🍼 Petites Bouchées

**Suivez la diversification alimentaire de bébé, simplement.**

[![Android](https://img.shields.io/badge/Android-8.0%2B-3DDC84?logo=android&logoColor=white)](https://developer.android.com)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.1-7F52FF?logo=kotlin&logoColor=white)](https://kotlinlang.org)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-Material%203-4285F4?logo=jetpackcompose&logoColor=white)](https://developer.android.com/jetpack/compose)
[![Licence MIT](https://img.shields.io/badge/Licence-MIT-yellow)](LICENSE)

</div>

---

Application Android gratuite et open source pour accompagner les parents dans l'introduction des premiers aliments solides de bébé. Pas de compte, pas de serveur — toutes les données restent sur votre appareil.

## ✨ Fonctionnalités

| | Fonctionnalité | Description |
|---|---|---|
| 📖 | **Catalogue d'aliments** | 100+ aliments classés par catégorie |
| 📝 | **Suivi des introductions** | Date, réaction de bébé, problèmes digestifs ou allergiques |
| 📅 | **Journal alimentaire** | Historique chronologique de tous les aliments introduits |
| 📊 | **Progression** | Statistiques par catégorie et par tranche d'âge |
| 🔍 | **Fiche détaillée** | Âge recommandé, notes et historique pour chaque aliment |

### Catégories

> 🥦 Légumes · 🍎 Fruits · 🍞 Féculents · 🍗 Protéines · 🧀 Produits laitiers · 🫒 Matières grasses · 🌿 Épices & Aromates

## 📥 Installation

Téléchargez l'APK depuis la [dernière release](https://github.com/bnjdpn/petites-bouchees/releases/latest) et installez-le sur un appareil **Android 8.0+** (SDK 26).

## 🏗️ Stack technique

| Composant | Technologie |
|---|---|
| Langage | Kotlin 2.1 |
| UI | Jetpack Compose + Material 3 |
| Base de données | Room (SQLite locale) |
| Architecture | MVVM |
| Vie privée | 100% hors ligne — aucune donnée ne quitte l'appareil |

## 🔨 Build

```bash
# Java 17 requis
export JAVA_HOME=/opt/homebrew/opt/openjdk@17
export PATH="$JAVA_HOME/bin:$PATH"

./gradlew assembleDebug
```

L'APK est généré dans `app/build/outputs/apk/debug/app-debug.apk`.

## 🤝 Contribuer

Les contributions sont les bienvenues ! N'hésitez pas à ouvrir une issue ou une pull request.

## 📄 Licence

[MIT](LICENSE)

---

<div align="center">
  Fait avec ❤️ pour les jeunes parents
</div>
