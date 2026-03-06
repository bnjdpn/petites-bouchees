import Testing
@testable import PetitesBouchees

@Suite("FoodCategory")
struct FoodCategoryTests {
    @Test("All categories have display name and emoji")
    func allCategoriesHaveDisplayInfo() {
        for category in FoodCategory.allCases {
            #expect(!category.displayName.isEmpty)
            #expect(!category.emoji.isEmpty)
        }
    }

    @Test("Seven categories exist")
    func sevenCategories() {
        #expect(FoodCategory.allCases.count == 7)
    }

    @Test("Sort order is unique")
    func uniqueSortOrder() {
        let orders = FoodCategory.allCases.map(\.sortOrder)
        #expect(Set(orders).count == FoodCategory.allCases.count)
    }
}
