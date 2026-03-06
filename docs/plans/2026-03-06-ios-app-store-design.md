# Petites Bouchees iOS - Design Document

## Overview

Port the Android "Petites Bouchees" app (baby food diversification tracker) to iOS for App Store deployment. All UI in French.

## Technical Stack

- Swift 6, SWIFT_STRICT_CONCURRENCY: complete
- SwiftUI 100%, MVVM + Services, @Observable @MainActor ViewModels
- SwiftData for persistence (no CloudKit, local only)
- XcodeGen (project.yml as source of truth, .xcodeproj gitignored)
- Fastlane for screenshots + App Store deployment
- iPhone only, iOS 17.0+, FR only
- Zero external dependencies
- Bundle ID: com.bnjdpn.PetitesBouchees
- Team ID: 767SX34A7Z

## Data Model

### Food (@Model)

| Property | Type | Notes |
|----------|------|-------|
| name | String | |
| category | FoodCategory | enum, 7 values |
| recommendedAgeMonths | Int | |
| notes | String? | Optional preparation notes |

### FoodEntry (@Model)

| Property | Type | Notes |
|----------|------|-------|
| food | Food | SwiftData relationship |
| dateIntroduced | Date | |
| reaction | Reaction | enum, 3 values |
| hasDigestiveIssue | Bool | default false |
| hasAllergicReaction | Bool | default false |
| notes | String? | |

### FoodCategory (enum, 7 values)

| Case | Display Name | Emoji | Color |
|------|-------------|-------|-------|
| legumes | Legumes | vegetable | #4CAF50 |
| fruits | Fruits | apple | #FF9800 |
| feculents | Feculents | bread | #D4A574 |
| proteines | Proteines | poultry | #E57373 |
| produitsLaitiers | Produits laitiers | cheese | #64B5F6 |
| matieresGrasses | Matieres grasses | olive | #FFD54F |
| epicesAromates | Epices & Aromates | herb | #BA68C8 |

### Reaction (enum, 3 values)

| Case | Display Name | Emoji | Color |
|------|-------------|-------|-------|
| liked | Aime | heart | #66BB6A |
| neutral | Neutre | neutral face | #FFCA28 |
| disliked | Pas aime | no gesture | #EF5350 |

### Prepopulated Data

230+ foods organized by age milestones (4m, 6m, 8m, 12m) with French names and optional preparation notes. Seeded on first launch via SeedDataService.

## Screens

### 1. Aliments (Foods)

- Category filter chips (7, color-coded)
- Age filter chips (4m, 6m, 8m, 9m, 12m+)
- Tested filter chips (Tous, Testes, A tester)
- List grouped by category with section headers
- Swipe-to-delete with confirmation
- FAB/toolbar button to add custom food via sheet
- Each row: colored category bar, name, notes, age badge, test status indicator

### 2. Journal

- Entries grouped by date (sticky headers, French format "d MMMM yyyy")
- Each card: category color dot, food name, category+emoji, reaction emoji, warning icons
- Empty state message
- Tap navigates to food detail

### 3. Progression (Progress)

- Circular progress arc (animated) with percentage and count
- Per-category horizontal progress bars (animated)
- 3 reaction count cards (liked/neutral/disliked)
- Alerts section for foods with digestive/allergic issues

### 4. Detail Aliment (Food Detail)

- Food info: category chip, recommended age, preparation notes
- DatePicker for introduction date
- Reaction selector (3 buttons)
- Issue checkboxes (digestive, allergic)
- Notes text field
- Save button + Delete entry button (if already tested)

## Navigation

TabView with 3 tabs:
1. Aliments (fork.knife icon)
2. Journal (book icon)
3. Progression (chart.bar icon)

NavigationStack within each tab. Food detail pushed from Aliments or Journal.

## Theme

Sage green primary (#4A7C59), warm peach secondary (#B87351).
Background: #FCFDF7, Surface variant: #E0E4DA.
Category-specific colors as listed above.

## Project Structure

```
PetitesBouchees/
  App/
    PetitesBoucheesApp.swift
    ContentView.swift
  Models/
    Food.swift
    FoodEntry.swift
    FoodCategory.swift
    Reaction.swift
  ViewModels/
    FoodsViewModel.swift
    JournalViewModel.swift
    ProgressViewModel.swift
    FoodDetailViewModel.swift
  Views/
    Foods/
      FoodsScreen.swift
      FoodRowView.swift
      AddFoodSheet.swift
    Journal/
      JournalScreen.swift
      JournalEntryRow.swift
    Progress/
      ProgressScreen.swift
      CircularProgressView.swift
      CategoryProgressBar.swift
    Detail/
      FoodDetailScreen.swift
  Services/
    SeedDataService.swift
  Extensions/
    Color+Theme.swift
  Resources/
    Assets.xcassets
    PrivacyInfo.xcprivacy
  PetitesBouchees.entitlements
```

## Deployment

- Fastlane: Fastfile, Appfile, Matchfile, Snapfile, metadata/
- Metadata FR-FR only (name, subtitle, description, keywords, promo text, release notes)
- Screenshots via UI tests on iPhone 17 Pro Max + iPhone 17 Pro
- GitHub Actions CI (build + test on push)
- Claude Code hooks (5 standard) + commands (/verify, /push, /release)

## ASO Strategy (FR)

- Name: "Petites Bouchees" (30 chars max)
- Subtitle: "Diversification alimentaire" (30 chars max)
- Keywords: diversification, bebe, aliments, allergies, introduction, nourriture, repas, suivi, journal
- Description: Focus on tracking first foods, allergies, reactions, progress by category
- Category: Health & Fitness or Food & Drink
