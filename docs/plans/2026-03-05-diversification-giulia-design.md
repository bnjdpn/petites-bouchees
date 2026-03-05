# Design - Diversification de Giulia

## Contexte
App Android pour suivre la diversification alimentaire de Giulia (5-6 mois).
Equivalent gratuit de "Cuisinez pour bebe". Tout local, pas de serveur.

## Tech Stack
- Kotlin + Jetpack Compose
- Room (local DB)
- Material 3
- MVVM architecture

## Ecrans principaux (Bottom Navigation)

### 1. Aliments
- Liste par categorie : Legumes, Fruits, Feculents, Proteines, Produits laitiers, Matieres grasses, Epices/Aromates
- Filtre par age : 4m, 6m, 8m, 12m+
- Filtre par statut : tous / testes / non testes
- Aliments au-dela de l'age configure sont affiches mais grises
- Chaque item montre : nom, age recommande, icone statut (teste/non teste), indicateur reaction

### 2. Journal
- Historique chronologique des introductions
- Chaque entree : aliment, date, reactions (aime/neutre/pas aime, digestif, allergique), notes
- Tri par date decroissante

### 3. Progression
- Pourcentage global d'aliments testes
- Repartition par categorie (barres de progression)
- Nombre d'aliments aimes / neutres / pas aimes
- Alertes reactions (liste des aliments avec reactions allergiques ou digestives)

## Fiche aliment (ecran detail)
- Nom, categorie, age recommande
- Statut : non teste / teste (avec date de premiere introduction)
- Reactions : aime / neutre / pas aime
- Checkboxes : probleme digestif, reaction allergique
- Champ notes libres
- Bouton sauvegarder

## Modele de donnees

### Table Food (pre-remplie)
- id: Int (PK)
- name: String
- category: String (enum)
- recommendedAgeMonths: Int (4, 6, 8, 12)
- notes: String? (ex: "cuit, pele, epepine" pour la tomate)

### Table FoodEntry (saisie utilisateur)
- id: Int (PK)
- foodId: Int (FK -> Food)
- dateIntroduced: Long (timestamp)
- reaction: String (enum: LIKED, NEUTRAL, DISLIKED)
- hasDigestiveIssue: Boolean
- hasAllergicReaction: Boolean
- notes: String?

### Table AppSettings
- key: String (PK)
- value: String
- (pour stocker l'age de bebe, preferences)

## Design visuel
- Material 3 avec palette pastel douce
- Couleurs par categorie : vert (legumes), orange (fruits), beige (feculents), rouge (proteines), bleu (laitiers), jaune (matieres grasses), violet (epices)
- Chips pour filtres d'age
- Cards arrondies pour les items
- ~120 aliments pre-remplis au premier lancement

## Base de donnees alimentaire
Voir memory/food-data.md pour la liste complete des aliments par categorie et age.
