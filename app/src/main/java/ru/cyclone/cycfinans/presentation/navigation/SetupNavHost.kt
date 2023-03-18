package ru.cyclone.cycfinans.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import ru.cyclone.cycfinans.presentation.screens.calendar.AddNote
import ru.cyclone.cycfinans.presentation.screens.calendar.CalendarScreen
import ru.cyclone.cycfinans.presentation.screens.main.MainDetailsScreen
import ru.cyclone.cycfinans.presentation.screens.main.MainScreen
import ru.cyclone.cycfinans.presentation.screens.settings.SetCategoryScreen
import ru.cyclone.cycfinans.presentation.screens.settings.SettingsScreen
import ru.cyclone.cycfinans.presentation.screens.statistics.StatisticsScreen
import ru.cyclone.cycfinans.presentation.ui.components.BottomNavigationBar
import ru.cyclone.cycnote.R

sealed class Screens(
    val title: String,
    val rout: String,
    val iconId: Int
) {
    object MainScreen: Screens(rout = "main_screen", iconId = R.drawable.home, title = "Главная")
    object CalendarScreen: Screens(rout = "target_screen", iconId = R.drawable.dashboard, title = "Календарь")
    object StatisticsScreen: Screens(rout = "statistics_screen", iconId = R.drawable.data_usage, title = "Статистика")
    object SettingsScreen: Screens(rout = "settings_screen", iconId = R.drawable.settings, title = "Настройки")
}

sealed class AdditionalScreens(
    val rout: String
) {
    object MainDetailsScreen: AdditionalScreens(rout = "mainDetails_screen")
    object AddNoteScreen: AdditionalScreens(rout = "add_note_screen")
    object SetCategoryScreen: AdditionalScreens(rout = "set_category")
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
            composable(route = AdditionalScreens.AddNoteScreen.rout + "{id}/{content}") {
                AddNote(
                    navController = navController,
                    noteId = it.arguments?.getString("id"),
                    noteContent = it.arguments?.getString("content"),
                    timeSting = it.arguments?.getString("time"),
                    onReturned = onReturned
                )
            }
            composable(route = AdditionalScreens.AddNoteScreen.rout) {
                AddNote(
                    navController = navController,
                    noteId = it.arguments?.getString("id"),
                    noteContent = it.arguments?.getString("content"),
                    timeSting = it.arguments?.getString("time"),
                    onReturned = onReturned
                )
            }
            composable(route = AdditionalScreens.AddNoteScreen.rout + "{id}/{content}/{time}") {
                AddNote(
                    navController = navController,
                    noteId = it.arguments?.getString("id"),
                    noteContent = it.arguments?.getString("content"),
                    timeSting = it.arguments?.getString("time"),
                    onReturned = onReturned
                )
            }
            composable(route = AdditionalScreens.AddNoteScreen.rout + "{time}") {
                AddNote(
                    navController = navController,
                    noteId = it.arguments?.getString("id"),
                    noteContent = it.arguments?.getString("content"),
                    timeSting = it.arguments?.getString("time"),
                    onReturned = onReturned
                )
            }
            composable(route = Screens.StatisticsScreen.rout) {
                StatisticsScreen(navController = navController)
            }
            composable(route = Screens.SettingsScreen.rout) {
                SettingsScreen(navController = navController)
            }
            composable(route = AdditionalScreens.SetCategoryScreen.rout) {
                SetCategoryScreen()
            }
        }
    }
}