import SwiftUI

struct AddFoodSheet: View {
    @Environment(\.dismiss) private var dismiss

    @State private var name = ""
    @State private var category: FoodCategory = .legumes
    @State private var recommendedAge = 4
    @State private var notes = ""

    let onSave: (String, FoodCategory, Int, String?) -> Void

    var body: some View {
        NavigationStack {
            Form {
                Section {
                    TextField("Nom de l'aliment", text: $name)
                }

                Section {
                    Picker("Cat\u{00E9}gorie", selection: $category) {
                        ForEach(FoodCategory.allCases) { cat in
                            HStack {
                                Text(cat.emoji)
                                Text(cat.displayName)
                            }
                            .tag(cat)
                        }
                    }
                }

                Section {
                    Stepper(
                        "\u{00C2}ge recommand\u{00E9} : \(recommendedAge) mois",
                        value: $recommendedAge,
                        in: 4...36
                    )
                }

                Section {
                    TextField("Notes (optionnel)", text: $notes)
                }
            }
            .navigationTitle("Nouvel aliment")
            .navigationBarTitleDisplayMode(.inline)
            .toolbar {
                ToolbarItem(placement: .cancellationAction) {
                    Button("Annuler") {
                        dismiss()
                    }
                }
                ToolbarItem(placement: .confirmationAction) {
                    Button("Ajouter") {
                        onSave(name, category, recommendedAge, notes.isEmpty ? nil : notes)
                        dismiss()
                    }
                    .disabled(name.trimmingCharacters(in: .whitespaces).isEmpty)
                }
            }
        }
    }
}
