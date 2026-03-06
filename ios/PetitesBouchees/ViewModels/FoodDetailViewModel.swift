import SwiftData
import Foundation

@Observable
@MainActor
final class FoodDetailViewModel {
    private let modelContext: ModelContext

    var food: Food?
    var existingEntry: FoodEntry?
    var dateIntroduced: Date = .now
    var reaction: Reaction = .neutral
    var hasDigestiveIssue: Bool = false
    var hasAllergicReaction: Bool = false
    var notes: String = ""
    var isSaved: Bool = false

    var isAlreadyTested: Bool {
        existingEntry != nil
    }

    init(modelContext: ModelContext) {
        self.modelContext = modelContext
    }

    func loadFood(id: PersistentIdentifier) {
        food = modelContext.model(for: id) as? Food

        if let food, let entries = food.entries, let entry = entries.first {
            existingEntry = entry
            dateIntroduced = entry.dateIntroduced
            reaction = entry.reaction
            hasDigestiveIssue = entry.hasDigestiveIssue
            hasAllergicReaction = entry.hasAllergicReaction
            notes = entry.notes ?? ""
        }
    }

    func save() {
        guard let food else { return }

        if let entry = existingEntry {
            entry.dateIntroduced = dateIntroduced
            entry.reaction = reaction
            entry.hasDigestiveIssue = hasDigestiveIssue
            entry.hasAllergicReaction = hasAllergicReaction
            entry.notes = notes.isEmpty ? nil : notes
        } else {
            let entry = FoodEntry(
                food: food,
                dateIntroduced: dateIntroduced,
                reaction: reaction,
                hasDigestiveIssue: hasDigestiveIssue,
                hasAllergicReaction: hasAllergicReaction,
                notes: notes.isEmpty ? nil : notes
            )
            modelContext.insert(entry)
        }

        try? modelContext.save()
        isSaved = true
    }

    func deleteEntry() {
        guard let entry = existingEntry else { return }
        modelContext.delete(entry)
        try? modelContext.save()
        isSaved = true
    }
}
