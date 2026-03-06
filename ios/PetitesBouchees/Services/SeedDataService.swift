import SwiftData

struct SeedDataService: Sendable {
    @MainActor
    static func seedIfNeeded(context: ModelContext) {
        let descriptor = FetchDescriptor<Food>()
        let count = (try? context.fetchCount(descriptor)) ?? 0
        guard count == 0 else { return }
        for food in allFoods() { context.insert(food) }
        try? context.save()
    }

    private static func allFoods() -> [Food] {
        [
            // ===== LEGUMES =====
            // 4 mois
            Food(name: "Carotte", category: .legumes, recommendedAgeMonths: 4),
            Food(name: "Courgette", category: .legumes, recommendedAgeMonths: 4),
            Food(name: "Haricot vert", category: .legumes, recommendedAgeMonths: 4),
            Food(name: "Blanc de poireau", category: .legumes, recommendedAgeMonths: 4),
            Food(name: "Potimarron", category: .legumes, recommendedAgeMonths: 4),
            Food(name: "Patate douce", category: .legumes, recommendedAgeMonths: 4),
            Food(name: "Potiron", category: .legumes, recommendedAgeMonths: 4),
            Food(name: "Butternut", category: .legumes, recommendedAgeMonths: 4),
            Food(name: "Panais", category: .legumes, recommendedAgeMonths: 4),
            // 6 mois
            Food(name: "Brocoli", category: .legumes, recommendedAgeMonths: 6),
            Food(name: "Epinards", category: .legumes, recommendedAgeMonths: 6),
            Food(name: "Petits pois", category: .legumes, recommendedAgeMonths: 6),
            Food(name: "Aubergine", category: .legumes, recommendedAgeMonths: 6),
            Food(name: "Tomate", category: .legumes, recommendedAgeMonths: 6, notes: "Cuite, pelée, épépinée"),
            Food(name: "Artichaut", category: .legumes, recommendedAgeMonths: 6),
            Food(name: "Betterave", category: .legumes, recommendedAgeMonths: 6),
            Food(name: "Fenouil", category: .legumes, recommendedAgeMonths: 6),
            Food(name: "Navet", category: .legumes, recommendedAgeMonths: 6),
            Food(name: "Céleri", category: .legumes, recommendedAgeMonths: 6),
            Food(name: "Avocat", category: .legumes, recommendedAgeMonths: 6),
            Food(name: "Concombre", category: .legumes, recommendedAgeMonths: 6),
            Food(name: "Poivron", category: .legumes, recommendedAgeMonths: 6),
            Food(name: "Endive", category: .legumes, recommendedAgeMonths: 6),
            Food(name: "Blette", category: .legumes, recommendedAgeMonths: 6),
            Food(name: "Topinambour", category: .legumes, recommendedAgeMonths: 6),
            Food(name: "Rutabaga", category: .legumes, recommendedAgeMonths: 6),
            Food(name: "Cresson", category: .legumes, recommendedAgeMonths: 6),
            Food(name: "Asperge", category: .legumes, recommendedAgeMonths: 6),
            Food(name: "Salsifis", category: .legumes, recommendedAgeMonths: 6),
            Food(name: "Maïs doux", category: .legumes, recommendedAgeMonths: 6, notes: "En purée"),
            Food(name: "Laitue", category: .legumes, recommendedAgeMonths: 6, notes: "Cuite"),
            Food(name: "Oseille", category: .legumes, recommendedAgeMonths: 6, notes: "En petite quantité"),
            // 8 mois
            Food(name: "Chou-fleur", category: .legumes, recommendedAgeMonths: 8),
            Food(name: "Chou blanc", category: .legumes, recommendedAgeMonths: 8),
            Food(name: "Chou vert", category: .legumes, recommendedAgeMonths: 8),
            Food(name: "Chou rouge", category: .legumes, recommendedAgeMonths: 8),
            Food(name: "Chou de Bruxelles", category: .legumes, recommendedAgeMonths: 8),
            Food(name: "Chou frisé (kale)", category: .legumes, recommendedAgeMonths: 8),
            Food(name: "Brocoli chinois", category: .legumes, recommendedAgeMonths: 8),
            Food(name: "Champignons", category: .legumes, recommendedAgeMonths: 8),
            Food(name: "Oignon", category: .legumes, recommendedAgeMonths: 8),
            Food(name: "Échalote", category: .legumes, recommendedAgeMonths: 8),
            Food(name: "Ail", category: .legumes, recommendedAgeMonths: 8),
            // 12 mois
            Food(name: "Radis", category: .legumes, recommendedAgeMonths: 12),
            Food(name: "Olive", category: .legumes, recommendedAgeMonths: 12),
            Food(name: "Cornichon", category: .legumes, recommendedAgeMonths: 12),

            // ===== FRUITS =====
            // 4 mois
            Food(name: "Pomme", category: .fruits, recommendedAgeMonths: 4),
            Food(name: "Poire", category: .fruits, recommendedAgeMonths: 4),
            Food(name: "Banane", category: .fruits, recommendedAgeMonths: 4),
            Food(name: "Pêche", category: .fruits, recommendedAgeMonths: 4),
            Food(name: "Abricot", category: .fruits, recommendedAgeMonths: 4),
            Food(name: "Coing", category: .fruits, recommendedAgeMonths: 4),
            // 6 mois
            Food(name: "Mangue", category: .fruits, recommendedAgeMonths: 6),
            Food(name: "Melon", category: .fruits, recommendedAgeMonths: 6),
            Food(name: "Pastèque", category: .fruits, recommendedAgeMonths: 6),
            Food(name: "Fraise", category: .fruits, recommendedAgeMonths: 6),
            Food(name: "Framboise", category: .fruits, recommendedAgeMonths: 6),
            Food(name: "Myrtille", category: .fruits, recommendedAgeMonths: 6),
            Food(name: "Cerise", category: .fruits, recommendedAgeMonths: 6, notes: "Dénoyautée"),
            Food(name: "Prune", category: .fruits, recommendedAgeMonths: 6),
            Food(name: "Raisin", category: .fruits, recommendedAgeMonths: 6, notes: "Pelé, coupé"),
            Food(name: "Orange", category: .fruits, recommendedAgeMonths: 6),
            Food(name: "Clémentine", category: .fruits, recommendedAgeMonths: 6),
            Food(name: "Mandarine", category: .fruits, recommendedAgeMonths: 6),
            Food(name: "Kiwi", category: .fruits, recommendedAgeMonths: 6),
            Food(name: "Ananas", category: .fruits, recommendedAgeMonths: 6),
            Food(name: "Papaye", category: .fruits, recommendedAgeMonths: 6),
            Food(name: "Fruit de la passion", category: .fruits, recommendedAgeMonths: 6),
            Food(name: "Nectarine", category: .fruits, recommendedAgeMonths: 6),
            Food(name: "Brugnon", category: .fruits, recommendedAgeMonths: 6),
            Food(name: "Figue", category: .fruits, recommendedAgeMonths: 6),
            Food(name: "Pruneau", category: .fruits, recommendedAgeMonths: 6),
            Food(name: "Cassis", category: .fruits, recommendedAgeMonths: 6),
            Food(name: "Groseille", category: .fruits, recommendedAgeMonths: 6),
            Food(name: "Mûre", category: .fruits, recommendedAgeMonths: 6),
            Food(name: "Mirabelle", category: .fruits, recommendedAgeMonths: 6),
            Food(name: "Noix de coco", category: .fruits, recommendedAgeMonths: 6, notes: "Râpée ou en lait"),
            // 12 mois
            Food(name: "Noix", category: .fruits, recommendedAgeMonths: 12, notes: "En poudre ou purée"),
            Food(name: "Noisettes", category: .fruits, recommendedAgeMonths: 12, notes: "En poudre ou purée"),
            Food(name: "Amandes", category: .fruits, recommendedAgeMonths: 12, notes: "En poudre ou purée"),
            Food(name: "Noix de cajou", category: .fruits, recommendedAgeMonths: 12, notes: "En poudre ou purée"),
            Food(name: "Pistache", category: .fruits, recommendedAgeMonths: 12, notes: "En poudre ou purée"),
            Food(name: "Noix de pécan", category: .fruits, recommendedAgeMonths: 12, notes: "En poudre ou purée"),
            Food(name: "Litchi", category: .fruits, recommendedAgeMonths: 12),
            Food(name: "Grenade", category: .fruits, recommendedAgeMonths: 12),
            Food(name: "Datte", category: .fruits, recommendedAgeMonths: 12),
            Food(name: "Citron", category: .fruits, recommendedAgeMonths: 12, notes: "En jus ou assaisonnement"),
            Food(name: "Pamplemousse", category: .fruits, recommendedAgeMonths: 12),

            // ===== FECULENTS =====
            // 4 mois
            Food(name: "Pomme de terre", category: .feculents, recommendedAgeMonths: 4),
            Food(name: "Céréales infantiles sans gluten", category: .feculents, recommendedAgeMonths: 4, notes: "Riz, maïs"),
            // 6 mois
            Food(name: "Blé", category: .feculents, recommendedAgeMonths: 6),
            Food(name: "Semoule", category: .feculents, recommendedAgeMonths: 6),
            Food(name: "Orge", category: .feculents, recommendedAgeMonths: 6),
            Food(name: "Épeautre", category: .feculents, recommendedAgeMonths: 6),
            Food(name: "Riz", category: .feculents, recommendedAgeMonths: 6),
            Food(name: "Pâtes fines", category: .feculents, recommendedAgeMonths: 6, notes: "Vermicelles"),
            Food(name: "Flocons d'avoine", category: .feculents, recommendedAgeMonths: 6),
            Food(name: "Tapioca", category: .feculents, recommendedAgeMonths: 6),
            Food(name: "Quinoa", category: .feculents, recommendedAgeMonths: 6),
            Food(name: "Polenta", category: .feculents, recommendedAgeMonths: 6),
            Food(name: "Sarrasin", category: .feculents, recommendedAgeMonths: 6),
            Food(name: "Millet", category: .feculents, recommendedAgeMonths: 6),
            // 8 mois
            Food(name: "Lentilles corail", category: .feculents, recommendedAgeMonths: 8),
            Food(name: "Pois chiches", category: .feculents, recommendedAgeMonths: 8),
            Food(name: "Haricots blancs", category: .feculents, recommendedAgeMonths: 8),
            Food(name: "Haricots rouges", category: .feculents, recommendedAgeMonths: 8),
            Food(name: "Flageolets", category: .feculents, recommendedAgeMonths: 8),
            Food(name: "Fèves", category: .feculents, recommendedAgeMonths: 8),
            Food(name: "Pois cassés", category: .feculents, recommendedAgeMonths: 8),
            Food(name: "Lentilles vertes", category: .feculents, recommendedAgeMonths: 8),
            // 12 mois
            Food(name: "Pain", category: .feculents, recommendedAgeMonths: 12),
            Food(name: "Biscottes", category: .feculents, recommendedAgeMonths: 12),
            Food(name: "Céréales complètes", category: .feculents, recommendedAgeMonths: 12),

            // ===== PROTEINES ANIMALES =====
            // 6 mois
            Food(name: "Poulet", category: .proteines, recommendedAgeMonths: 6),
            Food(name: "Dinde", category: .proteines, recommendedAgeMonths: 6),
            Food(name: "Boeuf", category: .proteines, recommendedAgeMonths: 6),
            Food(name: "Veau", category: .proteines, recommendedAgeMonths: 6),
            Food(name: "Porc", category: .proteines, recommendedAgeMonths: 6),
            Food(name: "Agneau", category: .proteines, recommendedAgeMonths: 6),
            Food(name: "Lapin", category: .proteines, recommendedAgeMonths: 6),
            Food(name: "Canard", category: .proteines, recommendedAgeMonths: 6),
            Food(name: "Pintade", category: .proteines, recommendedAgeMonths: 6),
            Food(name: "Jambon blanc", category: .proteines, recommendedAgeMonths: 6, notes: "Sans sel ajouté"),
            // Poissons 6 mois
            Food(name: "Cabillaud", category: .proteines, recommendedAgeMonths: 6),
            Food(name: "Colin", category: .proteines, recommendedAgeMonths: 6),
            Food(name: "Merlu", category: .proteines, recommendedAgeMonths: 6),
            Food(name: "Sole", category: .proteines, recommendedAgeMonths: 6),
            Food(name: "Saumon", category: .proteines, recommendedAgeMonths: 6),
            Food(name: "Lieu", category: .proteines, recommendedAgeMonths: 6),
            Food(name: "Bar", category: .proteines, recommendedAgeMonths: 6),
            Food(name: "Dorade", category: .proteines, recommendedAgeMonths: 6),
            Food(name: "Truite", category: .proteines, recommendedAgeMonths: 6),
            Food(name: "Limande", category: .proteines, recommendedAgeMonths: 6),
            Food(name: "Carrelet", category: .proteines, recommendedAgeMonths: 6),
            Food(name: "Sardine", category: .proteines, recommendedAgeMonths: 6),
            Food(name: "Maquereau", category: .proteines, recommendedAgeMonths: 6),
            Food(name: "Haddock", category: .proteines, recommendedAgeMonths: 6),
            // Oeuf 6 mois
            Food(name: "Oeuf", category: .proteines, recommendedAgeMonths: 6, notes: "Bien cuit, jaune puis blanc"),
            // 12 mois
            Food(name: "Charcuterie", category: .proteines, recommendedAgeMonths: 12, notes: "Limitée"),
            Food(name: "Fruits de mer", category: .proteines, recommendedAgeMonths: 12, notes: "Cuits"),
            Food(name: "Crevettes", category: .proteines, recommendedAgeMonths: 12, notes: "Cuites"),
            Food(name: "Thon", category: .proteines, recommendedAgeMonths: 12, notes: "Limiter (mercure)"),

            // ===== PRODUITS LAITIERS =====
            // 6 mois
            Food(name: "Yaourt nature", category: .produitsLaitiers, recommendedAgeMonths: 6),
            Food(name: "Fromage blanc nature", category: .produitsLaitiers, recommendedAgeMonths: 6),
            Food(name: "Petit-suisse nature", category: .produitsLaitiers, recommendedAgeMonths: 6),
            // 8 mois
            Food(name: "Gruyère", category: .produitsLaitiers, recommendedAgeMonths: 8),
            Food(name: "Emmental", category: .produitsLaitiers, recommendedAgeMonths: 8),
            Food(name: "Comté", category: .produitsLaitiers, recommendedAgeMonths: 8),
            Food(name: "Parmesan", category: .produitsLaitiers, recommendedAgeMonths: 8, notes: "Petites quantités"),
            Food(name: "Brie pasteurisé", category: .produitsLaitiers, recommendedAgeMonths: 8),
            Food(name: "Camembert pasteurisé", category: .produitsLaitiers, recommendedAgeMonths: 8),
            Food(name: "Mozzarella", category: .produitsLaitiers, recommendedAgeMonths: 8),
            Food(name: "Ricotta", category: .produitsLaitiers, recommendedAgeMonths: 8),
            Food(name: "Chèvre frais", category: .produitsLaitiers, recommendedAgeMonths: 8),
            Food(name: "Cantal", category: .produitsLaitiers, recommendedAgeMonths: 8),
            Food(name: "Beaufort", category: .produitsLaitiers, recommendedAgeMonths: 8),
            Food(name: "Kiri / Vache qui rit", category: .produitsLaitiers, recommendedAgeMonths: 8),
            // 12 mois
            Food(name: "Lait de vache entier", category: .produitsLaitiers, recommendedAgeMonths: 12),
            Food(name: "Roquefort", category: .produitsLaitiers, recommendedAgeMonths: 12),
            Food(name: "Fromages variés", category: .produitsLaitiers, recommendedAgeMonths: 12),

            // ===== MATIERES GRASSES =====
            // 4 mois
            Food(name: "Huile de colza", category: .matieresGrasses, recommendedAgeMonths: 4, notes: "1 cuillère à café par repas"),
            Food(name: "Huile d'olive", category: .matieresGrasses, recommendedAgeMonths: 4, notes: "1 cuillère à café par repas"),
            Food(name: "Huile de noix", category: .matieresGrasses, recommendedAgeMonths: 4, notes: "1 cuillère à café par repas"),
            Food(name: "Huile de tournesol", category: .matieresGrasses, recommendedAgeMonths: 4, notes: "1 cuillère à café par repas"),
            Food(name: "Huile de lin", category: .matieresGrasses, recommendedAgeMonths: 4, notes: "1 cuillère à café par repas"),
            Food(name: "Beurre non salé", category: .matieresGrasses, recommendedAgeMonths: 4),
            Food(name: "Crème fraîche", category: .matieresGrasses, recommendedAgeMonths: 4),
            // 6 mois
            Food(name: "Huile de sésame", category: .matieresGrasses, recommendedAgeMonths: 6, notes: "1 cuillère à café par repas"),

            // ===== EPICES & AROMATES =====
            // 6 mois
            Food(name: "Vanille", category: .epicesAromates, recommendedAgeMonths: 6),
            Food(name: "Cannelle", category: .epicesAromates, recommendedAgeMonths: 6),
            Food(name: "Cumin", category: .epicesAromates, recommendedAgeMonths: 6),
            Food(name: "Basilic", category: .epicesAromates, recommendedAgeMonths: 6),
            Food(name: "Persil", category: .epicesAromates, recommendedAgeMonths: 6),
            Food(name: "Ciboulette", category: .epicesAromates, recommendedAgeMonths: 6),
            Food(name: "Thym", category: .epicesAromates, recommendedAgeMonths: 6),
            Food(name: "Romarin", category: .epicesAromates, recommendedAgeMonths: 6),
            Food(name: "Menthe", category: .epicesAromates, recommendedAgeMonths: 6),
            Food(name: "Coriandre", category: .epicesAromates, recommendedAgeMonths: 6),
            Food(name: "Aneth", category: .epicesAromates, recommendedAgeMonths: 6),
            Food(name: "Estragon", category: .epicesAromates, recommendedAgeMonths: 6),
            Food(name: "Curry doux", category: .epicesAromates, recommendedAgeMonths: 6),
            Food(name: "Paprika doux", category: .epicesAromates, recommendedAgeMonths: 6),
            Food(name: "Muscade", category: .epicesAromates, recommendedAgeMonths: 6),
            Food(name: "Laurier", category: .epicesAromates, recommendedAgeMonths: 6),
            Food(name: "Cerfeuil", category: .epicesAromates, recommendedAgeMonths: 6),
            Food(name: "Gingembre", category: .epicesAromates, recommendedAgeMonths: 6),
            Food(name: "Curcuma", category: .epicesAromates, recommendedAgeMonths: 6),
            Food(name: "Safran", category: .epicesAromates, recommendedAgeMonths: 6),
            Food(name: "Origan", category: .epicesAromates, recommendedAgeMonths: 6),
            Food(name: "Sauge", category: .epicesAromates, recommendedAgeMonths: 6),
            Food(name: "Citronnelle", category: .epicesAromates, recommendedAgeMonths: 6),
        ]
    }
}
