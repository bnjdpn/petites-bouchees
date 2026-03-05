package com.giulia.diversification.data.repository

import com.giulia.diversification.data.local.FoodDao
import com.giulia.diversification.data.local.FoodEntryDao
import com.giulia.diversification.data.model.Food
import com.giulia.diversification.data.model.FoodCategory
import com.giulia.diversification.data.model.FoodEntry
import kotlinx.coroutines.flow.Flow

class FoodRepository(
    private val foodDao: FoodDao,
    private val foodEntryDao: FoodEntryDao
) {
    fun getAllFoods(): Flow<List<Food>> = foodDao.getAllFoods()
    fun getFoodsByCategory(category: FoodCategory): Flow<List<Food>> = foodDao.getFoodsByCategory(category)
    fun getFoodsForAge(ageMonths: Int): Flow<List<Food>> = foodDao.getFoodsForAge(ageMonths)
    suspend fun getFoodById(foodId: Int): Food? = foodDao.getFoodById(foodId)

    fun getAllEntries(): Flow<List<FoodEntry>> = foodEntryDao.getAllEntries()
    fun getEntryForFood(foodId: Int): Flow<FoodEntry?> = foodEntryDao.getEntryForFood(foodId)
    suspend fun getEntryForFoodOnce(foodId: Int): FoodEntry? = foodEntryDao.getEntryForFoodOnce(foodId)
    fun getTestedFoodIds(): Flow<List<Int>> = foodEntryDao.getTestedFoodIds()
    fun countTestedFoods(): Flow<Int> = foodEntryDao.countTestedFoods()
    fun getEntriesWithIssues(): Flow<List<FoodEntry>> = foodEntryDao.getEntriesWithIssues()

    suspend fun saveEntry(entry: FoodEntry) {
        val existing = foodEntryDao.getEntryForFoodOnce(entry.foodId)
        if (existing != null) {
            foodEntryDao.update(entry.copy(id = existing.id))
        } else {
            foodEntryDao.insert(entry)
        }
    }

    suspend fun deleteEntry(entry: FoodEntry) = foodEntryDao.delete(entry)
}
