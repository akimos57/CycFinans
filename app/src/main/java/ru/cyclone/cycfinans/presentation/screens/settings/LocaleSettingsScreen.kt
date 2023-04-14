package ru.cyclone.cycfinans.presentation.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import ru.cyclone.cycfinans.presentation.ui.components.SettingsElement
import ru.cyclone.cycfinans.presentation.ui.components.settings.selectors.CurrencySelector
import ru.cyclone.cycfinans.presentation.ui.components.settings.selectors.FirstDayOfTheWeekSelector
import ru.cyclone.cycfinans.presentation.ui.components.settings.selectors.LanguageSelector
import ru.cyclone.cycfinans.presentation.ui.components.settings.selectors.TimeFormatSelector
import ru.cyclone.cycnote.R

@Composable
fun LocaleSettingsScreen() {
    val showFirstDayOfTheWeekSelector = remember { mutableStateOf(false) }
    val showSetTimeFormatSelector = remember { mutableStateOf(false) }
    val showCurrencySelector = remember { mutableStateOf(false) }
    val showLanguageSelector = remember { mutableStateOf(false) }

    if (showFirstDayOfTheWeekSelector.value)
        FirstDayOfTheWeekSelector(showFirstDayOfTheWeekSelector)
    if (showSetTimeFormatSelector.value)
        TimeFormatSelector(showSetTimeFormatSelector)
    if (showCurrencySelector.value)
        CurrencySelector(showCurrencySelector)
    if (showLanguageSelector.value)
        LanguageSelector(showLanguageSelector)

    Column {
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
    }
}