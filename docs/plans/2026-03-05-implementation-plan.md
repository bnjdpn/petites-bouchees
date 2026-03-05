# Diversification de Giulia - Implementation Plan

> **For Claude:** REQUIRED SUB-SKILL: Use superpowers:executing-plans to implement this plan task-by-task.

**Goal:** Build an Android app to track baby food diversification for Giulia with categories, age filtering, reaction tracking, and progression stats.

**Architecture:** MVVM with Kotlin + Jetpack Compose + Room. Single-module app with data/domain/ui layers. Room database prepopulated with ~120 foods from food-data.md. No network, no DI framework (manual DI to keep it simple).

**Tech Stack:** Kotlin, Jetpack Compose, Room, Material 3, Navigation Compose, Kotlin Coroutines/Flow

---

### Task 1: Scaffold Android Project

**Files:**
- Create: `settings.gradle.kts`
- Create: `build.gradle.kts` (project-level)
- Create: `gradle.properties`
- Create: `app/build.gradle.kts`
- Create: `app/src/main/AndroidManifest.xml`
- Create: `app/src/main/kotlin/com/giulia/diversification/MainActivity.kt`
- Create: `app/src/main/kotlin/com/giulia/diversification/DiversificationApp.kt`

**Step 1: Create project-level Gradle files**

`settings.gradle.kts`:
```kotlin
pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "DiversificationDeGiulia"
include(":app")
```

`build.gradle.kts`:
```kotlin
plugins {
    id("com.android.application") version "8.7.3" apply false
    id("org.jetbrains.kotlin.android") version "2.1.0" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.1.0" apply false
    id("com.google.devtools.ksp") version "2.1.0-1.0.29" apply false
}
```

`gradle.properties`:
```properties
org.gradle.jvmargs=-Xmx2048m -Dfile.encoding=UTF-8
android.useAndroidX=true
kotlin.code.style=official
android.nonTransitiveRClass=true
```

**Step 2: Create app/build.gradle.kts**

```kotlin
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.giulia.diversification"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.giulia.diversification"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    val composeBom = platform("androidx.compose:compose-bom:2024.12.01")
    implementation(composeBom)
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material-icons-extended")
    implementation("androidx.activity:activity-compose:1.9.3")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.7")
    implementation("androidx.navigation:navigation-compose:2.8.5")

    // Room
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")

    debugImplementation("androidx.compose.ui:ui-tooling")
}
```

**Step 3: Create AndroidManifest.xml**

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android">
    <application
        android:name=".DiversificationApp"
        android:label="Giulia"
        android:icon="@mipmap/ic_launcher"
        android:theme="@style/Theme.Material3.DayNight.NoActionBar"
        android:supportsRtl="true">
        <activity
            android:name=".MainActivity"
            android:exported="true"
            android:windowSoftInputMode="adjustResize">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>
</manifest>
```

**Step 4: Create Application class and MainActivity (minimal)**

`DiversificationApp.kt`:
```kotlin
package com.giulia.diversification

import android.app.Application

class DiversificationApp : Application()
```

`MainActivity.kt`:
```kotlin
package com.giulia.diversification

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.giulia.diversification.ui.theme.GiuliaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GiuliaTheme {
                // Navigation will go here
            }
        }
    }
}
```

**Step 5: Setup Gradle wrapper and verify build**

Run: `gradle wrapper --gradle-version 8.11.1` then `./gradlew assembleDebug`

**Step 6: Commit**
```bash
git init && git add -A && git commit -m "chore: scaffold Android project"
```

---

### Task 2: Material 3 Theme

**Files:**
- Create: `app/src/main/kotlin/com/giulia/diversification/ui/theme/Color.kt`
- Create: `app/src/main/kotlin/com/giulia/diversification/ui/theme/Theme.kt`
- Create: `app/src/main/kotlin/com/giulia/diversification/ui/theme/Type.kt`

**Step 1: Define color palette**

`Color.kt`:
```kotlin
package com.giulia.diversification.ui.theme

import androidx.compose.ui.graphics.Color

// Primary - Soft sage green (baby/natural feel)
val PrimaryLight = Color(0xFF4A7C59)
val OnPrimaryLight = Color(0xFFFFFFFF)
val PrimaryContainerLight = Color(0xFFCCE8D5)
val OnPrimaryContainerLight = Color(0xFF082F16)

// Secondary - Warm peach
val SecondaryLight = Color(0xFFB87351)
val OnSecondaryLight = Color(0xFFFFFFFF)
val SecondaryContainerLight = Color(0xFFFFDBC9)
val OnSecondaryContainerLight = Color(0xFF3A1500)

