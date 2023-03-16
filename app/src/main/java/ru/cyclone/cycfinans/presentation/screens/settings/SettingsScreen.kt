package ru.cyclone.cycfinans.presentation.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ru.cyclone.cycfinans.presentation.navigation.AdditionalScreens
import ru.cyclone.cycfinans.presentation.navigation.Screens

@Composable
fun SettingsScreen(navController: NavHostController) {
    Column {
        TextButton(
            onClick = {
                      navController.navigate(AdditionalScreens.SetCategoryScreen.rout) {
                          popUpTo(Screens.SettingsScreen.rout) {
                              saveState = true
                          }
                          launchSingleTop = true
                      }
            },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                textAlign = TextAlign.Start,
                text = "Set Category limits",
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp, horizontal = 10.dp)
            )
        }
        Spacer( modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(colorResource(id = ru.cyclone.cycnote.R.color.secondary_gray))
        )

        TextButton(
            onClick = {
                navController.navigate(AdditionalScreens.SetCategoryScreen.rout) {
                    popUpTo(Screens.SettingsScreen.rout) {
                        saveState = true
                    }
                    launchSingleTop = true
                }
            },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                textAlign = TextAlign.Start,
                text = "Income values",
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp, horizontal = 10.dp)
            )
        }
        Spacer( modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(colorResource(id = ru.cyclone.cycnote.R.color.secondary_gray))
        )
    }
}