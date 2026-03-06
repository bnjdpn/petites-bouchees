package com.petitesbouchees.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "foods")
data class Food(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val category: FoodCategory,
    val recommendedAgeMonths: Int,
    val notes: String? = null
)
