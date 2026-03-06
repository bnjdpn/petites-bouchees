import SwiftData
import Foundation

struct CategoryProgress: Identifiable, Sendable {
    var id: String { category.rawValue }
    let category: FoodCategory
    let total: Int
    let tested: Int
}

struct AlertItem: Identifiable, Sendable {
    let id: PersistentIdentifier
    let foodName: String
    let hasDigestiveIssue: Bool
    let hasAllergicReaction: Bool
}

@Observable
@MainActor
final class ProgressViewModel {
    private let modelContext: ModelContext

    var totalFoods: Int = 0
    var testedFoods: Int = 0
    var categoryProgress: [CategoryProgress] = []
    var likedCount: Int = 0
    var neutralCount: Int = 0
    var dislikedCount: Int = 0
    var alerts: [AlertItem] = []

    var progressPercent: Double {
        guard totalFoods > 0 else { return 0 }
        return Double(testedFoods) / Double(totalFoods)
    }

    init(modelContext: ModelContext) {
        self.modelContext = modelContext
    }

    func fetchProgress() {
        let foodDescriptor = FetchDescriptor<Food>()
        let allFoods = (try? modelContext.fetch(foodDescriptor)) ?? []

        let entryDescriptor = FetchDescriptor<FoodEntry>()
        let allEntries = (try? modelContext.fetch(entryDescriptor)) ?? []

        let testedFoodIDs = Set(allEntries.compactMap { $0.food?.persistentModelID })

        totalFoods = allFoods.count
        testedFoods = testedFoodIDs.count

        categoryProgress = FoodCategory.allCases.map { cat in
            let catFoods = allFoods.filter { $0.category == cat }
            let catTested = catFoods.filter { testedFoodIDs.contains($0.persistentModelID) }.count
            return CategoryProgress(category: cat, total: catFoods.count, tested: catTested)
        }

        likedCount = allEntries.filter { $0.reaction == .liked }.count
        neutralCount = allEntries.filter { $0.reaction == .neutral }.count
        dislikedCount = allEntries.filter { $0.reaction == .disliked }.count

        alerts = allEntries
            .filter { $0.hasDigestiveIssue || $0.hasAllergicReaction }
            .compactMap { entry in
                guard let food = entry.food else { return nil }
                return AlertItem(
                    id: entry.persistentModelID,
                    foodName: food.name,
                    hasDigestiveIssue: entry.hasDigestiveIssue,
                    hasAllergicReaction: entry.hasAllergicReaction
                )
            }
    }
}
