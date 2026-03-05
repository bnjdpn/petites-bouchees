package com.giulia.diversification.ui.foods

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
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
import com.giulia.diversification.data.model.Food
import com.giulia.diversification.data.model.FoodCategory
import com.giulia.diversification.ui.theme.CategoryEpices
import com.giulia.diversification.ui.theme.CategoryFeculents
import com.giulia.diversification.ui.theme.CategoryFruits
import com.giulia.diversification.ui.theme.CategoryGraisses
import com.giulia.diversification.ui.theme.CategoryLaitiers
import com.giulia.diversification.ui.theme.CategoryLegumes
import com.giulia.diversification.ui.theme.CategoryProteines

fun getCategoryColor(category: FoodCategory): Color = when (category) {
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
fun FoodsScreen(
    viewModel: FoodsViewModel,
    onFoodClick: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val filteredFoods by viewModel.filteredFoods.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Aliments",
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Category filter chips
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FoodCategory.entries.forEach { category ->
                    FilterChip(
                        selected = uiState.selectedCategory == category,
                        onClick = { viewModel.selectCategory(category) },
                        label = {
                            Text("${category.emoji} ${category.displayName}")
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = getCategoryColor(category).copy(alpha = 0.2f),
                            selectedLabelColor = getCategoryColor(category)
                        )
                    )
                }
            }

            // Age filter chips
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val ageFilters = listOf(4, 6, 8, 9, 12)
                ageFilters.forEach { age ->
                    val label = if (age == 12) "12m+" else "${age}m"
                    FilterChip(
                        selected = uiState.selectedAgeFilter == age,
                        onClick = { viewModel.selectAgeFilter(age) },
                        label = { Text(label) }
                    )
                }
            }

            // Tested filter chips
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = uiState.showOnlyTested == null,
                    onClick = { viewModel.setTestedFilter(null) },
                    label = { Text("Tous") }
                )
                FilterChip(
                    selected = uiState.showOnlyTested == true,
                    onClick = { viewModel.setTestedFilter(true) },
                    label = { Text("Testés") }
                )
                FilterChip(
                    selected = uiState.showOnlyTested == false,
                    onClick = { viewModel.setTestedFilter(false) },
                    label = { Text("À tester") }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (filteredFoods.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Aucun aliment",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                val groupedFoods = filteredFoods.groupBy { it.category }

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    groupedFoods.forEach { (category, foods) ->
                        stickyHeader {
                            CategoryHeader(category)
                        }
                        items(foods, key = { it.id }) { food ->
                            FoodItemCard(
                                food = food,
                                isTested = food.id in uiState.testedFoodIds,
                                onClick = { onFoodClick(food.id) }
                            )
                        }
                        item {
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CategoryHeader(category: FoodCategory) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = category.emoji,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = category.displayName,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = getCategoryColor(category)
        )
    }
}

@Composable
private fun FoodItemCard(
    food: Food,
    isTested: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 2.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left colored bar
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .height(56.dp)
                    .clip(RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp))
                    .background(getCategoryColor(food.category))
            )

            // Food info
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 12.dp, vertical = 10.dp)
            ) {
                Text(
                    text = food.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
                if (!food.notes.isNullOrBlank()) {
                    Text(
                        text = food.notes,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1
                    )
                }
            }

            // Age badge
            Box(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "Dès ${food.recommendedAgeMonths}m",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Tested indicator
            Icon(
                imageVector = if (isTested) Icons.Filled.CheckCircle else Icons.Outlined.Circle,
                contentDescription = if (isTested) "Testé" else "Non testé",
                modifier = Modifier
                    .padding(end = 12.dp)
                    .size(24.dp),
                tint = if (isTested) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.outlineVariant
                }
            )
        }
    }
}
