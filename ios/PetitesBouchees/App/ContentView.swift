import SwiftUI
import SwiftData

enum AppTab: String, Sendable {
    case foods, journal, progress
}

struct ContentView: View {
    @Environment(\.modelContext) private var modelContext
    @State private var selectedTab: AppTab = .foods

    var body: some View {
        TabView(selection: $selectedTab) {
            NavigationStack { FoodsScreen() }
                .tabItem {
                    Label("Aliments", systemImage: "fork.knife")
                }
                .tag(AppTab.foods)

            NavigationStack { JournalScreen() }
                .tabItem {
                    Label("Journal", systemImage: "book")
                }
                .tag(AppTab.journal)

            NavigationStack { ProgressScreen() }
                .tabItem {
                    Label("Progression", systemImage: "chart.bar")
                }
                .tag(AppTab.progress)
        }
        .tint(.primaryGreen)
        .task {
            #if DEBUG
            if ProcessInfo.processInfo.arguments.contains("-screenshot") {
                ScreenshotDataService.populate(context: modelContext)
                return
            }
            #endif
            SeedDataService.seedIfNeeded(context: modelContext)
        }
    }
}
