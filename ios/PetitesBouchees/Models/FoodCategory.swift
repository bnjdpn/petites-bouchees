import SwiftUI

enum FoodCategory: String, Codable, CaseIterable, Identifiable, Sendable {
    case legumes
    case fruits
    case feculents
    case proteines
    case produitsLaitiers
    case matieresGrasses
    case epicesAromates

    var id: String { rawValue }

    var displayName: String {
        switch self {
        case .legumes: "Légumes"
        case .fruits: "Fruits"
        case .feculents: "Féculents"
        case .proteines: "Protéines"
        case .produitsLaitiers: "Produits laitiers"
        case .matieresGrasses: "Matières grasses"
        case .epicesAromates: "Épices & Aromates"
        }
    }

    var emoji: String {
        switch self {
        case .legumes: "\u{1F966}"
        case .fruits: "\u{1F34E}"
        case .feculents: "\u{1F35E}"
        case .proteines: "\u{1F357}"
        case .produitsLaitiers: "\u{1F9C0}"
        case .matieresGrasses: "\u{1FAD2}"
        case .epicesAromates: "\u{1F33F}"
        }
    }

    var color: Color {
        switch self {
        case .legumes: Color(red: 0.298, green: 0.686, blue: 0.314)
        case .fruits: Color(red: 1.0, green: 0.596, blue: 0.0)
        case .feculents: Color(red: 0.831, green: 0.647, blue: 0.455)
        case .proteines: Color(red: 0.898, green: 0.451, blue: 0.451)
        case .produitsLaitiers: Color(red: 0.392, green: 0.710, blue: 0.965)
        case .matieresGrasses: Color(red: 1.0, green: 0.835, blue: 0.310)
        case .epicesAromates: Color(red: 0.729, green: 0.408, blue: 0.784)
        }
    }

    var sortOrder: Int {
        switch self {
        case .legumes: 0
        case .fruits: 1
        case .feculents: 2
        case .proteines: 3
        case .produitsLaitiers: 4
        case .matieresGrasses: 5
        case .epicesAromates: 6
        }
    }
}
