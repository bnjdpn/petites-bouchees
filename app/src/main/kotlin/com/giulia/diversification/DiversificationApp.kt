package com.giulia.diversification

import android.app.Application
import com.giulia.diversification.data.local.AppDatabase
import com.giulia.diversification.data.repository.FoodRepository

class DiversificationApp : Application() {
    val database by lazy { AppDatabase.getInstance(this) }
    val repository by lazy { FoodRepository(database.foodDao(), database.foodEntryDao()) }
}
