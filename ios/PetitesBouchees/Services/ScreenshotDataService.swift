import SwiftData
import Foundation

struct ScreenshotDataService: Sendable {
    static var isScreenshotMode: Bool {
        ProcessInfo.processInfo.arguments.contains("-screenshot")
    }

    @MainActor
    static func populate(context: ModelContext) {
        SeedDataService.seedIfNeeded(context: context)

        let descriptor = FetchDescriptor<Food>()
        guard let allFoods = try? context.fetch(descriptor) else { return }

        // Mark ~40% as tested with varied reactions
        let foodsToTest = allFoods.prefix(90)
        let reactions: [Reaction] = [.liked, .liked, .liked, .neutral, .neutral, .disliked]
        let calendar = Calendar.current

        for (index, food) in foodsToTest.enumerated() {
            let daysAgo = Int.random(in: 1...60)
            let date = calendar.date(byAdding: .day, value: -daysAgo, to: .now) ?? .now
            let reaction = reactions[index % reactions.count]

            let entry = FoodEntry(
                food: food,
                dateIntroduced: date,
                reaction: reaction,
                hasDigestiveIssue: index % 15 == 0,
                hasAllergicReaction: index % 25 == 0
            )
            context.insert(entry)
        }
        try? context.save()
    }
}
