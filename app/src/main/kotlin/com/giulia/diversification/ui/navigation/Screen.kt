package com.giulia.diversification.ui.navigation

sealed class Screen(val route: String) {
    data object Foods : Screen("foods")
    data object Journal : Screen("journal")
    data object Progress : Screen("progress")
    data object FoodDetail : Screen("food/{foodId}") {
        fun createRoute(foodId: Int) = "food/$foodId"
    }
}
