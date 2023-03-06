package ru.cyclone.cycfinans.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.cyclone.cycfinans.presentation.components.BottomNavigationBar
import ru.cyclone.cycfinans.presentation.screens.*
import ru.cyclone.cycfinans.presentation.screens.main.AddPromotion
import ru.cyclone.cycfinans.presentation.screens.main.MainDetailsScreen
import ru.cyclone.cycfinans.presentation.screens.target.AddTarget
import ru.cyclone.cycfinans.presentation.screens.target.TargetScreen
import ru.cyclone.cycnote.R

sealed class Screens(
    val title: String,
    val rout: String,
    val iconId: Int
) {
    object MainScreen: Screens(rout = "main_screen", iconId = R.drawable.home, title = "Главная")
//    object MainDetailsScreen: Screens(rout = "mainDetails_screen", iconId = Icons.Filled.Home)
//    object AddPromotionScreen: Screens(rout = "addPromotion_screen", iconId = Icons.Filled.Home)
    object TargetScreen: Screens(rout = "target_screen", iconId = R.drawable.star, title = "Цели")
//    object TargetDetailsScreen: Screens(rout = "targetDetails_screen", iconId = Icons.Filled.Home)
    object StatisticsScreen: Screens(rout = "statistics_screen", iconId = R.drawable.data_usage, title = "Статистика")
    object SettingsScreen: Screens(rout = "settings_screen", iconId = R.drawable.settings, title = "Настройки")
}

sealed class AdditionalScreens(
    val rout: String
) {
    object MainDetailsScreen: AdditionalScreens(rout = "mainDetails_screen")
    object AddPromotionScreen: AdditionalScreens(rout = "addPromotion_screen")
    object AddTargetScreen: AdditionalScreens(rout = "targetDetails_screen")
}

@Composable
fun SetupNavHost(navController: NavHostController) {
    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screens.MainScreen.rout,
            modifier = Modifier
                .padding(paddingValues)
        ) {
            composable(route = Screens.MainScreen.rout) {
                MainScreen(navController = navController)
            }
            composable(
                route = AdditionalScreens.AddPromotionScreen.rout
//                    + "/{id}",
//            arguments = listOf(navArgument("id"){type = NavType.StringType}

            ) {
                AddPromotion(navController = navController, type = true)
//                it.arguments?.getString("id")


            }
            composable(route = AdditionalScreens.MainDetailsScreen.rout) {
                MainDetailsScreen(navController = navController)
            }
            composable(route = Screens.TargetScreen.rout) {
                TargetScreen(navController = navController)
            }
            composable(route = AdditionalScreens.AddTargetScreen.rout) {
                AddTarget(navController = navController)
            }
            composable(route = Screens.StatisticsScreen.rout) {
                StatisticsScreen(navController = navController)
            }
            composable(route = Screens.SettingsScreen.rout) {
                SettingsScreen(navController = navController)
            }
        }
    }
}