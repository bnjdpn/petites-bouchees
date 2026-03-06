import SwiftData
import Foundation

@Model
final class FoodEntry {
    var dateIntroduced: Date = Date.now
    var reactionRawValue: String = "neutral"
    var hasDigestiveIssue: Bool = false
    var hasAllergicReaction: Bool = false
    var notes: String?
    var food: Food?

    var reaction: Reaction {
        get { Reaction(rawValue: reactionRawValue) ?? .neutral }
        set { reactionRawValue = newValue.rawValue }
    }

    init(food: Food, dateIntroduced: Date = .now, reaction: Reaction = .neutral, hasDigestiveIssue: Bool = false, hasAllergicReaction: Bool = false, notes: String? = nil) {
        self.food = food
        self.dateIntroduced = dateIntroduced
        self.reactionRawValue = reaction.rawValue
        self.hasDigestiveIssue = hasDigestiveIssue
        self.hasAllergicReaction = hasAllergicReaction
        self.notes = notes
    }
}
