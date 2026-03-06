package com.petitesbouchees.app.ui.progress

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.petitesbouchees.app.data.model.FoodCategory
import com.petitesbouchees.app.data.model.Reaction
import com.petitesbouchees.app.data.repository.FoodRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

data class CategoryProgress(
    val category: FoodCategory,
    val total: Int,
    val tested: Int
)

data class AlertItem(
    val foodName: String,
    val hasDigestiveIssue: Boolean,
    val hasAllergicReaction: Boolean
)

data class ProgressUiState(
    val totalFoods: Int = 0,
    val testedFoods: Int = 0,
    val categoryProgress: List<CategoryProgress> = emptyList(),
    val likedCount: Int = 0,
    val neutralCount: Int = 0,
    val dislikedCount: Int = 0,
    val alerts: List<AlertItem> = emptyList(),
    val isLoading: Boolean = true
) {
    val progressPercent: Float
        get() = if (totalFoods > 0) testedFoods.toFloat() / totalFoods else 0f
}

class ProgressViewModel(private val repository: FoodRepository) : ViewModel() {
    val uiState: StateFlow<ProgressUiState> = combine(
        repository.getAllFoods(),
        repository.getAllEntries(),
        repository.getEntriesWithIssues()
    ) { foods, entries, issueEntries ->
        val foodMap = foods.associateBy { it.id }
        val testedFoodIds = entries.map { it.foodId }.toSet()

        val categoryProgress = FoodCategory.entries.map { category ->
            val total = foods.count { it.category == category }
            val tested = entries.count { entry -> foodMap[entry.foodId]?.category == category }
            CategoryProgress(category, total, tested)
        }

        val alerts = issueEntries.mapNotNull { entry ->
            foodMap[entry.foodId]?.let { food ->
                AlertItem(food.name, entry.hasDigestiveIssue, entry.hasAllergicReaction)
            }
        }

        ProgressUiState(
            totalFoods = foods.size,
            testedFoods = testedFoodIds.size,
            categoryProgress = categoryProgress,
            likedCount = entries.count { it.reaction == Reaction.LIKED },
            neutralCount = entries.count { it.reaction == Reaction.NEUTRAL },
            dislikedCount = entries.count { it.reaction == Reaction.DISLIKED },
            alerts = alerts,
            isLoading = false
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ProgressUiState())

    class Factory(private val repository: FoodRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ProgressViewModel(repository) as T
        }
    }
}
