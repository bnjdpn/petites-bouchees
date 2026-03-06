import SwiftUI
import SwiftData

struct ProgressScreen: View {
    @Environment(\.modelContext) private var modelContext
    @State private var viewModel: ProgressViewModel?

    var body: some View {
        Group {
            if let viewModel {
                ProgressContentView(viewModel: viewModel)
            } else {
                ProgressView()
            }
        }
        .navigationTitle("Progression")
        .task {
            if viewModel == nil {
                let vm = ProgressViewModel(modelContext: modelContext)
                vm.fetchProgress()
                viewModel = vm
            }
        }
    }
}

private struct ProgressContentView: View {
    let viewModel: ProgressViewModel

    var body: some View {
        ScrollView {
            VStack(spacing: 24) {
                circularProgress
                categorySection
                reactionsSection
                if !viewModel.alerts.isEmpty {
                    alertsSection
                }
            }
            .padding()
        }
    }

    private var circularProgress: some View {
        ZStack {
            Circle()
                .stroke(Color.surfaceVariant, lineWidth: 16)

            Circle()
                .trim(from: 0, to: viewModel.progressPercent)
                .stroke(
                    Color.primaryGreen,
                    style: StrokeStyle(lineWidth: 16, lineCap: .round)
                )
                .rotationEffect(.degrees(-90))
                .animation(.easeInOut(duration: 0.8), value: viewModel.progressPercent)

            VStack(spacing: 4) {
                Text("\(Int(viewModel.progressPercent * 100))%")
                    .font(.title)
                    .fontWeight(.bold)
                    .foregroundStyle(Color.primaryGreen)

                Text("\(viewModel.testedFoods)/\(viewModel.totalFoods) test\u{00E9}s")
                    .font(.caption)
                    .foregroundStyle(.secondary)
            }
        }
        .frame(width: 180, height: 180)
        .padding(.vertical, 8)
    }

    private var categorySection: some View {
        VStack(alignment: .leading, spacing: 12) {
            Text("Par cat\u{00E9}gorie")
                .font(.headline)

            ForEach(viewModel.categoryProgress) { progress in
                HStack(spacing: 12) {
                    Text(progress.category.emoji)
                        .font(.title3)

                    Text(progress.category.displayName)
                        .font(.subheadline)
                        .frame(width: 100, alignment: .leading)

                    GeometryReader { geo in
                        ZStack(alignment: .leading) {
                            RoundedRectangle(cornerRadius: 4)
                                .fill(Color.surfaceVariant)
                                .frame(height: 8)

                            if progress.total > 0 {
                                RoundedRectangle(cornerRadius: 4)
                                    .fill(progress.category.color)
                                    .frame(
                                        width: geo.size.width * CGFloat(progress.tested) / CGFloat(progress.total),
                                        height: 8
                                    )
                                    .animation(.easeInOut(duration: 0.5), value: progress.tested)
                            }
                        }
                    }
                    .frame(height: 8)

                    Text("\(progress.tested)/\(progress.total)")
                        .font(.caption)
                        .foregroundStyle(.secondary)
                        .frame(width: 40, alignment: .trailing)
                }
            }
        }
    }

    private var reactionsSection: some View {
        VStack(alignment: .leading, spacing: 12) {
            Text("R\u{00E9}actions")
                .font(.headline)

            HStack(spacing: 12) {
                reactionCard(
                    emoji: Reaction.liked.emoji,
                    count: viewModel.likedCount,
                    label: Reaction.liked.displayName,
                    color: Reaction.liked.color
                )

                reactionCard(
                    emoji: Reaction.neutral.emoji,
                    count: viewModel.neutralCount,
                    label: Reaction.neutral.displayName,
                    color: Reaction.neutral.color
                )

                reactionCard(
                    emoji: Reaction.disliked.emoji,
                    count: viewModel.dislikedCount,
                    label: Reaction.disliked.displayName,
                    color: Reaction.disliked.color
                )
            }
        }
    }

    private func reactionCard(emoji: String, count: Int, label: String, color: Color) -> some View {
        VStack(spacing: 6) {
            Text(emoji)
                .font(.title2)
            Text("\(count)")
                .font(.title3)
                .fontWeight(.bold)
            Text(label)
                .font(.caption)
                .foregroundStyle(.secondary)
        }
        .frame(maxWidth: .infinity)
        .padding(.vertical, 12)
        .background(color.opacity(0.15))
        .clipShape(.rect(cornerRadius: 12))
    }

    private var alertsSection: some View {
        VStack(alignment: .leading, spacing: 12) {
            Text("Alertes")
                .font(.headline)

            ForEach(viewModel.alerts) { alert in
                HStack(spacing: 12) {
                    if alert.hasAllergicReaction {
                        Image(systemName: "exclamationmark.circle.fill")
                            .foregroundStyle(.red)
                    }
                    if alert.hasDigestiveIssue {
                        Image(systemName: "exclamationmark.triangle.fill")
                            .foregroundStyle(.orange)
                    }

                    Text(alert.foodName)
                        .font(.subheadline)

                    Spacer()
                }
                .padding(12)
                .background(Color.surfaceVariant.opacity(0.3))
                .clipShape(.rect(cornerRadius: 8))
            }
        }
    }
}
