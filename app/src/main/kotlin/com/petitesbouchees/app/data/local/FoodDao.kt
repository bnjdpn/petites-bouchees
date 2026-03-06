package com.petitesbouchees.app.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.petitesbouchees.app.data.model.Food
import com.petitesbouchees.app.data.model.FoodCategory
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

    @Insert
    suspend fun insert(food: Food): Long

    @Delete
    suspend fun delete(food: Food)

    @Query("SELECT COUNT(*) FROM foods")
    suspend fun count(): Int
}
