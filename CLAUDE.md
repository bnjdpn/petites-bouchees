# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project

Android app for tracking baby food diversification (introduction of solid foods). All UI text is in French.

Package: `com.petitesbouchees.app`

## Build Commands

**Prerequisites:** Java 17 must be in PATH (not available by default on this machine):
```bash
export JAVA_HOME=/opt/homebrew/opt/openjdk@17
export PATH="$JAVA_HOME/bin:$PATH"
```

```bash
./gradlew assembleDebug          # Build debug APK
./gradlew installDebug           # Build and install on connected device/emulator
./gradlew kspDebugKotlin         # Run KSP annotation processing (Room code generation)
```

Debug APK output: `app/build/outputs/apk/debug/app-debug.apk`

No tests are configured yet. No linter is configured.

## Architecture

Single-module Android app (`app/`) using MVVM with Jetpack Compose, Room, and manual dependency injection.

### Dependency Graph

```
MainActivity -> AppNavigation (Scaffold + NavHost)
  -> FoodsScreen / JournalScreen / ProgressScreen / FoodDetailScreen
     -> *ViewModel (ViewModelProvider.Factory pattern)
        -> FoodRepository
           -> FoodDao / FoodEntryDao
              -> AppDatabase (Room, singleton)
```

### Key Patterns

- **No DI framework**: `DiversificationApp` (Application subclass) holds a lazy `FoodRepository`. Screens get it via `LocalContext.current.applicationContext as DiversificationApp`.
- **ViewModel factories**: Each ViewModel has a nested `Factory` class that takes `FoodRepository` (and sometimes extra args like `foodId`).
- **Room DB prepopulation**: `PrepopulateData.allFoods` is inserted via `AppDatabase.Callback.onCreate`. The database uses `fallbackToDestructiveMigration()` — bumping the version wipes data.
- **Navigation**: Sealed class `Screen` defines routes. Bottom bar shows on Foods/Journal/Progress. FoodDetail takes a `foodId` int argument.

### Data Model

Two Room entities:
- `Food` — id, name, category (enum `FoodCategory`), recommendedAgeMonths, notes
- `FoodEntry` — id, foodId (FK to Food), dateIntroduced, reaction (enum `Reaction`), hasDigestiveIssue, hasAllergicReaction, notes

`FoodCategory` has 7 values: LEGUMES, FRUITS, FECULENTS, PROTEINES, PRODUITS_LAITIERS, MATIERES_GRASSES, EPICES_AROMATES. Each has a French `displayName` and emoji.

### Tech Versions

- Kotlin 2.1.0, AGP 8.7.3, compileSdk/targetSdk 36, minSdk 26
- Compose BOM 2024.12.01, Room 2.6.1, Navigation Compose 2.8.5
- KSP for Room annotation processing
- JVM target 17
