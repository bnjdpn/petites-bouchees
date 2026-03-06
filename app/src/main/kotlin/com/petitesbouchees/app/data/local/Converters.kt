package com.petitesbouchees.app.data.local

import androidx.room.TypeConverter
import com.petitesbouchees.app.data.model.FoodCategory
import com.petitesbouchees.app.data.model.Reaction

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