// Background
val BackgroundLight = Color(0xFFFCFDF7)
val OnBackgroundLight = Color(0xFF1A1C19)
val SurfaceLight = Color(0xFFFCFDF7)
val OnSurfaceLight = Color(0xFF1A1C19)
val SurfaceVariantLight = Color(0xFFE0E4DA)

// Category colors
val CategoryLegumes = Color(0xFF4CAF50)
val CategoryFruits = Color(0xFFFF9800)
val CategoryFeculents = Color(0xFFD4A574)
val CategoryProteines = Color(0xFFE57373)
val CategoryLaitiers = Color(0xFF64B5F6)
val CategoryGraisses = Color(0xFFFFD54F)
val CategoryEpices = Color(0xFFBA68C8)

// Reaction colors
val ReactionLiked = Color(0xFF66BB6A)
val ReactionNeutral = Color(0xFFFFCA28)
val ReactionDisliked = Color(0xFFEF5350)
```

**Step 2: Define typography**

`Type.kt`:
```kotlin
package com.giulia.diversification.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val Typography = Typography(
    headlineLarge = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 34.sp
    ),
    headlineMedium = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp,
        lineHeight = 28.sp
    ),
    titleLarge = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = 24.sp
    ),
    titleMedium = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 22.sp
    ),
    bodyLarge = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 22.sp
    ),
    bodyMedium = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    labelLarge = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp
    )
)
```

**Step 3: Create theme composable**

`Theme.kt`:
```kotlin
package com.giulia.diversification.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = PrimaryLight,
    onPrimary = OnPrimaryLight,
    primaryContainer = PrimaryContainerLight,
    onPrimaryContainer = OnPrimaryContainerLight,
    secondary = SecondaryLight,
    onSecondary = OnSecondaryLight,
    secondaryContainer = SecondaryContainerLight,
    onSecondaryContainer = OnSecondaryContainerLight,
    background = BackgroundLight,
    onBackground = OnBackgroundLight,
    surface = SurfaceLight,
    onSurface = OnSurfaceLight,
    surfaceVariant = SurfaceVariantLight,
)

@Composable
fun GiuliaTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}
```

**Step 4: Commit**
```bash
git add -A && git commit -m "feat: add Material 3 theme with pastel baby colors"
```

---

### Task 3: Data Layer - Room Entities and Database

**Files:**
- Create: `app/src/main/kotlin/com/giulia/diversification/data/model/FoodCategory.kt`
- Create: `app/src/main/kotlin/com/giulia/diversification/data/model/Reaction.kt`
- Create: `app/src/main/kotlin/com/giulia/diversification/data/model/Food.kt`
- Create: `app/src/main/kotlin/com/giulia/diversification/data/model/FoodEntry.kt`
- Create: `app/src/main/kotlin/com/giulia/diversification/data/local/FoodDao.kt`
- Create: `app/src/main/kotlin/com/giulia/diversification/data/local/FoodEntryDao.kt`
- Create: `app/src/main/kotlin/com/giulia/diversification/data/local/AppDatabase.kt`
- Create: `app/src/main/kotlin/com/giulia/diversification/data/local/Converters.kt`
- Create: `app/src/main/kotlin/com/giulia/diversification/data/local/PrepopulateData.kt`

**Step 1: Create enums**

`FoodCategory.kt`:
```kotlin
package com.giulia.diversification.data.model

import androidx.compose.ui.graphics.Color
import com.giulia.diversification.ui.theme.*

enum class FoodCategory(val displayName: String, val color: Color, val icon: String) {
    LEGUMES("Legumes", CategoryLegumes, "eco"),
    FRUITS("Fruits", CategoryFruits, "nutrition"),
    FECULENTS("Feculents", CategoryFeculents, "bakery_dining"),
    PROTEINES("Proteines", CategoryProteines, "set_meal"),
    PRODUITS_LAITIERS("Produits laitiers", CategoryLaitiers, "water_drop"),
    MATIERES_GRASSES("Matieres grasses", CategoryGraisses, "opacity"),
    EPICES_AROMATES("Epices & Aromates", CategoryEpices, "spa");
}
```

`Reaction.kt`:
```kotlin
package com.giulia.diversification.data.model

