package ru.cyclone.cycfinans.presentation.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import ru.cyclone.cycfinans.presentation.navigation.AdditionalScreens
import ru.cyclone.cycfinans.presentation.navigation.Screens
import ru.cyclone.cycfinans.presentation.ui.components.*
import ru.cyclone.cycfinans.presentation.ui.theme.fab1
import ru.cyclone.cycnote.R

@Composable
fun SettingsScreen(navController: NavHostController) {
    val showFirstDayOfTheWeekSelector = remember { mutableStateOf(false) }
    val showSetTimeFormatSelector = remember { mutableStateOf(false) }
    val showCurrencySelector = remember { mutableStateOf(false) }
    val showLanguageSelector = remember { mutableStateOf(false) }
    Column {
        Text(
            modifier = Modifier
                .padding(horizontal = 35.dp, vertical = 16.dp),
            text = stringResource(id = R.string.settings),
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = fab1
        )
        if (showFirstDayOfTheWeekSelector.value)
            FirstDayOfTheWeekSelector(showFirstDayOfTheWeekSelector)
        if (showSetTimeFormatSelector.value)
            TimeFormatSelector(showSetTimeFormatSelector)
        if (showCurrencySelector.value)
            CurrencySelector(showCurrencySelector)
        if (showLanguageSelector.value)
            LanguageSelector(showLanguageSelector)

        SettingsElement(
            title = stringResource(id = R.string.first_day_of_the_week),
            icon = R.drawable.sun,
            onClick = {
                showFirstDayOfTheWeekSelector.value = true
            }
        )
        SettingsElement(
            title = stringResource(id = R.string.time_format),
            icon = R.drawable.time,
            onClick = {
                showSetTimeFormatSelector.value = true
            }
        )
        SettingsElement(
            title = stringResource(id = R.string.categories),
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
            title = stringResource(id = R.string.language),
            icon = R.drawable.language,
            onClick = {
                showLanguageSelector.value = true
            }
        )
        SettingsElement(
            title = stringResource(id = R.string.currency),
            icon = R.drawable.dollar,
            onClick = {
                showCurrencySelector.value = true
            }
        )
        SettingsElement(
            title = stringResource(id = R.string.about_us),
            icon = R.drawable.groups
        )
    }
}