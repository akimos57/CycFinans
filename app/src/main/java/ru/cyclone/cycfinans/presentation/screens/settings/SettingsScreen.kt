package ru.cyclone.cycfinans.presentation.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ru.cyclone.cycfinans.presentation.navigation.AdditionalScreens
import ru.cyclone.cycfinans.presentation.navigation.Screens
import ru.cyclone.cycfinans.presentation.ui.components.FirstDayOfTheWeekChooser
import ru.cyclone.cycfinans.presentation.ui.components.SettingsElement
import ru.cyclone.cycnote.R

@Composable
fun SettingsScreen(navController: NavHostController) {
    Column {
        FirstDayOfTheWeekChooser()
        Spacer( modifier = Modifier
            .fillMaxWidth()
            .height(0.5.dp)
            .background(colorResource(id = R.color.secondary_gray))
        )
        SettingsElement(
            title = "Установить лимиты на категории",
            icon = R.drawable.settings,
            modifier = Modifier
                .clickable {
                    navController.navigate(AdditionalScreens.SetCategoryLimitsScreen.rout) {
                        popUpTo(Screens.SettingsScreen.rout) {
                            saveState = true
                        }
                        launchSingleTop = true
                    }
                }
        )
        SettingsElement(
            title = "Первый день недели",
            icon = R.drawable.settings,
            modifier = Modifier
                .clickable {}
        )
        SettingsElement(
            title = "Настроечкиand",
            icon = R.drawable.settings,
            modifier = Modifier
                .clickable {
                    navController.navigate(AdditionalScreens.SetCategoryLimitsScreen.rout) {
                        popUpTo(Screens.SettingsScreen.rout) {
                            saveState = true
                        }
                        launchSingleTop = true
                    }
                }
        )
        SettingsElement(
            title = "EditCategoriesList",
            icon = R.drawable.settings,
            modifier = Modifier
                .clickable {
                    navController.navigate(AdditionalScreens.EditCategoriesListScreen.rout) {
                        popUpTo(Screens.SettingsScreen.rout) {
                            saveState = true
                        }
                        launchSingleTop = true
                    }
                }
        )
    }
}