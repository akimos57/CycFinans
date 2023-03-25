package ru.cyclone.cycfinans.presentation.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import ru.cyclone.cycfinans.presentation.navigation.AdditionalScreens
import ru.cyclone.cycfinans.presentation.navigation.Screens
import ru.cyclone.cycfinans.presentation.ui.components.FirstDayOfTheWeekChooser
import ru.cyclone.cycfinans.presentation.ui.components.SetCurrencyDialog
import ru.cyclone.cycfinans.presentation.ui.components.SetTimeFormatDialog
import ru.cyclone.cycfinans.presentation.ui.components.SettingsElement
import ru.cyclone.cycfinans.presentation.ui.theme.fab1
import ru.cyclone.cycnote.R

@Composable
fun SettingsScreen(navController: NavHostController) {
    val show = remember { mutableStateOf(false) }
    val showTime = remember { mutableStateOf(false) }
    val showCurrency = remember { mutableStateOf(false) }
    Column {
        Text(
            modifier = Modifier
                .padding(horizontal = 35.dp, vertical = 16.dp),
            text = "Настройки",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = fab1
        )
        if (show.value)
            FirstDayOfTheWeekChooser(show)
        if (showTime.value)
            SetTimeFormatDialog(showTime)
        if (showCurrency.value)
            SetCurrencyDialog(showCurrency)

        SettingsElement(
            title = "Choose First Day Of the Week",
            icon = R.drawable.settings,
            onClick = {
                show.value = true
            }
        )
        SettingsElement(
            title = "Формат времени",
            icon = R.drawable.time,
            onClick = {
                showTime.value = true
            }
        )
        SettingsElement(
            title = "Категории",
            icon = R.drawable.categories,
            onClick = {
                navController.navigate(AdditionalScreens.EditCategoriesListScreen.rout) {
                    popUpTo(Screens.SettingsScreen.rout) {
                        saveState = true
                    }
                    launchSingleTop = true
                }
            }
        )
        SettingsElement(
            title = "Язык",
            icon = R.drawable.language
        )
        SettingsElement(
            title = "Валюта",
            icon = R.drawable.dollar,
            onClick = {
                showCurrency.value = true
            }
        )
        SettingsElement(
            title = "Помочь разработчикам",
            icon = R.drawable.pay
        )
    }
}