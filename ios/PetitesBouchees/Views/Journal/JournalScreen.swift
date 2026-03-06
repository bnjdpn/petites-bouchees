import SwiftUI
import SwiftData

struct JournalScreen: View {
    @Environment(\.modelContext) private var modelContext
    @State private var viewModel: JournalViewModel?

    var body: some View {
        Group {
            if let viewModel {
                JournalContentView(viewModel: viewModel)
            } else {
                ProgressView()
            }
        }
        .navigationTitle("Journal")
        .task {
            if viewModel == nil {
                let vm = JournalViewModel(modelContext: modelContext)
                vm.fetchEntries()
                viewModel = vm
            }
        }
    }
}

private struct JournalContentView: View {
    let viewModel: JournalViewModel

    private var dateFormatter: DateFormatter {
        let formatter = DateFormatter()
        formatter.dateFormat = "d MMMM yyyy"
        formatter.locale = Locale(identifier: "fr_FR")
        return formatter
    }

    var body: some View {
        Group {
            if viewModel.entries.isEmpty {
                ContentUnavailableView(
                    "Aucun aliment introduit",
                    systemImage: "book",
                    description: Text("Les aliments test\u{00E9}s apparaitront ici.")
                )
            } else {
                List {
                    ForEach(viewModel.groupedByDate, id: \.0) { date, entries in
                        Section {
                            ForEach(entries, id: \.persistentModelID) { entry in
                                if let food = entry.food {
                                    NavigationLink(value: food.persistentModelID) {
                                        JournalRow(entry: entry, food: food)
                                    }
                                }
                            }
                        } header: {
                            Text(dateFormatter.string(from: date))
                                .textCase(nil)
                        }
                    }
                }
                .listStyle(.plain)
                .navigationDestination(for: PersistentIdentifier.self) { foodID in
                    FoodDetailScreen(foodID: foodID)
                }
            }
        }
    }
}

private struct JournalRow: View {
    let entry: FoodEntry
    let food: Food

    var body: some View {
        HStack(spacing: 12) {
            Circle()
                .fill(food.category.color)
                .frame(width: 10, height: 10)

            VStack(alignment: .leading, spacing: 2) {
                Text(food.name)
                    .font(.body)
                    .fontWeight(.semibold)

                HStack(spacing: 4) {
                    Text(food.category.emoji)
                    Text(food.category.displayName)
                }
                .font(.caption)
                .foregroundStyle(.secondary)
            }

            Spacer()

            Text(entry.reaction.emoji)
                .font(.title3)

            if entry.hasDigestiveIssue {
                Image(systemName: "exclamationmark.triangle.fill")
                    .foregroundStyle(.orange)
                    .imageScale(.small)
            }

            if entry.hasAllergicReaction {
                Image(systemName: "exclamationmark.circle.fill")
                    .foregroundStyle(.red)
                    .imageScale(.small)
            }
        }
        .padding(.vertical, 2)
    }
}
