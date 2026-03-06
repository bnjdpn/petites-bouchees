package com.petitesbouchees.app.ui.foods

import androidx.compose.animation.animateColorAsState
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.petitesbouchees.app.data.model.Food
import com.petitesbouchees.app.data.model.FoodCategory
import com.petitesbouchees.app.ui.theme.CategoryEpices
import com.petitesbouchees.app.ui.theme.CategoryFeculents
import com.petitesbouchees.app.ui.theme.CategoryFruits
import com.petitesbouchees.app.ui.theme.CategoryGraisses
import com.petitesbouchees.app.ui.theme.CategoryLaitiers
import com.petitesbouchees.app.ui.theme.CategoryLegumes
import com.petitesbouchees.app.ui.theme.CategoryProteines

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
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.showAddDialog() },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Ajouter un aliment"
                )
            }
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
                    label = { Text("Testes") }
                )
                FilterChip(
                    selected = uiState.showOnlyTested == false,
                    onClick = { viewModel.setTestedFilter(false) },
                    label = { Text("A tester") }
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
                            SwipeToDeleteFoodItem(
                                food = food,
                                isTested = food.id in uiState.testedFoodIds,
                                onClick = { onFoodClick(food.id) },
                                onDelete = { viewModel.requestDeleteFood(food) }
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

    // Add food dialog
    if (uiState.showAddDialog) {
        AddFoodDialog(
            onDismiss = { viewModel.hideAddDialog() },
            onConfirm = { name, category, age, notes ->
                viewModel.addFood(name, category, age, notes)
            }
        )
    }

    // Delete confirmation dialog
    uiState.foodToDelete?.let { food ->
        AlertDialog(
            onDismissRequest = { viewModel.cancelDeleteFood() },
            title = { Text("Supprimer l'aliment") },
            text = { Text("Supprimer \"${food.name}\" de la liste ? Les donnees associees seront aussi supprimees.") },
            confirmButton = {
                TextButton(onClick = { viewModel.confirmDeleteFood() }) {
                    Text("Supprimer", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.cancelDeleteFood() }) {
                    Text("Annuler")
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SwipeToDeleteFoodItem(
    food: Food,
    isTested: Boolean,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            if (value == SwipeToDismissBoxValue.EndToStart) {
                onDelete()
                false
            } else {
                false
            }
        }
    )

    SwipeToDismissBox(
        state = dismissState,
        backgroundContent = {
            val color by animateColorAsState(
                targetValue = if (dismissState.targetValue == SwipeToDismissBoxValue.EndToStart) {
                    MaterialTheme.colorScheme.errorContainer
                } else {
                    Color.Transparent
                },
                label = "swipe-bg"
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 2.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(color),
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Supprimer",
                    modifier = Modifier.padding(end = 20.dp),
                    tint = MaterialTheme.colorScheme.onErrorContainer
                )
            }
        },
        enableDismissFromStartToEnd = false
    ) {
        FoodItemCard(
            food = food,
            isTested = isTested,
            onClick = onClick
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddFoodDialog(
    onDismiss: () -> Unit,
    onConfirm: (name: String, category: FoodCategory, ageMonths: Int, notes: String?) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(FoodCategory.LEGUMES) }
    var ageMonths by remember { mutableIntStateOf(4) }
    var ageText by remember { mutableStateOf("4") }
    var notes by remember { mutableStateOf("") }
    var categoryExpanded by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Ajouter un aliment") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nom") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                ExposedDropdownMenuBox(
                    expanded = categoryExpanded,
                    onExpandedChange = { categoryExpanded = it }
                ) {
                    OutlinedTextField(
                        value = "${selectedCategory.emoji} ${selectedCategory.displayName}",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Categorie") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = categoryExpanded) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                    )
                    ExposedDropdownMenu(
                        expanded = categoryExpanded,
                        onDismissRequest = { categoryExpanded = false }
                    ) {
                        FoodCategory.entries.forEach { category ->
                            DropdownMenuItem(
                                text = { Text("${category.emoji} ${category.displayName}") },
                                onClick = {
                                    selectedCategory = category
                                    categoryExpanded = false
                                }
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = ageText,
                    onValueChange = { text ->
                        ageText = text
                        text.toIntOrNull()?.let { ageMonths = it }
                    },
                    label = { Text("Age recommande (mois)") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Notes (optionnel)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirm(name, selectedCategory, ageMonths, notes.ifBlank { null }) },
                enabled = name.isNotBlank() && ageMonths > 0
            ) {
                Text("Ajouter")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Annuler")
            }
        }
    )
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
                    text = "Des ${food.recommendedAgeMonths}m",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Tested indicator
            Icon(
                imageVector = if (isTested) Icons.Filled.CheckCircle else Icons.Outlined.Circle,
                contentDescription = if (isTested) "Teste" else "Non teste",
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
