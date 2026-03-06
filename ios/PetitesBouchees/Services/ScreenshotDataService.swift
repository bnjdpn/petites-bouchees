import SwiftData
import Foundation

struct ScreenshotDataService: Sendable {
    static var isScreenshotMode: Bool {
        ProcessInfo.processInfo.arguments.contains("-screenshot")
    }

    @MainActor
    static func populate(context: ModelContext) {
        // Will be implemented later
        SeedDataService.seedIfNeeded(context: context)
    }
}
