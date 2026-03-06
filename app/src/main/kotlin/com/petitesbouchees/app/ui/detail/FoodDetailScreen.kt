package com.petitesbouchees.app.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.petitesbouchees.app.data.model.FoodCategory
import com.petitesbouchees.app.data.model.Reaction
import com.petitesbouchees.app.ui.theme.CategoryEpices
import com.petitesbouchees.app.ui.theme.CategoryFeculents
import com.petitesbouchees.app.ui.theme.CategoryFruits
import com.petitesbouchees.app.ui.theme.CategoryGraisses
import com.petitesbouchees.app.ui.theme.CategoryLaitiers
import com.petitesbouchees.app.ui.theme.CategoryLegumes
import com.petitesbouchees.app.ui.theme.CategoryProteines
import com.petitesbouchees.app.ui.theme.ReactionDisliked
import com.petitesbouchees.app.ui.theme.ReactionLiked
import com.petitesbouchees.app.ui.theme.ReactionNeutral
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

private fun getReactionColor(reaction: Reaction): Color = when (reaction) {
    Reaction.LIKED -> ReactionLiked
    Reaction.NEUTRAL -> ReactionNeutral
    Reaction.DISLIKED -> ReactionDisliked
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodDetailScreen(
    viewModel: FoodDetailViewModel,
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    var showDatePicker by remember { mutableStateOf(false) }

    LaunchedEffect(uiState.isSaved) {
        if (uiState.isSaved) onNavigateBack()
    }

    val food = uiState.food

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = food?.name ?: "",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Retour"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { paddingValues ->
        if (food != null) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                // Info section

                // Category chip
                Box(
                    modifier = Modifier
                        .background(
                            color = getCategoryColor(food.category).copy(alpha = 0.15f),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "${food.category.emoji} ${food.category.displayName}",
                        style = MaterialTheme.typography.labelLarge,
                        color = getCategoryColor(food.category),
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Recommended age
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Âge recommandé : dès ${food.recommendedAgeMonths} mois",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                // Food notes (preparation info)
                if (!food.notes.isNullOrBlank()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = food.notes,
                        style = MaterialTheme.typography.bodyMedium,
                        fontStyle = FontStyle.Italic,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(16.dp))

                // Introduction section
                Text(
                    text = "Introduction",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Date row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = formatDate(uiState.dateIntroduced),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    IconButton(onClick = { showDatePicker = true }) {
                        Icon(
                            imageVector = Icons.Default.CalendarToday,
                            contentDescription = "Choisir la date",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Reaction selector
                Text(
                    text = "Réaction",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Reaction.entries.forEach { reaction ->
                        val isSelected = uiState.reaction == reaction
                        FilledTonalButton(
                            onClick = { viewModel.setReaction(reaction) },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.filledTonalButtonColors(
                                containerColor = if (isSelected) {
                                    getReactionColor(reaction).copy(alpha = 0.3f)
                                } else {
                                    MaterialTheme.colorScheme.surfaceVariant
                                }
                            )
                        ) {
                            Text(
                                text = "${reaction.emoji} ${reaction.displayName}",
                                style = MaterialTheme.typography.labelMedium,
                                maxLines = 1
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Checkboxes
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { viewModel.toggleDigestiveIssue() },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = uiState.hasDigestiveIssue,
                        onCheckedChange = { viewModel.toggleDigestiveIssue() }
                    )
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = null,
                        tint = Color(0xFFFF9800),
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Problème digestif",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { viewModel.toggleAllergicReaction() },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = uiState.hasAllergicReaction,
                        onCheckedChange = { viewModel.toggleAllergicReaction() }
                    )
                    Icon(
                        imageVector = Icons.Default.Error,
                        contentDescription = null,
                        tint = Color(0xFFE53935),
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Réaction allergique",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Notes
                OutlinedTextField(
                    value = uiState.notes,
                    onValueChange = { viewModel.setNotes(it) },
                    label = { Text("Notes") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Save button
                Button(
                    onClick = { viewModel.save() },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(
                        text = "Enregistrer",
                        style = MaterialTheme.typography.labelLarge
                    )
                }

                // Delete button (only if already tested)
                if (uiState.isAlreadyTested) {
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedButton(
                        onClick = { viewModel.deleteEntry() },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colorScheme.error
                        )
                    ) {
                        Text(
                            text = "Supprimer l'entrée",
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }
            }

            // Date picker dialog
            if (showDatePicker) {
                val datePickerState = rememberDatePickerState(
                    initialSelectedDateMillis = uiState.dateIntroduced
                )
                DatePickerDialog(
                    onDismissRequest = { showDatePicker = false },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                datePickerState.selectedDateMillis?.let { millis ->
                                    viewModel.setDate(millis)
                                }
                                showDatePicker = false
                            }
                        ) {
                            Text("OK")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDatePicker = false }) {
                            Text("Annuler")
                        }
                    }
                ) {
                    DatePicker(state = datePickerState)
                }
            }
        }
    }
}
