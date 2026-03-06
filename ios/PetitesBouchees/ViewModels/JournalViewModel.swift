import SwiftData
import Foundation

@Observable
@MainActor
final class JournalViewModel {
    private let modelContext: ModelContext

    var entries: [FoodEntry] = []

    var groupedByDate: [(Date, [FoodEntry])] {
        let calendar = Calendar.current
        let grouped = Dictionary(grouping: entries) { entry in
            calendar.startOfDay(for: entry.dateIntroduced)
        }
        return grouped
            .sorted { $0.key > $1.key }
            .map { ($0.key, $0.value.sorted { $0.dateIntroduced > $1.dateIntroduced }) }
    }

    init(modelContext: ModelContext) {
        self.modelContext = modelContext
    }

    func fetchEntries() {
        let descriptor = FetchDescriptor<FoodEntry>(sortBy: [SortDescriptor(\.dateIntroduced, order: .reverse)])
        entries = (try? modelContext.fetch(descriptor)) ?? []
    }
}
