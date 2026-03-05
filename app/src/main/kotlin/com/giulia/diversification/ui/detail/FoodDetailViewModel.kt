package com.giulia.diversification.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.giulia.diversification.data.model.Food
import com.giulia.diversification.data.model.FoodEntry
import com.giulia.diversification.data.model.Reaction
import com.giulia.diversification.data.repository.FoodRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class FoodDetailUiState(
    val food: Food? = null,
    val isAlreadyTested: Boolean = false,
    val dateIntroduced: Long = System.currentTimeMillis(),
    val reaction: Reaction = Reaction.NEUTRAL,
    val hasDigestiveIssue: Boolean = false,
    val hasAllergicReaction: Boolean = false,
    val notes: String = "",
    val isSaved: Boolean = false
)

class FoodDetailViewModel(
    private val repository: FoodRepository,
    private val foodId: Int
) : ViewModel() {
    private val _uiState = MutableStateFlow(FoodDetailUiState())
    val uiState: StateFlow<FoodDetailUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val food = repository.getFoodById(foodId)
            _uiState.update { it.copy(food = food) }
        }
        viewModelScope.launch {
            repository.getEntryForFood(foodId).collect { entry ->
                if (entry != null) {
                    _uiState.update {
                        it.copy(
                            isAlreadyTested = true,
                            dateIntroduced = entry.dateIntroduced,
                            reaction = entry.reaction,
                            hasDigestiveIssue = entry.hasDigestiveIssue,
                            hasAllergicReaction = entry.hasAllergicReaction,
                            notes = entry.notes ?: ""
                        )
                    }
                }
            }
        }
    }

    fun setReaction(reaction: Reaction) {
        _uiState.update { it.copy(reaction = reaction) }
    }

    fun setDate(timestamp: Long) {
        _uiState.update { it.copy(dateIntroduced = timestamp) }
    }

    fun toggleDigestiveIssue() {
        _uiState.update { it.copy(hasDigestiveIssue = !it.hasDigestiveIssue) }
    }

    fun toggleAllergicReaction() {
        _uiState.update { it.copy(hasAllergicReaction = !it.hasAllergicReaction) }
    }

    fun setNotes(notes: String) {
        _uiState.update { it.copy(notes = notes) }
    }

    fun save() {
        viewModelScope.launch {
            val state = _uiState.value
            val entry = FoodEntry(
                foodId = foodId,
                dateIntroduced = state.dateIntroduced,
                reaction = state.reaction,
                hasDigestiveIssue = state.hasDigestiveIssue,
                hasAllergicReaction = state.hasAllergicReaction,
                notes = state.notes.ifBlank { null }
            )
            repository.saveEntry(entry)
            _uiState.update { it.copy(isSaved = true) }
        }
    }

    fun deleteEntry() {
        viewModelScope.launch {
            val entry = repository.getEntryForFoodOnce(foodId)
            if (entry != null) {
                repository.deleteEntry(entry)
                _uiState.update {
                    FoodDetailUiState(food = it.food)
                }
            }
        }
    }

    class Factory(private val repository: FoodRepository, private val foodId: Int) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return FoodDetailViewModel(repository, foodId) as T
        }
    }
}
