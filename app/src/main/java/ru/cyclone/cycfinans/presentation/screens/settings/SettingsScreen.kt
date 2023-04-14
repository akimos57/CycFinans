package ru.cyclone.cycfinans.presentation.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import ru.cyclone.cycfinans.presentation.navigation.AdditionalScreens
import ru.cyclone.cycfinans.presentation.navigation.Screens
import ru.cyclone.cycfinans.presentation.ui.components.SettingsElement
import ru.cyclone.cycfinans.presentation.ui.theme.fab1
import ru.cyclone.cycnote.R

@Composable
fun SettingsScreen(navController: NavHostController) {
    Column {
        Text(
            modifier = Modifier
                .padding(horizontal = 35.dp, vertical = 16.dp),
            text = stringResource(id = R.string.settings),
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = fab1
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
            title = stringResource(id = R.string.locale_settings),
            icon = R.drawable.globe,
            onClick = {
                navController.navigate(AdditionalScreens.LocaleSettingsScreen.rout) {
                    popUpTo(Screens.SettingsScreen.rout) {
                        saveState = true
                    }
                    launchSingleTop = true
                }
            }
        )
        SettingsElement(
            title = stringResource(id = R.string.about_us),
            icon = R.drawable.groups
        )
    }
}