package ru.cyclone.cycfinans.presentation.navigation

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import ru.cyclone.cycfinans.data.local.preferences.PreferencesController
import ru.cyclone.cycfinans.presentation.screens.calendar.CalendarScreen
import ru.cyclone.cycfinans.presentation.screens.main.MainDetailsScreen
import ru.cyclone.cycfinans.presentation.screens.main.MainScreen
import ru.cyclone.cycfinans.presentation.screens.settings.EditCategoriesListScreen
import ru.cyclone.cycfinans.presentation.screens.settings.SetCategoryLimitsScreen
import ru.cyclone.cycfinans.presentation.screens.settings.SettingsScreen
import ru.cyclone.cycfinans.presentation.screens.statistics.StatisticsScreen
import ru.cyclone.cycfinans.presentation.ui.components.BottomNavigationBar
import ru.cyclone.cycnote.R
import java.util.*


sealed class Screens(
    val rout: String,
    val iconId: Int
) {
    object MainScreen: Screens(rout = "main_screen", iconId = R.drawable.home)
    object CalendarScreen: Screens(
        rout = "target_screen",
        iconId = R.drawable.ic_baseline_check_box_24
    )
    object StatisticsScreen: Screens(rout = "statistics_screen", iconId = R.drawable.data_usage)
    object SettingsScreen: Screens(rout = "settings_screen", iconId = R.drawable.settings)
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

    showNavigationBar = when (navBackStackEntry?.destination?.route) {
        Screens.MainScreen.rout -> true
        Screens.CalendarScreen.rout -> true
        Screens.StatisticsScreen.rout -> true
        Screens.SettingsScreen.rout -> true
        else -> false
    }

    val onReturned = remember { mutableStateOf({}) }
    var preferencesController = PreferencesController("currency_table")
    if (preferencesController.fileNameList.isEmpty()){
        preferencesController.fileNameList.add("$")
        preferencesController.saveLists()
    }
    preferencesController = PreferencesController("locale_table")
    if (preferencesController.fileNameList.isEmpty()){
        preferencesController.fileNameList.add("en")
        preferencesController.saveLists()
    }
    LocalContext.current.resources.apply {
        val locale = Locale.forLanguageTag(preferencesController.fileNameList.last())
        val config = Configuration(configuration)

        LocalContext.current.createConfigurationContext(configuration)
        Locale.setDefault(locale)
        config.setLocale(locale)
        @Suppress("DEPRECATION")
        LocalContext.current.resources.updateConfiguration(config, displayMetrics)
    }

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
                MainScreen(
                    navController = navController,
                    onReturned = onReturned
                )
            }
            composable(route = AdditionalScreens.MainDetailsScreen.rout +
                    "/{day}" +
                    "/{month}" +
                    "/{year}"
            ) {
                MainDetailsScreen(
                    navController = navController,
                    it.arguments?.getString("day"),
                    it.arguments?.getString("month"),
                    it.arguments?.getString("year"),
                    onReturned = onReturned
                )
            }
            composable(route = Screens.CalendarScreen.rout) {
                CalendarScreen(
                    navController = navController,
                    onReturned = onReturned
                )
            }
            composable(route = Screens.StatisticsScreen.rout +
                    "/{month}" +
                    "/{year}"
            ) {
                StatisticsScreen(
                    navController = navController,
                    it.arguments?.getString("month"),
                    it.arguments?.getString("year"),
                )
            }
            composable(route = Screens.StatisticsScreen.rout) {
                StatisticsScreen(
                    navController = navController,
                    month = it.arguments?.getString("month"),
                    year = it.arguments?.getString("year")
                )
            }
            composable(route = Screens.SettingsScreen.rout) {
                SettingsScreen(navController = navController)
            }
            composable(route = AdditionalScreens.SetCategoryLimitsScreen.rout) {
                SetCategoryLimitsScreen()
            }
            composable(route = AdditionalScreens.EditCategoriesListScreen.rout) {
                EditCategoriesListScreen(navController = navController)
            }
        }
    }
}