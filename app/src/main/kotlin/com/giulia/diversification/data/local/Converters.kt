package com.giulia.diversification.data.local

import androidx.room.TypeConverter
import com.giulia.diversification.data.model.FoodCategory
import com.giulia.diversification.data.model.Reaction

class Converters {
    @TypeConverter
    fun fromCategory(value: FoodCategory): String = value.name
    @TypeConverter
    fun toCategory(value: String): FoodCategory = FoodCategory.valueOf(value)
    @TypeConverter
    fun fromReaction(value: Reaction): String = value.name
    @TypeConverter
    fun toReaction(value: String): Reaction = Reaction.valueOf(value)
}
