package com.giulia.diversification.ui.journal

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.giulia.diversification.data.model.FoodCategory
import com.giulia.diversification.ui.theme.CategoryEpices
import com.giulia.diversification.ui.theme.CategoryFeculents
import com.giulia.diversification.ui.theme.CategoryFruits
import com.giulia.diversification.ui.theme.CategoryGraisses
import com.giulia.diversification.ui.theme.CategoryLaitiers
import com.giulia.diversification.ui.theme.CategoryLegumes
import com.giulia.diversification.ui.theme.CategoryProteines
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

private val dateFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.FRENCH)

private fun formatDate(timestamp: Long): String {
    return Instant.ofEpochMilli(timestamp)
        .atZone(ZoneId.systemDefault())
        .format(dateFormatter)
}

private fun getCategoryColor(category: FoodCategory): Color = when (category) {
    FoodCategory.LEGUMES -> CategoryLegumes
    FoodCategory.FRUITS -> CategoryFruits
    FoodCategory.FECULENTS -> CategoryFeculents
    FoodCategory.PROTEINES -> CategoryProteines
    FoodCategory.PRODUITS_LAITIERS -> CategoryLaitiers
    FoodCategory.MATIERES_GRASSES -> CategoryGraisses
    FoodCategory.EPICES_AROMATES -> CategoryEpices
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun JournalScreen(
    viewModel: JournalViewModel,
    onFoodClick: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Journal") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        if (!uiState.isLoading && uiState.items.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Aucun aliment introduit pour le moment",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Commencez par explorer les aliments !",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                    )
                }
            }
        } else {
            val groupedItems = uiState.items.groupBy { formatDate(it.entry.dateIntroduced) }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                groupedItems.forEach { (date, items) ->
                    stickyHeader {
                        Text(
                            text = date,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.background)
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                    }
                    items(items, key = { it.entry.id }) { item ->
                        JournalEntryCard(
                            item = item,
                            onClick = { onFoodClick(item.food.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun JournalEntryCard(
    item: JournalItem,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Category color circle
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .clip(CircleShape)
                    .background(getCategoryColor(item.food.category))
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Food name and category
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.food.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${item.food.category.emoji} ${item.food.category.displayName}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Warning icons
            if (item.entry.hasDigestiveIssue) {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = "Problème digestif",
                    tint = Color(0xFFFF9800),
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
            }
            if (item.entry.hasAllergicReaction) {
                Icon(
                    imageVector = Icons.Default.Error,
                    contentDescription = "Réaction allergique",
                    tint = Color(0xFFE53935),
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
            }

            // Reaction emoji
            Text(
                text = item.entry.reaction.emoji,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}
