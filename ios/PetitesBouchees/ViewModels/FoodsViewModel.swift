import SwiftData
import SwiftUI

@Observable
@MainActor
final class FoodsViewModel {
    private let modelContext: ModelContext

    var foods: [Food] = []
    var testedFoodIDs: Set<PersistentIdentifier> = []
    var selectedCategory: FoodCategory?
    var selectedAgeFilter: Int?
    var showOnlyTested: Bool?
    var showAddSheet = false
    var foodToDelete: Food?

    var filteredFoods: [Food] {
        foods.filter { food in
            if let cat = selectedCategory, food.category != cat { return false }
            if let age = selectedAgeFilter, food.recommendedAgeMonths != age { return false }
            if let tested = showOnlyTested {
                let isTested = testedFoodIDs.contains(food.persistentModelID)
                if tested && !isTested { return false }
                if !tested && isTested { return false }
            }
            return true
        }
    }

    var groupedFoods: [(FoodCategory, [Food])] {
        let grouped = Dictionary(grouping: filteredFoods) { $0.category }
        return FoodCategory.allCases
            .compactMap { cat in
                guard let items = grouped[cat], !items.isEmpty else { return nil }
                return (cat, items.sorted { $0.name < $1.name })
            }
    }

    init(modelContext: ModelContext) {
        self.modelContext = modelContext
    }

    func fetchFoods() {
        let descriptor = FetchDescriptor<Food>(sortBy: [SortDescriptor(\.name)])
        foods = (try? modelContext.fetch(descriptor)) ?? []

        let entryDescriptor = FetchDescriptor<FoodEntry>()
        let entries = (try? modelContext.fetch(entryDescriptor)) ?? []
        testedFoodIDs = Set(entries.compactMap { $0.food?.persistentModelID })
    }

    func addFood(name: String, category: FoodCategory, recommendedAge: Int, notes: String?) {
        let food = Food(name: name, category: category, recommendedAgeMonths: recommendedAge, notes: notes)
        modelContext.insert(food)
        try? modelContext.save()
        fetchFoods()
    }

    func deleteFood(_ food: Food) {
        modelContext.delete(food)
        try? modelContext.save()
        fetchFoods()
    }

    func toggleCategory(_ category: FoodCategory) {
        if selectedCategory == category {
            selectedCategory = nil
        } else {
            selectedCategory = category
        }
    }

    func toggleAgeFilter(_ age: Int) {
        if selectedAgeFilter == age {
            selectedAgeFilter = nil
        } else {
            selectedAgeFilter = age
        }
    }

    func toggleTestedFilter(_ tested: Bool?) {
        if showOnlyTested == tested {
            showOnlyTested = nil
        } else {
            showOnlyTested = tested
        }
    }
}
