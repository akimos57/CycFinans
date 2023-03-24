package ru.cyclone.cycfinans.presentation.navigation

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import ru.cyclone.cycfinans.presentation.screens.calendar.CalendarScreen
import ru.cyclone.cycfinans.presentation.screens.main.MainDetailsScreen
import ru.cyclone.cycfinans.presentation.screens.main.MainScreen
import ru.cyclone.cycfinans.presentation.screens.settings.EditCategoriesListScreen
import ru.cyclone.cycfinans.presentation.screens.settings.SetCategoryLimitsScreen
import ru.cyclone.cycfinans.presentation.screens.settings.SettingsScreen
import ru.cyclone.cycfinans.presentation.screens.statistics.StatisticsScreen
import ru.cyclone.cycfinans.presentation.ui.components.BottomNavigationBar
import ru.cyclone.cycnote.R


sealed class Screens(
    val titleId: Int,
    val rout: String,
    val iconId: Int
) {
    object MainScreen: Screens(rout = "main_screen", iconId = R.drawable.home, titleId = R.string.main)
    object CalendarScreen: Screens(rout = "target_screen", iconId = R.drawable.dashboard, titleId = R.string.calendar)
    object StatisticsScreen: Screens(rout = "statistics_screen", iconId = R.drawable.data_usage, titleId = R.string.statistics)
    object SettingsScreen: Screens(rout = "settings_screen", iconId = R.drawable.settings, titleId = R.string.settings)
}

sealed class AdditionalScreens(
    val rout: String
) {
    object MainDetailsScreen: AdditionalScreens(rout = "mainDetails_screen")
    object SetCategoryLimitsScreen: AdditionalScreens(rout = "set_category_limits_screen")
    object EditCategoriesListScreen: AdditionalScreens(rout = "edit_categories_screen")
}

@Composable
fun SetupNavHost(navController: NavHostController) {
    var showNavigationBar by remember { mutableStateOf(true) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val s = stringResource(id = R.string.main)

    showNavigationBar = when (navBackStackEntry?.destination?.route) {
        Screens.MainScreen.rout -> true
        Screens.CalendarScreen.rout -> true
        Screens.StatisticsScreen.rout -> true
        Screens.SettingsScreen.rout -> true
        else -> false
    }

    val onReturned = remember { mutableStateOf({}) }

    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController, showNavigationBar) }
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
            composable(route = AdditionalScreens.MainDetailsScreen.rout + "/{day}/{month}/{year}") {
                MainDetailsScreen(
                    navController = navController,
                    it.arguments?.getString("day"),
                    it.arguments?.getString("month"),
                    it.arguments?.getString("year"),
                    onReturned
                )
            }
            composable(route = Screens.CalendarScreen.rout) {
                CalendarScreen(
                    navController = navController,
                    onReturned
                )
            }
            composable(route = Screens.StatisticsScreen.rout) {
                StatisticsScreen(navController = navController)
            }
            composable(route = Screens.SettingsScreen.rout) {
                SettingsScreen(navController = navController)
            }
            composable(route = AdditionalScreens.SetCategoryLimitsScreen.rout) {
                SetCategoryLimitsScreen()
            }
            composable(route = AdditionalScreens.EditCategoriesListScreen.rout) {
                EditCategoriesListScreen()
            }
        }
    }
}