package com.petitesbouchees.app.ui.foods

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.petitesbouchees.app.data.model.Food
import com.petitesbouchees.app.data.model.FoodCategory
import com.petitesbouchees.app.data.repository.FoodRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class FoodsUiState(
    val foods: List<Food> = emptyList(),
    val testedFoodIds: Set<Int> = emptySet(),
    val selectedCategory: FoodCategory? = null,
    val selectedAgeFilter: Int? = null,
    val showOnlyTested: Boolean? = null,
    val showAddDialog: Boolean = false,
    val foodToDelete: Food? = null
)

class FoodsViewModel(private val repository: FoodRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(FoodsUiState())
    val uiState: StateFlow<FoodsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                repository.getAllFoods(),
                repository.getTestedFoodIds()
            ) { foods, testedIds ->
                Pair(foods, testedIds.toSet())
            }.collect { (foods, testedIds) ->
                _uiState.update { it.copy(foods = foods, testedFoodIds = testedIds) }
            }
        }
    }

    fun selectCategory(category: FoodCategory?) {
        _uiState.update {
            it.copy(selectedCategory = if (it.selectedCategory == category) null else category)
        }
    }

    fun selectAgeFilter(ageMonths: Int?) {
        _uiState.update {
            it.copy(selectedAgeFilter = if (it.selectedAgeFilter == ageMonths) null else ageMonths)
        }
    }

    fun setTestedFilter(tested: Boolean?) {
        _uiState.update {
            it.copy(showOnlyTested = if (it.showOnlyTested == tested) null else tested)
        }
    }

    fun showAddDialog() {
        _uiState.update { it.copy(showAddDialog = true) }
    }

    fun hideAddDialog() {
        _uiState.update { it.copy(showAddDialog = false) }
    }

    fun addFood(name: String, category: FoodCategory, ageMonths: Int, notes: String?) {
        viewModelScope.launch {
            repository.addFood(
                Food(
                    name = name.trim(),
                    category = category,
                    recommendedAgeMonths = ageMonths,
                    notes = notes?.trim()?.ifBlank { null }
                )
            )
            _uiState.update { it.copy(showAddDialog = false) }
        }
    }

    fun requestDeleteFood(food: Food) {
        _uiState.update { it.copy(foodToDelete = food) }
    }

    fun cancelDeleteFood() {
        _uiState.update { it.copy(foodToDelete = null) }
    }

    fun confirmDeleteFood() {
        val food = _uiState.value.foodToDelete ?: return
        viewModelScope.launch {
            repository.deleteFood(food)
            _uiState.update { it.copy(foodToDelete = null) }
        }
    }

    val filteredFoods: StateFlow<List<Food>> = _uiState.map { state ->
        state.foods.filter { food ->
            val categoryMatch =
                state.selectedCategory == null || food.category == state.selectedCategory
            val ageMatch =
                state.selectedAgeFilter == null || food.recommendedAgeMonths <= state.selectedAgeFilter
            val testedMatch = when (state.showOnlyTested) {
                null -> true
                true -> food.id in state.testedFoodIds
                false -> food.id !in state.testedFoodIds
            }
            categoryMatch && ageMatch && testedMatch
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    class Factory(private val repository: FoodRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return FoodsViewModel(repository) as T
        }
    }
}
