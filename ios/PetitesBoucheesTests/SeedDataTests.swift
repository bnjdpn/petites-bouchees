import Testing
import SwiftData
@testable import PetitesBouchees

@Suite("SeedDataService")
struct SeedDataTests {
    @Test("Seed data is idempotent")
    @MainActor
    func seedIdempotent() throws {
        let config = ModelConfiguration(isStoredInMemoryOnly: true)
        let container = try ModelContainer(for: Food.self, FoodEntry.self, configurations: config)
        let context = container.mainContext

        SeedDataService.seedIfNeeded(context: context)
        let firstCount = try context.fetchCount(FetchDescriptor<Food>())

        SeedDataService.seedIfNeeded(context: context)
        let secondCount = try context.fetchCount(FetchDescriptor<Food>())

        #expect(firstCount == secondCount)
        #expect(firstCount > 150)
    }

    @Test("All categories are represented in seed data")
    @MainActor
    func allCategoriesPresent() throws {
        let config = ModelConfiguration(isStoredInMemoryOnly: true)
        let container = try ModelContainer(for: Food.self, FoodEntry.self, configurations: config)
        let context = container.mainContext

        SeedDataService.seedIfNeeded(context: context)
        let foods = try context.fetch(FetchDescriptor<Food>())
        let categories = Set(foods.map(\.category))

        #expect(categories.count == FoodCategory.allCases.count)
    }
}
