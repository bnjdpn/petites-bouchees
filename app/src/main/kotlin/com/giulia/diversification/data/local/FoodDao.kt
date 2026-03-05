package com.giulia.diversification.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.giulia.diversification.data.model.Food
import com.giulia.diversification.data.model.FoodCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {
    @Query("SELECT * FROM foods ORDER BY category, name")
    fun getAllFoods(): Flow<List<Food>>

    @Query("SELECT * FROM foods WHERE category = :category ORDER BY name")
    fun getFoodsByCategory(category: FoodCategory): Flow<List<Food>>

    @Query("SELECT * FROM foods WHERE recommendedAgeMonths <= :ageMonths ORDER BY category, name")
    fun getFoodsForAge(ageMonths: Int): Flow<List<Food>>

    @Query("SELECT * FROM foods WHERE id = :foodId")
    suspend fun getFoodById(foodId: Int): Food?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(foods: List<Food>)

    @Query("SELECT COUNT(*) FROM foods")
    suspend fun count(): Int
}
