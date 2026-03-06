import SwiftUI
import SwiftData

@main
struct PetitesBoucheesApp: App {
    let modelContainer: ModelContainer

    init() {
        #if DEBUG
        if ProcessInfo.processInfo.arguments.contains("-screenshot") {
            let config = ModelConfiguration(isStoredInMemoryOnly: true)
            do {
                modelContainer = try ModelContainer(for: Food.self, FoodEntry.self, configurations: config)
            } catch {
                fatalError("Failed to create in-memory container: \(error)")
            }
            return
        }
        #endif

        let config = ModelConfiguration()
        do {
            modelContainer = try ModelContainer(for: Food.self, FoodEntry.self, configurations: config)
        } catch {
            fatalError("Failed to create container: \(error)")
        }
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
        .modelContainer(modelContainer)
    }
}
