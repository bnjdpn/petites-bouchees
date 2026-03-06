import SwiftUI
import SwiftData

struct FoodDetailScreen: View {
    @Environment(\.modelContext) private var modelContext
    @Environment(\.dismiss) private var dismiss

    let foodID: PersistentIdentifier
    @State private var viewModel: FoodDetailViewModel?

    var body: some View {
        Group {
            if let viewModel {
                FoodDetailContentView(viewModel: viewModel)
            } else {
                ProgressView()
            }
        }
        .task {
            if viewModel == nil {
                let vm = FoodDetailViewModel(modelContext: modelContext)
                vm.loadFood(id: foodID)
                viewModel = vm
            }
        }
        .onChange(of: viewModel?.isSaved) { _, newValue in
            if newValue == true {
                dismiss()
            }
        }
    }
}

private struct FoodDetailContentView: View {
    @Bindable var viewModel: FoodDetailViewModel

    var body: some View {
        ScrollView {
            if let food = viewModel.food {
                VStack(alignment: .leading, spacing: 20) {
                    foodInfoSection(food: food)
                    Divider()
                    dateSection
                    reactionSelector
                    issuesSection
                    notesSection
                    saveButton
                    if viewModel.isAlreadyTested {
                        deleteButton
                    }
                }
                .padding()
            }
        }
        .navigationTitle(viewModel.food?.name ?? "")
        .navigationBarTitleDisplayMode(.inline)
    }

    private func foodInfoSection(food: Food) -> some View {
        VStack(alignment: .leading, spacing: 12) {
            HStack(spacing: 8) {
                HStack(spacing: 4) {
                    Text(food.category.emoji)
                    Text(food.category.displayName)
                }
                .font(.subheadline)
                .fontWeight(.medium)
                .padding(.horizontal, 10)
                .padding(.vertical, 6)
                .background(food.category.color.opacity(0.15))
                .foregroundStyle(food.category.color)
                .clipShape(.capsule)

                Text("D\u{00E8}s \(food.recommendedAgeMonths) mois")
                    .font(.subheadline)
                    .padding(.horizontal, 10)
                    .padding(.vertical, 6)
                    .background(Color.surfaceVariant.opacity(0.5))
                    .clipShape(.capsule)
            }

            if let notes = food.notes, !notes.isEmpty {
                Text(notes)
                    .font(.subheadline)
                    .italic()
                    .foregroundStyle(.secondary)
            }
        }
    }

    private var dateSection: some View {
        DatePicker(
            "Date d'introduction",
            selection: $viewModel.dateIntroduced,
            displayedComponents: .date
        )
        .environment(\.locale, Locale(identifier: "fr_FR"))
    }

    private var reactionSelector: some View {
        VStack(alignment: .leading, spacing: 8) {
            Text("R\u{00E9}action")
                .font(.headline)

            HStack(spacing: 12) {
                ForEach(Reaction.allCases) { reaction in
                    Button {
                        viewModel.reaction = reaction
                    } label: {
                        VStack(spacing: 4) {
                            Text(reaction.emoji)
                                .font(.title2)
                            Text(reaction.displayName)
                                .font(.caption)
                        }
                        .frame(maxWidth: .infinity)
                        .padding(.vertical, 12)
                        .background(
                            viewModel.reaction == reaction
                                ? reaction.color.opacity(0.2)
                                : Color.surfaceVariant.opacity(0.3)
                        )
                        .foregroundStyle(
                            viewModel.reaction == reaction
                                ? reaction.color
                                : .primary
                        )
                        .clipShape(.rect(cornerRadius: 12))
                        .overlay(
                            RoundedRectangle(cornerRadius: 12)
                                .strokeBorder(
                                    viewModel.reaction == reaction
                                        ? reaction.color
                                        : .clear,
                                    lineWidth: 2
                                )
                        )
                    }
                }
            }
        }
    }

    private var issuesSection: some View {
        VStack(spacing: 0) {
            Toggle(isOn: $viewModel.hasDigestiveIssue) {
                HStack(spacing: 8) {
                    Image(systemName: "exclamationmark.triangle.fill")
                        .foregroundStyle(.orange)
                    Text("Probl\u{00E8}me digestif")
                }
            }
            .tint(.orange)

            Toggle(isOn: $viewModel.hasAllergicReaction) {
                HStack(spacing: 8) {
                    Image(systemName: "exclamationmark.circle.fill")
                        .foregroundStyle(.red)
                    Text("R\u{00E9}action allergique")
                }
            }
            .tint(.red)
        }
    }

    private var notesSection: some View {
        VStack(alignment: .leading, spacing: 8) {
            Text("Notes")
                .font(.headline)

            TextField("Observations...", text: $viewModel.notes, axis: .vertical)
                .lineLimit(3, reservesSpace: true)
                .textFieldStyle(.roundedBorder)
        }
    }

    private var saveButton: some View {
        Button {
            viewModel.save()
        } label: {
            Text("Enregistrer")
                .font(.headline)
                .frame(maxWidth: .infinity)
                .padding(.vertical, 12)
        }
        .buttonStyle(.borderedProminent)
        .tint(.primaryGreen)
    }

    private var deleteButton: some View {
        Button(role: .destructive) {
            viewModel.deleteEntry()
        } label: {
            Text("Supprimer l'entr\u{00E9}e")
                .font(.subheadline)
                .frame(maxWidth: .infinity)
                .padding(.vertical, 8)
        }
        .buttonStyle(.bordered)
    }
}
