package com.petitesbouchees.app

import android.app.Application
import com.petitesbouchees.app.data.local.AppDatabase
import com.petitesbouchees.app.data.repository.FoodRepository

class DiversificationApp : Application() {
    val database by lazy { AppDatabase.getInstance(this) }
    val repository by lazy { FoodRepository(database.foodDao(), database.foodEntryDao()) }
}
