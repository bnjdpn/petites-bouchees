import SwiftUI

enum Reaction: String, Codable, CaseIterable, Identifiable, Sendable {
    case liked
    case neutral
    case disliked

    var id: String { rawValue }

    var displayName: String {
        switch self {
        case .liked: "Aimé"
        case .neutral: "Neutre"
        case .disliked: "Pas aimé"
        }
    }

    var emoji: String {
        switch self {
        case .liked: "\u{2764}\u{FE0F}"
        case .neutral: "\u{1F610}"
        case .disliked: "\u{1F645}"
        }
    }

    var color: Color {
        switch self {
        case .liked: Color(red: 0.4, green: 0.733, blue: 0.416)
        case .neutral: Color(red: 1.0, green: 0.792, blue: 0.157)
        case .disliked: Color(red: 0.937, green: 0.325, blue: 0.314)
        }
    }
}
