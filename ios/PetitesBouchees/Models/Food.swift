import SwiftData

@Model
final class Food {
    var name: String = ""
    var categoryRawValue: String = "legumes"
    var recommendedAgeMonths: Int = 4
    var notes: String?

    var category: FoodCategory {
        get { FoodCategory(rawValue: categoryRawValue) ?? .legumes }
        set { categoryRawValue = newValue.rawValue }
    }

    @Relationship(deleteRule: .cascade, inverse: \FoodEntry.food)
    var entries: [FoodEntry]? = []

    init(name: String, category: FoodCategory, recommendedAgeMonths: Int, notes: String? = nil) {
        self.name = name
        self.categoryRawValue = category.rawValue
        self.recommendedAgeMonths = recommendedAgeMonths
        self.notes = notes
    }
}
