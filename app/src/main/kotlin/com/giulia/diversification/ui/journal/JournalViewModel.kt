package com.giulia.diversification.ui.journal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.giulia.diversification.data.model.Food
import com.giulia.diversification.data.model.FoodEntry
import com.giulia.diversification.data.repository.FoodRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

data class JournalItem(
    val entry: FoodEntry,
    val food: Food
)

data class JournalUiState(
    val items: List<JournalItem> = emptyList(),
    val isLoading: Boolean = true
)

class JournalViewModel(private val repository: FoodRepository) : ViewModel() {
    val uiState: StateFlow<JournalUiState> = combine(
        repository.getAllEntries(),
        repository.getAllFoods()
    ) { entries, foods ->
        val foodMap = foods.associateBy { it.id }
        val items = entries.mapNotNull { entry ->
            foodMap[entry.foodId]?.let { food ->
                JournalItem(entry = entry, food = food)
            }
        }.sortedByDescending { it.entry.dateIntroduced }
        JournalUiState(items = items, isLoading = false)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), JournalUiState())

    class Factory(private val repository: FoodRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return JournalViewModel(repository) as T
        }
    }
}
