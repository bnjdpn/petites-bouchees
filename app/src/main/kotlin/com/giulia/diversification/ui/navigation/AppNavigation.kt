package com.giulia.diversification.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.giulia.diversification.DiversificationApp
import com.giulia.diversification.ui.detail.FoodDetailScreen
import com.giulia.diversification.ui.detail.FoodDetailViewModel
import com.giulia.diversification.ui.foods.FoodsScreen
import com.giulia.diversification.ui.foods.FoodsViewModel
import com.giulia.diversification.ui.journal.JournalScreen
import com.giulia.diversification.ui.journal.JournalViewModel
import com.giulia.diversification.ui.progress.ProgressScreen
import com.giulia.diversification.ui.progress.ProgressViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val context = LocalContext.current
    val app = context.applicationContext as DiversificationApp
    val repository = app.repository

    val showBottomBar = currentRoute in listOf(Screen.Foods.route, Screen.Journal.route, Screen.Progress.route)

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNavBar(
                    currentRoute = currentRoute,
                    onNavigate = { screen ->
                        navController.navigate(screen.route) {
                            popUpTo(Screen.Foods.route) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Foods.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Foods.route) {
                val viewModel: FoodsViewModel = viewModel(factory = FoodsViewModel.Factory(repository))
                FoodsScreen(
                    viewModel = viewModel,
                    onFoodClick = { foodId ->
                        navController.navigate(Screen.FoodDetail.createRoute(foodId))
                    }
                )
            }

            composable(Screen.Journal.route) {
                val viewModel: JournalViewModel = viewModel(factory = JournalViewModel.Factory(repository))
                JournalScreen(
                    viewModel = viewModel,
                    onFoodClick = { foodId ->
                        navController.navigate(Screen.FoodDetail.createRoute(foodId))
                    }
                )
            }

            composable(Screen.Progress.route) {
                val viewModel: ProgressViewModel = viewModel(factory = ProgressViewModel.Factory(repository))
                ProgressScreen(viewModel = viewModel)
            }

            composable(
                route = Screen.FoodDetail.route,
                arguments = listOf(navArgument("foodId") { type = NavType.IntType })
            ) { backStackEntry ->
                val foodId = backStackEntry.arguments?.getInt("foodId") ?: return@composable
                val viewModel: FoodDetailViewModel = viewModel(
                    factory = FoodDetailViewModel.Factory(repository, foodId)
                )
                FoodDetailScreen(
                    viewModel = viewModel,
                    onNavigateBack = { navController.popBackStack() }
                )
            }
        }
    }
}
