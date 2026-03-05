package com.giulia.diversification.data.local

import com.giulia.diversification.data.model.Food
import com.giulia.diversification.data.model.FoodCategory

object PrepopulateData {
    val allFoods = listOf(
        // LEGUMES - 4 mois
        Food(name = "Carotte", category = FoodCategory.LEGUMES, recommendedAgeMonths = 4),
        Food(name = "Courgette", category = FoodCategory.LEGUMES, recommendedAgeMonths = 4),
        Food(name = "Haricot vert", category = FoodCategory.LEGUMES, recommendedAgeMonths = 4),
        Food(name = "Blanc de poireau", category = FoodCategory.LEGUMES, recommendedAgeMonths = 4),
        Food(name = "Potimarron", category = FoodCategory.LEGUMES, recommendedAgeMonths = 4),
        Food(name = "Patate douce", category = FoodCategory.LEGUMES, recommendedAgeMonths = 4),
        Food(name = "Potiron", category = FoodCategory.LEGUMES, recommendedAgeMonths = 4),
        Food(name = "Butternut", category = FoodCategory.LEGUMES, recommendedAgeMonths = 4),
        Food(name = "Panais", category = FoodCategory.LEGUMES, recommendedAgeMonths = 4),
        // LEGUMES - 6 mois
        Food(name = "Brocoli", category = FoodCategory.LEGUMES, recommendedAgeMonths = 6),
        Food(name = "Épinards", category = FoodCategory.LEGUMES, recommendedAgeMonths = 6),
        Food(name = "Petits pois", category = FoodCategory.LEGUMES, recommendedAgeMonths = 6),
        Food(name = "Aubergine", category = FoodCategory.LEGUMES, recommendedAgeMonths = 6),
        Food(name = "Tomate", category = FoodCategory.LEGUMES, recommendedAgeMonths = 6, notes = "Cuite, pelée, épépinée"),
        Food(name = "Artichaut", category = FoodCategory.LEGUMES, recommendedAgeMonths = 6),
        Food(name = "Betterave", category = FoodCategory.LEGUMES, recommendedAgeMonths = 6),
        Food(name = "Fenouil", category = FoodCategory.LEGUMES, recommendedAgeMonths = 6),
        Food(name = "Navet", category = FoodCategory.LEGUMES, recommendedAgeMonths = 6),
        Food(name = "Céleri", category = FoodCategory.LEGUMES, recommendedAgeMonths = 6),
        Food(name = "Avocat", category = FoodCategory.LEGUMES, recommendedAgeMonths = 6),
        Food(name = "Concombre", category = FoodCategory.LEGUMES, recommendedAgeMonths = 6),
        Food(name = "Poivron", category = FoodCategory.LEGUMES, recommendedAgeMonths = 6),
        // LEGUMES - 8 mois
        Food(name = "Chou-fleur", category = FoodCategory.LEGUMES, recommendedAgeMonths = 8),
        Food(name = "Brocoli chinois", category = FoodCategory.LEGUMES, recommendedAgeMonths = 8),
        Food(name = "Chou blanc", category = FoodCategory.LEGUMES, recommendedAgeMonths = 8),
        Food(name = "Champignons", category = FoodCategory.LEGUMES, recommendedAgeMonths = 8),
        Food(name = "Oignon", category = FoodCategory.LEGUMES, recommendedAgeMonths = 8),
        Food(name = "Échalote", category = FoodCategory.LEGUMES, recommendedAgeMonths = 8),
        Food(name = "Ail", category = FoodCategory.LEGUMES, recommendedAgeMonths = 8),

        // FRUITS - 4 mois
        Food(name = "Pomme", category = FoodCategory.FRUITS, recommendedAgeMonths = 4),
        Food(name = "Poire", category = FoodCategory.FRUITS, recommendedAgeMonths = 4),
        Food(name = "Banane", category = FoodCategory.FRUITS, recommendedAgeMonths = 4),
        Food(name = "Pêche", category = FoodCategory.FRUITS, recommendedAgeMonths = 4),
        Food(name = "Abricot", category = FoodCategory.FRUITS, recommendedAgeMonths = 4),
        Food(name = "Coing", category = FoodCategory.FRUITS, recommendedAgeMonths = 4),
        // FRUITS - 6 mois
        Food(name = "Mangue", category = FoodCategory.FRUITS, recommendedAgeMonths = 6),
        Food(name = "Melon", category = FoodCategory.FRUITS, recommendedAgeMonths = 6),
        Food(name = "Pastèque", category = FoodCategory.FRUITS, recommendedAgeMonths = 6),
        Food(name = "Fraise", category = FoodCategory.FRUITS, recommendedAgeMonths = 6),
        Food(name = "Framboise", category = FoodCategory.FRUITS, recommendedAgeMonths = 6),
        Food(name = "Myrtille", category = FoodCategory.FRUITS, recommendedAgeMonths = 6),
        Food(name = "Cerise", category = FoodCategory.FRUITS, recommendedAgeMonths = 6, notes = "Dénoyautée"),
        Food(name = "Prune", category = FoodCategory.FRUITS, recommendedAgeMonths = 6),
        Food(name = "Raisin", category = FoodCategory.FRUITS, recommendedAgeMonths = 6, notes = "Pelé, coupé"),
        Food(name = "Orange", category = FoodCategory.FRUITS, recommendedAgeMonths = 6),
        Food(name = "Clémentine", category = FoodCategory.FRUITS, recommendedAgeMonths = 6),
        Food(name = "Kiwi", category = FoodCategory.FRUITS, recommendedAgeMonths = 6),
        Food(name = "Ananas", category = FoodCategory.FRUITS, recommendedAgeMonths = 6),
        Food(name = "Papaye", category = FoodCategory.FRUITS, recommendedAgeMonths = 6),
        Food(name = "Fruit de la passion", category = FoodCategory.FRUITS, recommendedAgeMonths = 6),
        Food(name = "Nectarine", category = FoodCategory.FRUITS, recommendedAgeMonths = 6),
        Food(name = "Figue", category = FoodCategory.FRUITS, recommendedAgeMonths = 6),
        // FRUITS - 12 mois
        Food(name = "Noix", category = FoodCategory.FRUITS, recommendedAgeMonths = 12, notes = "En poudre ou purée"),
        Food(name = "Noisettes", category = FoodCategory.FRUITS, recommendedAgeMonths = 12, notes = "En poudre ou purée"),
        Food(name = "Amandes", category = FoodCategory.FRUITS, recommendedAgeMonths = 12, notes = "En poudre ou purée"),
        Food(name = "Litchi", category = FoodCategory.FRUITS, recommendedAgeMonths = 12),

        // FECULENTS - 4 mois
        Food(name = "Pomme de terre", category = FoodCategory.FECULENTS, recommendedAgeMonths = 4),
        Food(name = "Céréales infantiles sans gluten", category = FoodCategory.FECULENTS, recommendedAgeMonths = 4, notes = "Riz, maïs"),
        // FECULENTS - 6 mois
        Food(name = "Blé", category = FoodCategory.FECULENTS, recommendedAgeMonths = 6),
        Food(name = "Semoule", category = FoodCategory.FECULENTS, recommendedAgeMonths = 6),
        Food(name = "Orge", category = FoodCategory.FECULENTS, recommendedAgeMonths = 6),
        Food(name = "Épeautre", category = FoodCategory.FECULENTS, recommendedAgeMonths = 6),
        Food(name = "Riz", category = FoodCategory.FECULENTS, recommendedAgeMonths = 6),
        Food(name = "Pâtes fines", category = FoodCategory.FECULENTS, recommendedAgeMonths = 6, notes = "Vermicelles"),
        Food(name = "Flocons d'avoine", category = FoodCategory.FECULENTS, recommendedAgeMonths = 6),
        Food(name = "Tapioca", category = FoodCategory.FECULENTS, recommendedAgeMonths = 6),
        Food(name = "Quinoa", category = FoodCategory.FECULENTS, recommendedAgeMonths = 6),
        Food(name = "Polenta", category = FoodCategory.FECULENTS, recommendedAgeMonths = 6),
        // FECULENTS - 8 mois
        Food(name = "Lentilles corail", category = FoodCategory.FECULENTS, recommendedAgeMonths = 8),
        Food(name = "Pois chiches", category = FoodCategory.FECULENTS, recommendedAgeMonths = 8),
        Food(name = "Haricots blancs", category = FoodCategory.FECULENTS, recommendedAgeMonths = 8),
        Food(name = "Pois cassés", category = FoodCategory.FECULENTS, recommendedAgeMonths = 8),
        Food(name = "Lentilles vertes", category = FoodCategory.FECULENTS, recommendedAgeMonths = 8),
        // FECULENTS - 12 mois
        Food(name = "Pain", category = FoodCategory.FECULENTS, recommendedAgeMonths = 12),
        Food(name = "Biscottes", category = FoodCategory.FECULENTS, recommendedAgeMonths = 12),
        Food(name = "Céréales complètes", category = FoodCategory.FECULENTS, recommendedAgeMonths = 12),

        // PROTEINES - 6 mois
        Food(name = "Poulet", category = FoodCategory.PROTEINES, recommendedAgeMonths = 6),
        Food(name = "Dinde", category = FoodCategory.PROTEINES, recommendedAgeMonths = 6),
        Food(name = "Bœuf", category = FoodCategory.PROTEINES, recommendedAgeMonths = 6),
        Food(name = "Veau", category = FoodCategory.PROTEINES, recommendedAgeMonths = 6),
        Food(name = "Porc", category = FoodCategory.PROTEINES, recommendedAgeMonths = 6),
        Food(name = "Agneau", category = FoodCategory.PROTEINES, recommendedAgeMonths = 6),
        Food(name = "Jambon blanc", category = FoodCategory.PROTEINES, recommendedAgeMonths = 6, notes = "Sans sel ajouté"),
        Food(name = "Cabillaud", category = FoodCategory.PROTEINES, recommendedAgeMonths = 6),
        Food(name = "Colin", category = FoodCategory.PROTEINES, recommendedAgeMonths = 6),
        Food(name = "Merlu", category = FoodCategory.PROTEINES, recommendedAgeMonths = 6),
        Food(name = "Sole", category = FoodCategory.PROTEINES, recommendedAgeMonths = 6),
        Food(name = "Saumon", category = FoodCategory.PROTEINES, recommendedAgeMonths = 6),
        Food(name = "Lieu", category = FoodCategory.PROTEINES, recommendedAgeMonths = 6),
        Food(name = "Bar", category = FoodCategory.PROTEINES, recommendedAgeMonths = 6),
        Food(name = "Dorade", category = FoodCategory.PROTEINES, recommendedAgeMonths = 6),
        Food(name = "Truite", category = FoodCategory.PROTEINES, recommendedAgeMonths = 6),
        Food(name = "Œuf", category = FoodCategory.PROTEINES, recommendedAgeMonths = 6, notes = "Bien cuit, jaune puis blanc"),
        // PROTEINES - 12 mois
        Food(name = "Charcuterie", category = FoodCategory.PROTEINES, recommendedAgeMonths = 12, notes = "Limitée"),
        Food(name = "Fruits de mer", category = FoodCategory.PROTEINES, recommendedAgeMonths = 12, notes = "Cuits"),

        // PRODUITS LAITIERS - 6 mois
        Food(name = "Yaourt nature", category = FoodCategory.PRODUITS_LAITIERS, recommendedAgeMonths = 6),
        Food(name = "Fromage blanc nature", category = FoodCategory.PRODUITS_LAITIERS, recommendedAgeMonths = 6),
        Food(name = "Petit-suisse nature", category = FoodCategory.PRODUITS_LAITIERS, recommendedAgeMonths = 6),
        // PRODUITS LAITIERS - 8 mois
        Food(name = "Gruyère", category = FoodCategory.PRODUITS_LAITIERS, recommendedAgeMonths = 8),
        Food(name = "Emmental", category = FoodCategory.PRODUITS_LAITIERS, recommendedAgeMonths = 8),
        Food(name = "Comté", category = FoodCategory.PRODUITS_LAITIERS, recommendedAgeMonths = 8),
        Food(name = "Parmesan", category = FoodCategory.PRODUITS_LAITIERS, recommendedAgeMonths = 8, notes = "Petites quantités"),
        Food(name = "Brie pasteurisé", category = FoodCategory.PRODUITS_LAITIERS, recommendedAgeMonths = 8),
        // PRODUITS LAITIERS - 12 mois
        Food(name = "Lait de vache entier", category = FoodCategory.PRODUITS_LAITIERS, recommendedAgeMonths = 12),
        Food(name = "Fromages variés", category = FoodCategory.PRODUITS_LAITIERS, recommendedAgeMonths = 12),

        // MATIERES GRASSES - 4 mois
        Food(name = "Huile de colza", category = FoodCategory.MATIERES_GRASSES, recommendedAgeMonths = 4, notes = "1 cuillère à café par repas"),
        Food(name = "Huile d'olive", category = FoodCategory.MATIERES_GRASSES, recommendedAgeMonths = 4, notes = "1 cuillère à café par repas"),
        Food(name = "Huile de noix", category = FoodCategory.MATIERES_GRASSES, recommendedAgeMonths = 4, notes = "1 cuillère à café par repas"),
        Food(name = "Huile de tournesol", category = FoodCategory.MATIERES_GRASSES, recommendedAgeMonths = 4, notes = "1 cuillère à café par repas"),
        Food(name = "Beurre non salé", category = FoodCategory.MATIERES_GRASSES, recommendedAgeMonths = 4),
        Food(name = "Crème fraîche", category = FoodCategory.MATIERES_GRASSES, recommendedAgeMonths = 4),

        // EPICES & AROMATES - 6 mois
        Food(name = "Vanille", category = FoodCategory.EPICES_AROMATES, recommendedAgeMonths = 6),
        Food(name = "Cannelle", category = FoodCategory.EPICES_AROMATES, recommendedAgeMonths = 6),
        Food(name = "Cumin", category = FoodCategory.EPICES_AROMATES, recommendedAgeMonths = 6),
        Food(name = "Basilic", category = FoodCategory.EPICES_AROMATES, recommendedAgeMonths = 6),
        Food(name = "Persil", category = FoodCategory.EPICES_AROMATES, recommendedAgeMonths = 6),
        Food(name = "Ciboulette", category = FoodCategory.EPICES_AROMATES, recommendedAgeMonths = 6),
        Food(name = "Thym", category = FoodCategory.EPICES_AROMATES, recommendedAgeMonths = 6),
        Food(name = "Romarin", category = FoodCategory.EPICES_AROMATES, recommendedAgeMonths = 6),
        Food(name = "Menthe", category = FoodCategory.EPICES_AROMATES, recommendedAgeMonths = 6),
        Food(name = "Coriandre", category = FoodCategory.EPICES_AROMATES, recommendedAgeMonths = 6),
        Food(name = "Aneth", category = FoodCategory.EPICES_AROMATES, recommendedAgeMonths = 6),
        Food(name = "Estragon", category = FoodCategory.EPICES_AROMATES, recommendedAgeMonths = 6),
        Food(name = "Curry doux", category = FoodCategory.EPICES_AROMATES, recommendedAgeMonths = 6),
        Food(name = "Paprika doux", category = FoodCategory.EPICES_AROMATES, recommendedAgeMonths = 6),
        Food(name = "Muscade", category = FoodCategory.EPICES_AROMATES, recommendedAgeMonths = 6),
    )
}
