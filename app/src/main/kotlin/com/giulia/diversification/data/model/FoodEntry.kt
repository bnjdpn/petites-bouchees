package com.giulia.diversification.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "food_entries",
    foreignKeys = [ForeignKey(
        entity = Food::class,
        parentColumns = ["id"],
        childColumns = ["foodId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("foodId")]
)
data class FoodEntry(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val foodId: Int,
    val dateIntroduced: Long = System.currentTimeMillis(),
    val reaction: Reaction = Reaction.NEUTRAL,
    val hasDigestiveIssue: Boolean = false,
    val hasAllergicReaction: Boolean = false,
    val notes: String? = null
)