enum class Reaction(val displayName: String, val emoji: String) {
    LIKED("Aime", "\u2764\uFE0F"),
    NEUTRAL("Neutre", "\uD83D\uDE10"),
    DISLIKED("Pas aime", "\uD83D\uDE45");
}
```

**Step 2: Create Room entities**

`Food.kt`:
```kotlin
package com.giulia.diversification.data.model

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
```

`FoodEntry.kt`:
```kotlin
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
```

**Step 3: Create DAOs**

`FoodDao.kt`:
```kotlin
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
```

`FoodEntryDao.kt`:
```kotlin
package com.giulia.diversification.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.giulia.diversification.data.model.FoodEntry
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
```

**Step 4: Create type converters and database**

`Converters.kt`:
```kotlin
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
```

`AppDatabase.kt`:
```kotlin
package com.giulia.diversification.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.giulia.diversification.data.model.Food
import com.giulia.diversification.data.model.FoodEntry
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Food::class, FoodEntry::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun foodDao(): FoodDao
    abstract fun foodEntryDao(): FoodEntryDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "giulia_diversification.db"
                )
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        INSTANCE?.let { database ->
                            CoroutineScope(Dispatchers.IO).launch {
                                database.foodDao().insertAll(PrepopulateData.allFoods)
                            }
                        }
                    }
                })
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
```

**Step 5: Create prepopulate data from food-data.md**

`PrepopulateData.kt`: Contains all ~120 foods from the food database organized by category and recommended age. Each food is a `Food(name = "...", category = FoodCategory.XXX, recommendedAgeMonths = N, notes = "...")`.

Full data mapping:
- LEGUMES 4m: Carotte, Courgette, Haricot vert, Blanc de poireau, Potimarron, Patate douce, Potiron, Butternut, Panais
- LEGUMES 6m: Brocoli, Epinards, Petits pois, Aubergine, Tomate (notes: cuite/pelee/epepinee), Artichaut, Betterave, Fenouil, Navet, Celeri, Avocat, Concombre, Poivron
- LEGUMES 8m: Chou-fleur, Brocoli chinois, Chou blanc, Champignons, Oignon, Echalote, Ail
- FRUITS 4m: Pomme, Poire, Banane, Peche, Abricot, Coing
- FRUITS 6m: Mangue, Melon, Pasteque, Fraise, Framboise, Myrtille, Cerise (notes: denoyautee), Prune, Raisin (notes: pele/coupe), Orange, Clementine, Kiwi, Ananas, Papaye, Fruit de la passion, Nectarine, Figue
- FRUITS 12m: Noix, Noisettes, Amandes (notes: en poudre ou puree), Litchi
- FECULENTS 4m: Pomme de terre, Cereales infantiles sans gluten
- FECULENTS 6m: Ble, Semoule, Orge, Epeautre, Riz, Pates fines, Flocons d'avoine, Tapioca, Quinoa, Polenta
- FECULENTS 8m: Lentilles corail, Pois chiches, Haricots blancs, Pois casses, Lentilles vertes
- FECULENTS 12m: Pain, Biscottes, Cereales completes
- PROTEINES 6m: Poulet, Dinde, Boeuf, Veau, Porc, Agneau, Jambon blanc (notes: sans sel ajoute), Cabillaud, Colin, Merlu, Sole, Saumon, Lieu, Bar, Dorade, Truite, Oeuf (notes: bien cuit, jaune puis blanc)
- PROTEINES 12m: Charcuterie (notes: limitee), Fruits de mer (notes: cuits)
- PRODUITS_LAITIERS 6m: Yaourt nature, Fromage blanc nature, Petit-suisse nature
- PRODUITS_LAITIERS 8m: Gruyere, Emmental, Comte, Parmesan (notes: petites quantites), Brie pasteurise
- PRODUITS_LAITIERS 12m: Lait de vache entier, Fromages varies
- MATIERES_GRASSES 4m: Huile de colza, Huile d'olive, Huile de noix, Huile de tournesol, Beurre non sale, Creme fraiche
- EPICES_AROMATES 6m: Vanille, Cannelle, Cumin, Basilic, Persil, Ciboulette, Thym, Romarin, Menthe, Coriandre, Aneth, Estragon, Curry doux, Paprika doux, Muscade

**Step 6: Commit**
```bash
git add -A && git commit -m "feat: add Room database with entities, DAOs, and prepopulated food data"
```

---

### Task 4: Repository Layer

**Files:**
- Create: `app/src/main/kotlin/com/giulia/diversification/data/repository/FoodRepository.kt`

**Step 1: Create repository**

```kotlin
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
```

**Step 2: Commit**
```bash
git add -A && git commit -m "feat: add FoodRepository"
```

---

### Task 5: App-level DI and Application Setup

**Files:**
- Modify: `app/src/main/kotlin/com/giulia/diversification/DiversificationApp.kt`

**Step 1: Add manual DI in Application class**

```kotlin
package com.giulia.diversification

