package ru.cyclone.cycfinans.presentation.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import ru.cyclone.cycfinans.presentation.navigation.AdditionalScreens
import ru.cyclone.cycfinans.presentation.navigation.Screens
import ru.cyclone.cycfinans.presentation.ui.components.FirstDayOfTheWeekChooser
import ru.cyclone.cycfinans.presentation.ui.components.SettingsElement
import ru.cyclone.cycnote.R

@Composable
fun SettingsScreen(navController: NavHostController) {
    Column {
//        TextButton(
//            onClick = {
//                      navController.navigate(AdditionalScreens.SetCategoryScreen.rout) {
//                          popUpTo(Screens.SettingsScreen.rout) {
//                              saveState = true
//                          }
//                          launchSingleTop = true
//                      }
//            },
//            modifier = Modifier
//                .fillMaxWidth()
//        ) {
//            Text(
//                textAlign = TextAlign.Start,
//                text = "Set Category limits",
//                fontSize = 16.sp,
//                fontWeight = FontWeight.Normal,
//                color = MaterialTheme.colors.onSurface,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 10.dp, horizontal = 10.dp)
//            )
//        }
//        Spacer( modifier = Modifier
//            .fillMaxWidth()
//            .height(0.5.dp)
//            .background(colorResource(id = ru.cyclone.cycnote.R.color.secondary_gray))
//        )
//
//        TextButton(
//            onClick = {
//                navController.navigate(AdditionalScreens.SetCategoryScreen.rout) {
//                    popUpTo(Screens.SettingsScreen.rout) {
//                        saveState = true
//                    }
//                    launchSingleTop = true
//                }
//            },
//            modifier = Modifier
//                .fillMaxWidth()
//        ) {
//            Text(
//                textAlign = TextAlign.Start,
//                text = "Income values",
//                fontSize = 16.sp,
//                fontWeight = FontWeight.Normal,
//                color = MaterialTheme.colors.onSurface,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 10.dp, horizontal = 10.dp)
//            )
//        }
//        Spacer( modifier = Modifier
//            .fillMaxWidth()
//            .height(0.5.dp)
//            .background(colorResource(id = ru.cyclone.cycnote.R.color.secondary_gray))
//        )
//
        FirstDayOfTheWeekChooser()
        Spacer( modifier = Modifier
            .fillMaxWidth()
            .height(0.5.dp)
            .background(colorResource(id = ru.cyclone.cycnote.R.color.secondary_gray))
        )
        SettingsElement(
            title = "Установить лимиты на категории",
            icon = R.drawable.settings,
            modifier = Modifier
                .clickable {
                    navController.navigate(AdditionalScreens.SetCategoryScreen.rout) {
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
                .clickable {

                }
        )
        SettingsElement(
            title = "Настроечкиand",
            icon = R.drawable.settings,
            modifier = Modifier
                .clickable {
                    navController.navigate(AdditionalScreens.SetCategoryScreen.rout) {
                        popUpTo(Screens.SettingsScreen.rout) {
                            saveState = true
                        }
                        launchSingleTop = true
                    }
                }
        )
    }
}