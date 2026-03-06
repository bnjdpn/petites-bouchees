import Testing
@testable import PetitesBouchees

@Suite("Reaction")
struct ReactionTests {
    @Test("Three reactions exist")
    func threeReactions() {
        #expect(Reaction.allCases.count == 3)
    }

    @Test("All reactions have display name and emoji")
    func allReactionsHaveDisplayInfo() {
        for reaction in Reaction.allCases {
            #expect(!reaction.displayName.isEmpty)
            #expect(!reaction.emoji.isEmpty)
        }
    }
}