import android.app.Application
import com.giulia.diversification.data.local.AppDatabase
import com.giulia.diversification.data.repository.FoodRepository

class DiversificationApp : Application() {
    val database by lazy { AppDatabase.getInstance(this) }
    val repository by lazy { FoodRepository(database.foodDao(), database.foodEntryDao()) }
}
```

**Step 2: Commit**
```bash
git add -A && git commit -m "feat: add manual DI in Application class"
```

---

### Task 6: Food List Screen (Aliments Tab)

**Files:**
- Create: `app/src/main/kotlin/com/giulia/diversification/ui/foods/FoodsViewModel.kt`
- Create: `app/src/main/kotlin/com/giulia/diversification/ui/foods/FoodsScreen.kt`
- Create: `app/src/main/kotlin/com/giulia/diversification/ui/components/FoodCard.kt`
- Create: `app/src/main/kotlin/com/giulia/diversification/ui/components/CategoryChip.kt`
- Create: `app/src/main/kotlin/com/giulia/diversification/ui/components/AgeFilterRow.kt`

**Step 1: Create ViewModel**

`FoodsViewModel.kt`:
```kotlin
package com.giulia.diversification.ui.foods

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.giulia.diversification.data.model.Food
import com.giulia.diversification.data.model.FoodCategory
import com.giulia.diversification.data.repository.FoodRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class FoodsUiState(
    val foods: List<Food> = emptyList(),
    val testedFoodIds: Set<Int> = emptySet(),
    val selectedCategory: FoodCategory? = null,
    val selectedAgeFilter: Int? = null,  // null = all ages
    val showOnlyTested: Boolean? = null  // null=all, true=tested, false=not tested
)

