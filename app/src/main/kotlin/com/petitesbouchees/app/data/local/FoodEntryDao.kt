package com.petitesbouchees.app.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.petitesbouchees.app.data.model.FoodEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodEntryDao {
    @Query("SELECT * FROM food_entries ORDER BY dateIntroduced DESC")
    fun getAllEntries(): Flow<List<FoodEntry>>

    @Query("SELECT * FROM food_entries WHERE foodId = :foodId ORDER BY dateIntroduced DESC LIMIT 1")
    fun getEntryForFood(foodId: Int): Flow<FoodEntry?>

    @Query("SELECT * FROM food_entries WHERE foodId = :foodId ORDER BY dateIntroduced DESC LIMIT 1")
    suspend fun getEntryForFoodOnce(foodId: Int): FoodEntry?

    @Query("SELECT DISTINCT foodId FROM food_entries")
    fun getTestedFoodIds(): Flow<List<Int>>

    @Query("SELECT COUNT(DISTINCT foodId) FROM food_entries")
    fun countTestedFoods(): Flow<Int>

    @Query("SELECT * FROM food_entries WHERE hasAllergicReaction = 1 OR hasDigestiveIssue = 1")
    fun getEntriesWithIssues(): Flow<List<FoodEntry>>

    @Insert
    suspend fun insert(entry: FoodEntry): Long

    @Update
    suspend fun update(entry: FoodEntry)

    @Delete
    suspend fun delete(entry: FoodEntry)
}
