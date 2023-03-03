package ru.cyclone.cycfinans.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ru.cyclone.cycfinans.presentation.screens.*
import ru.cyclone.cycfinans.presentation.screens.main.AddPromotion
import ru.cyclone.cycfinans.presentation.screens.main.MainDetailsScreen

sealed class Screens(
    val rout: String,
    val icon: ImageVector
) {
    object MainScreen: Screens(rout = "main_screen", icon = Icons.Filled.Home)
    object MainDetailsScreen: Screens(rout = "mainDetails_screen", icon = Icons.Filled.Home)
    object AddPromotionScreen: Screens(rout = "addPromotion_screen", icon = Icons.Filled.Home)
    object TargetScreen: Screens(rout = "target_screen", icon = Icons.Filled.Star)
    object TargetDetailsScreen: Screens(rout = "targetDetails_screen", icon = Icons.Filled.Home)
    object StatisticsScreen: Screens(rout = "statistics_screen", icon = Icons.Filled.DateRange)
    object SettingsScreen: Screens(rout = "settings_screen", icon = Icons.Filled.Settings)
}

@Composable
fun SetupNavHost(navController: NavHostController) {
    
    NavHost(
        navController = navController,
        startDestination = Screens.MainScreen.rout
    ) {
        composable(route = Screens.MainScreen.rout) {
            MainScreen(navController = navController)
        }
        composable(
            route = Screens.AddPromotionScreen.rout
//                    + "/{id}",
//            arguments = listOf(navArgument("id"){type = NavType.StringType}

        ) {
            AddPromotion(navController = navController)
//                it.arguments?.getString("id")


        }
        composable(route = Screens.MainDetailsScreen.rout) {
            MainDetailsScreen(navController = navController)
        }
        composable(route = Screens.TargetScreen.rout) {
            TargetScreen(navController = navController)
        }
        composable(route = Screens.TargetDetailsScreen.rout) {
            TargetDetailsScreen(navController = navController)
        }
        composable(route = Screens.StatisticsScreen.rout) {
            StatisticsScreen(navController = navController)
        }
        composable(route = Screens.SettingsScreen.rout) {
            SettingsScreen(navController = navController)
        }
    }
}