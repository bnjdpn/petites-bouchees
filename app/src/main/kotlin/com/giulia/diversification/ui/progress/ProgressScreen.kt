package com.giulia.diversification.ui.progress

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.giulia.diversification.data.model.FoodCategory
import com.giulia.diversification.ui.theme.CategoryEpices
import com.giulia.diversification.ui.theme.CategoryFeculents
import com.giulia.diversification.ui.theme.CategoryFruits
import com.giulia.diversification.ui.theme.CategoryGraisses
import com.giulia.diversification.ui.theme.CategoryLaitiers
import com.giulia.diversification.ui.theme.CategoryLegumes
import com.giulia.diversification.ui.theme.CategoryProteines
import com.giulia.diversification.ui.theme.ReactionDisliked
import com.giulia.diversification.ui.theme.ReactionLiked
import com.giulia.diversification.ui.theme.ReactionNeutral

private fun getCategoryColor(category: FoodCategory): Color = when (category) {
    FoodCategory.LEGUMES -> CategoryLegumes
    FoodCategory.FRUITS -> CategoryFruits
    FoodCategory.FECULENTS -> CategoryFeculents
    FoodCategory.PROTEINES -> CategoryProteines
    FoodCategory.PRODUITS_LAITIERS -> CategoryLaitiers
    FoodCategory.MATIERES_GRASSES -> CategoryGraisses
    FoodCategory.EPICES_AROMATES -> CategoryEpices
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgressScreen(viewModel: ProgressViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Progression",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { paddingValues ->
        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                // Section 1: Overall Progress
                OverallProgressSection(uiState)

                Spacer(modifier = Modifier.height(24.dp))

                // Section 2: Progress by Category
                CategoryProgressSection(uiState.categoryProgress)

                Spacer(modifier = Modifier.height(24.dp))

                // Section 3: Reactions
                ReactionsSection(
                    likedCount = uiState.likedCount,
                    neutralCount = uiState.neutralCount,
                    dislikedCount = uiState.dislikedCount
                )

                // Section 4: Alerts
                if (uiState.alerts.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(24.dp))
                    AlertsSection(uiState.alerts)
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun OverallProgressSection(uiState: ProgressUiState) {
    val animatedProgress by animateFloatAsState(
        targetValue = uiState.progressPercent,
        animationSpec = tween(durationMillis = 800),
        label = "progress"
    )
    val percentText = (animatedProgress * 100).toInt()

    val primaryColor = MaterialTheme.colorScheme.primary
    val trackColor = MaterialTheme.colorScheme.surfaceVariant

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(180.dp)
    ) {
        Canvas(modifier = Modifier.size(180.dp)) {
            // Background track
            drawArc(
                color = trackColor,
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = 14.dp.toPx(), cap = StrokeCap.Round)
            )
            // Progress arc
            drawArc(
                color = primaryColor,
                startAngle = -90f,
                sweepAngle = animatedProgress * 360f,
                useCenter = false,
                style = Stroke(width = 14.dp.toPx(), cap = StrokeCap.Round)
            )
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "$percentText%",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "${uiState.testedFoods}/${uiState.totalFoods} aliments testes",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun CategoryProgressSection(categoryProgress: List<CategoryProgress>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Par categorie",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        categoryProgress.forEach { progress ->
            CategoryProgressRow(progress)
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
private fun CategoryProgressRow(progress: CategoryProgress) {
    val fraction = if (progress.total > 0) {
        progress.tested.toFloat() / progress.total
    } else {
        0f
    }
    val animatedFraction by animateFloatAsState(
        targetValue = fraction,
        animationSpec = tween(durationMillis = 600),
        label = "category_progress"
    )
    val categoryColor = getCategoryColor(progress.category)

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = progress.category.emoji,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.width(28.dp)
        )
        Text(
            text = progress.category.displayName,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.width(120.dp),
            maxLines = 1
        )
        LinearProgressIndicator(
            progress = { animatedFraction },
            modifier = Modifier
                .weight(1f)
                .height(8.dp),
            color = categoryColor,
            trackColor = categoryColor.copy(alpha = 0.15f),
            strokeCap = StrokeCap.Round
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "${progress.tested}/${progress.total}",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.width(36.dp),
            textAlign = TextAlign.End
        )
    }
}

@Composable
private fun ReactionsSection(likedCount: Int, neutralCount: Int, dislikedCount: Int) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Reactions",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ReactionCard(
                emoji = "\u2764\uFE0F",
                count = likedCount,
                label = "Aimes",
                backgroundColor = ReactionLiked.copy(alpha = 0.15f),
                contentColor = ReactionLiked,
                modifier = Modifier.weight(1f)
            )
            ReactionCard(
                emoji = "\uD83D\uDE10",
                count = neutralCount,
                label = "Neutres",
                backgroundColor = ReactionNeutral.copy(alpha = 0.15f),
                contentColor = ReactionNeutral,
                modifier = Modifier.weight(1f)
            )
            ReactionCard(
                emoji = "\uD83D\uDE45",
                count = dislikedCount,
                label = "Pas aimes",
                backgroundColor = ReactionDisliked.copy(alpha = 0.15f),
                contentColor = ReactionDisliked,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun ReactionCard(
    emoji: String,
    count: Int,
    label: String,
    backgroundColor: Color,
    contentColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = emoji,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "$count",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = contentColor
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = contentColor
            )
        }
    }
}

@Composable
private fun AlertsSection(alerts: List<AlertItem>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Alertes",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        alerts.forEach { alert ->
            AlertCard(alert)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun AlertCard(alert: AlertItem) {
    val backgroundColor = if (alert.hasAllergicReaction) {
        ReactionDisliked.copy(alpha = 0.1f)
    } else {
        Color(0xFFFFF3E0) // Light orange
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = alert.foodName,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f)
            )
            if (alert.hasDigestiveIssue) {
                Icon(
                    imageVector = Icons.Filled.WarningAmber,
                    contentDescription = "Probleme digestif",
                    tint = Color(0xFFFF9800),
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
            if (alert.hasAllergicReaction) {
                Icon(
                    imageVector = Icons.Filled.ErrorOutline,
                    contentDescription = "Reaction allergique",
                    tint = ReactionDisliked,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}
