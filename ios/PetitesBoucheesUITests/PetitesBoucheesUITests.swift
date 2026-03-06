import XCTest

@MainActor
final class PetitesBoucheesUITests: XCTestCase {
    var app: XCUIApplication!

    override func setUpWithError() throws {
        continueAfterFailure = false
        app = XCUIApplication()
        app.launchArguments = ["-screenshot"]
        setupSnapshot(app)
        app.launch()
    }

    func test01FoodsScreen() {
        let tabBar = app.tabBars.firstMatch
        _ = tabBar.waitForExistence(timeout: 5)
        snapshot("01_Aliments")
    }

    func test02JournalScreen() {
        let tabBar = app.tabBars.firstMatch
        _ = tabBar.waitForExistence(timeout: 5)
        tabBar.buttons.element(boundBy: 1).tap()
        sleep(1)
        snapshot("02_Journal")
    }

    func test03ProgressScreen() {
        let tabBar = app.tabBars.firstMatch
        _ = tabBar.waitForExistence(timeout: 5)
        tabBar.buttons.element(boundBy: 2).tap()
        sleep(1)
        snapshot("03_Progression")
    }

    func test04FoodDetail() {
        let tabBar = app.tabBars.firstMatch
        _ = tabBar.waitForExistence(timeout: 5)
        let firstCell = app.cells.firstMatch
        if firstCell.waitForExistence(timeout: 5) {
            firstCell.tap()
            sleep(1)
            snapshot("04_Detail")
        }
    }
}