class FoodsViewModel(private val repository: FoodRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(FoodsUiState())
    val uiState: StateFlow<FoodsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                repository.getAllFoods(),
                repository.getTestedFoodIds()
            ) { foods, testedIds ->
                Pair(foods, testedIds.toSet())
            }.collect { (foods, testedIds) ->
                _uiState.update { it.copy(foods = foods, testedFoodIds = testedIds) }
            }
        }
    }

    fun selectCategory(category: FoodCategory?) {
        _uiState.update { it.copy(selectedCategory = category) }
    }

    fun selectAgeFilter(ageMonths: Int?) {
        _uiState.update { it.copy(selectedAgeFilter = ageMonths) }
    }

    fun setTestedFilter(tested: Boolean?) {
        _uiState.update { it.copy(showOnlyTested = tested) }
    }

    val filteredFoods: StateFlow<List<Food>> = _uiState.map { state ->
        state.foods.filter { food ->
            val categoryMatch = state.selectedCategory == null || food.category == state.selectedCategory
            val ageMatch = state.selectedAgeFilter == null || food.recommendedAgeMonths <= state.selectedAgeFilter!!
            val testedMatch = when (state.showOnlyTested) {
                null -> true
                true -> food.id in state.testedFoodIds
                false -> food.id !in state.testedFoodIds
            }
            categoryMatch && ageMatch && testedMatch
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    class Factory(private val repository: FoodRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return FoodsViewModel(repository) as T
        }
    }
}
```

**Step 2: Create reusable components**

`CategoryChip.kt` - FilterChip for each FoodCategory with colored icon
`AgeFilterRow.kt` - Row of FilterChips: "Tous", "4m", "6m", "8m", "12m+"
`FoodCard.kt` - Card showing food name, age badge, tested/untested icon, category color bar on left

**Step 3: Create FoodsScreen**

`FoodsScreen.kt`:
- TopAppBar with title "Aliments"
- Category chips row (horizontal scrollable)
- Age filter row
- LazyColumn of FoodCards grouped by category (with sticky headers)
- Each FoodCard is clickable -> navigates to food detail
- Untested foods for ages beyond filter shown grayed out

**Step 4: Commit**
```bash
git add -A && git commit -m "feat: add Foods list screen with category and age filters"
```

---

### Task 7: Food Detail Screen

**Files:**
- Create: `app/src/main/kotlin/com/giulia/diversification/ui/detail/FoodDetailViewModel.kt`
- Create: `app/src/main/kotlin/com/giulia/diversification/ui/detail/FoodDetailScreen.kt`

**Step 1: Create ViewModel**

`FoodDetailViewModel.kt`:
```kotlin
// Loads Food by id + existing FoodEntry
// Exposes: food, entry (nullable), reaction, hasDigestive, hasAllergic, notes
// Methods: setReaction(), toggleDigestive(), toggleAllergic(), setNotes(), save(), removeEntry()
```

**Step 2: Create FoodDetailScreen**

Layout:
- Back arrow, food name as title
- Category chip with color
- "Age recommande: X mois" info row
- Food notes if any (e.g., "cuit, pele, epepine")
- Divider
- Section "Introduction":
  - Date picker (date de premiere introduction)
  - Reaction selector: 3 toggle buttons (Aime/Neutre/Pas aime) with emojis
  - Checkbox: Probleme digestif
  - Checkbox: Reaction allergique
  - TextField: Notes
- FAB or Button: "Enregistrer"
- If already tested: Button "Supprimer l'entree" in red

**Step 3: Commit**
```bash
git add -A && git commit -m "feat: add food detail screen with reaction tracking"
```

---

### Task 8: Journal Screen

**Files:**
- Create: `app/src/main/kotlin/com/giulia/diversification/ui/journal/JournalViewModel.kt`
- Create: `app/src/main/kotlin/com/giulia/diversification/ui/journal/JournalScreen.kt`

**Step 1: Create ViewModel**

Combines all FoodEntries with their Food data, sorted by date descending.

**Step 2: Create JournalScreen**

- TopAppBar: "Journal"
- LazyColumn of journal entries grouped by month/date
- Each entry shows: food name, category color dot, date, reaction emoji, warning icons for digestive/allergic
- Tap on entry -> navigate to food detail
- Empty state: "Aucun aliment introduit" with illustration prompt

**Step 3: Commit**
```bash
git add -A && git commit -m "feat: add Journal screen with chronological food entries"
```

---

### Task 9: Progression Screen

**Files:**
- Create: `app/src/main/kotlin/com/giulia/diversification/ui/progress/ProgressViewModel.kt`
- Create: `app/src/main/kotlin/com/giulia/diversification/ui/progress/ProgressScreen.kt`
- Create: `app/src/main/kotlin/com/giulia/diversification/ui/components/CategoryProgressBar.kt`

**Step 1: Create ViewModel**

Computes:
- Total foods count and tested count (overall %)
- Per-category: total and tested count
- Reaction breakdown: count liked/neutral/disliked
- List of foods with allergic or digestive issues

**Step 2: Create ProgressScreen**

Layout:
- Large circular progress indicator with "X% explore" at top
- "X/Y aliments testes"
- Section: progress bars per category (colored, with label and X/Y)
- Section: Reaction breakdown (horizontal bar chart or 3 cards)
- Section: "Alertes" - list of foods with issues (allergic icon red, digestive icon orange)

**Step 3: Commit**
```bash
git add -A && git commit -m "feat: add Progression screen with stats and alerts"
```

---

### Task 10: Navigation and Bottom Bar

**Files:**
- Create: `app/src/main/kotlin/com/giulia/diversification/ui/navigation/Navigation.kt`
- Create: `app/src/main/kotlin/com/giulia/diversification/ui/navigation/BottomNavBar.kt`
- Modify: `app/src/main/kotlin/com/giulia/diversification/MainActivity.kt`

**Step 1: Define navigation routes**

```kotlin
sealed class Screen(val route: String) {
    data object Foods : Screen("foods")
    data object Journal : Screen("journal")
    data object Progress : Screen("progress")
    data object FoodDetail : Screen("food/{foodId}") {
        fun createRoute(foodId: Int) = "food/$foodId"
    }
}
```

**Step 2: Create BottomNavBar**

3 items: Aliments (Restaurant icon), Journal (MenuBook icon), Progression (BarChart icon)

**Step 3: Create NavHost and wire up in MainActivity**

NavHost with 4 composable destinations. Bottom bar visible on main 3 screens, hidden on detail.

**Step 4: Commit**
```bash
git add -A && git commit -m "feat: add navigation with bottom bar"
```

---

### Task 11: Polish and Final Integration

**Step 1:** Verify all screens work together end-to-end
**Step 2:** Add edge cases: empty states, loading states
**Step 3:** Ensure date formatting uses French locale
**Step 4:** Final build verification: `./gradlew assembleDebug`

**Step 5: Commit**
```bash
git add -A && git commit -m "feat: polish UI and final integration"
```
