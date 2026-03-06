import SwiftUI
import SwiftData

struct FoodsScreen: View {
    @Environment(\.modelContext) private var modelContext
    @State private var viewModel: FoodsViewModel?

    private let ageFilters = [4, 6, 8, 9, 12]

    var body: some View {
        Group {
            if let viewModel {
                FoodsContentView(viewModel: viewModel, ageFilters: ageFilters)
            } else {
                ProgressView()
            }
        }
        .navigationTitle("Aliments")
        .task {
            if viewModel == nil {
                let vm = FoodsViewModel(modelContext: modelContext)
                vm.fetchFoods()
                viewModel = vm
            }
        }
    }
}

private struct FoodsContentView: View {
    @Bindable var viewModel: FoodsViewModel
    let ageFilters: [Int]

    var body: some View {
        VStack(spacing: 0) {
            filtersSection
            foodsList
        }
        .toolbar {
            ToolbarItem(placement: .topBarTrailing) {
                Button {
                    viewModel.showAddSheet = true
                } label: {
                    Image(systemName: "plus")
                }
            }
        }
        .sheet(isPresented: $viewModel.showAddSheet) {
            AddFoodSheet { name, category, age, notes in
                viewModel.addFood(name: name, category: category, recommendedAge: age, notes: notes)
            }
        }
        .alert(
            "Supprimer cet aliment ?",
            isPresented: Binding(
                get: { viewModel.foodToDelete != nil },
                set: { if !$0 { viewModel.foodToDelete = nil } }
            ),
            presenting: viewModel.foodToDelete
        ) { food in
            Button("Supprimer", role: .destructive) {
                viewModel.deleteFood(food)
            }
            Button("Annuler", role: .cancel) {
                viewModel.foodToDelete = nil
            }
        } message: { food in
            Text("L'aliment \"\(food.name)\" et ses enregistrements seront supprim\u{00E9}s.")
        }
    }

    private var filtersSection: some View {
        VStack(spacing: 8) {
            ScrollView(.horizontal, showsIndicators: false) {
                HStack(spacing: 8) {
                    ForEach(FoodCategory.allCases) { category in
                        Button {
                            viewModel.toggleCategory(category)
                        } label: {
                            HStack(spacing: 4) {
                                Text(category.emoji)
                                    .font(.caption)
                                Text(category.displayName)
                                    .font(.caption)
                                    .fontWeight(.medium)
                            }
                            .padding(.horizontal, 12)
                            .padding(.vertical, 6)
                            .background(
                                viewModel.selectedCategory == category
                                    ? category.color.opacity(0.2)
                                    : Color.surfaceVariant.opacity(0.5)
                            )
                            .foregroundStyle(
                                viewModel.selectedCategory == category
                                    ? category.color
                                    : .primary
                            )
                            .clipShape(.capsule)
                            .overlay(
                                Capsule()
                                    .strokeBorder(
                                        viewModel.selectedCategory == category
                                            ? category.color
                                            : .clear,
                                        lineWidth: 1.5
                                    )
                            )
                        }
                    }
                }
                .padding(.horizontal)
            }

            ScrollView(.horizontal, showsIndicators: false) {
                HStack(spacing: 8) {
                    ForEach(ageFilters, id: \.self) { age in
                        Button {
                            viewModel.toggleAgeFilter(age)
                        } label: {
                            Text("\(age) mois")
                                .font(.caption)
                                .fontWeight(.medium)
                                .padding(.horizontal, 12)
                                .padding(.vertical, 6)
                                .background(
                                    viewModel.selectedAgeFilter == age
                                        ? Color.primaryGreen.opacity(0.2)
                                        : Color.surfaceVariant.opacity(0.5)
                                )
                                .foregroundStyle(
                                    viewModel.selectedAgeFilter == age
                                        ? Color.primaryGreen
                                        : .primary
                                )
                                .clipShape(.capsule)
                                .overlay(
                                    Capsule()
                                        .strokeBorder(
                                            viewModel.selectedAgeFilter == age
                                                ? Color.primaryGreen
                                                : .clear,
                                            lineWidth: 1.5
                                        )
                                )
                        }
                    }

                    Divider().frame(height: 20)

                    testedChip(label: "Tous", value: nil)
                    testedChip(label: "Test\u{00E9}s", value: true)
                    testedChip(label: "\u{00C0} tester", value: false)
                }
                .padding(.horizontal)
            }
        }
        .padding(.vertical, 8)
    }

    private func testedChip(label: String, value: Bool?) -> some View {
        Button {
            viewModel.toggleTestedFilter(value)
        } label: {
            Text(label)
                .font(.caption)
                .fontWeight(.medium)
                .padding(.horizontal, 12)
                .padding(.vertical, 6)
                .background(
                    viewModel.showOnlyTested == value
                        ? Color.secondaryPeach.opacity(0.2)
                        : Color.surfaceVariant.opacity(0.5)
                )
                .foregroundStyle(
                    viewModel.showOnlyTested == value
                        ? Color.secondaryPeach
                        : .primary
                )
                .clipShape(.capsule)
                .overlay(
                    Capsule()
                        .strokeBorder(
                            viewModel.showOnlyTested == value
                                ? Color.secondaryPeach
                                : .clear,
                            lineWidth: 1.5
                        )
                )
        }
    }

    private var foodsList: some View {
        List {
            ForEach(viewModel.groupedFoods, id: \.0) { category, foods in
                Section {
                    ForEach(foods, id: \.persistentModelID) { food in
                        NavigationLink(value: food.persistentModelID) {
                            FoodRow(
                                food: food,
                                isTested: viewModel.testedFoodIDs.contains(food.persistentModelID),
                                categoryColor: category.color
                            )
                        }
                        .swipeActions(edge: .trailing, allowsFullSwipe: false) {
                            Button(role: .destructive) {
                                viewModel.foodToDelete = food
                            } label: {
                                Label("Supprimer", systemImage: "trash")
                            }
                        }
                    }
                } header: {
                    HStack(spacing: 6) {
                        Text(category.emoji)
                        Text(category.displayName)
                            .fontWeight(.semibold)
                    }
                    .font(.subheadline)
                    .foregroundStyle(category.color)
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

private struct FoodRow: View {
    let food: Food
    let isTested: Bool
    let categoryColor: Color

    var body: some View {
        HStack(spacing: 12) {
            RoundedRectangle(cornerRadius: 2)
                .fill(categoryColor)
                .frame(width: 4, height: 40)

            VStack(alignment: .leading, spacing: 2) {
                Text(food.name)
                    .font(.body)

                if let notes = food.notes, !notes.isEmpty {
                    Text(notes)
                        .font(.caption)
                        .italic()
                        .foregroundStyle(.secondary)
                }
            }

            Spacer()

            Text("D\u{00E8}s \(food.recommendedAgeMonths)m")
                .font(.caption2)
                .fontWeight(.medium)
                .padding(.horizontal, 8)
                .padding(.vertical, 4)
                .background(Color.surfaceVariant.opacity(0.5))
                .clipShape(.capsule)

            Image(systemName: isTested ? "checkmark.circle.fill" : "circle")
                .foregroundStyle(isTested ? Color.primaryGreen : .secondary)
                .imageScale(.large)
        }
        .padding(.vertical, 2)
    }
}
