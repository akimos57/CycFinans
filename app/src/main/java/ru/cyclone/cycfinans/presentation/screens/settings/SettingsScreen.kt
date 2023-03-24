package ru.cyclone.cycfinans.presentation.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import ru.cyclone.cycfinans.data.local.preferences.PreferencesController
import ru.cyclone.cycfinans.presentation.navigation.AdditionalScreens
import ru.cyclone.cycfinans.presentation.navigation.Screens
import ru.cyclone.cycfinans.presentation.ui.components.FirstDayOfTheWeekChooser
import ru.cyclone.cycfinans.presentation.ui.components.SettingsElement
import ru.cyclone.cycfinans.presentation.ui.theme.fab1
import ru.cyclone.cycfinans.presentation.ui.theme.switchDark
import ru.cyclone.cycnote.R
import java.time.DayOfWeek

@Composable
fun SettingsScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
    ) {
//        SettingsElement(
//            title = "Установить лимиты на категории",
//            icon = R.drawable.settings,
//            modifier = Modifier
//                .clickable {
//                    navController.navigate(AdditionalScreens.SetCategoryLimitsScreen.rout) {
//                        popUpTo(Screens.SettingsScreen.rout) {
//                            saveState = true
//                        }
//                        launchSingleTop = true
//                    }
//                }
//        click = {navController.navigate(AdditionalScreens.SetCategoryLimitsScreen.rout) {
//                        popUpTo(Screens.SettingsScreen.rout) {
//                            saveState = true
//                        }
//                        launchSingleTop = true
//                    }
//                }
//        )
        Text(
            modifier = Modifier
                .padding(horizontal = 35.dp, vertical = 16.dp),
            text = "Настройки",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = fab1
        )

        val preferencesController = PreferencesController("firstDayOfWeek_table")
        var show by remember { mutableStateOf(false) }
        SettingsElement(
            title = "Первый день недели",
            icon = R.drawable.sun,
            click = { show = true }
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.5.dp)
                .background(colorResource(id = R.color.secondary_gray))
        )
        if (show) {
            Dialog(onDismissRequest = { show = false }) {
                Box(
                    modifier = Modifier
                        .width(200.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colors.secondary)
                ) {
                    Column {
                        for (i in 1..7) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        preferencesController.fileNameList =
                                            mutableListOf((i).toString())
                                        preferencesController.saveLists()

                                        show = false
                                    }
                            ) {
                                Text(
                                    modifier = Modifier
                                        .padding(
                                            horizontal = 20.dp,
                                            vertical = 12.dp
                                        ),
                                    text = DayOfWeek.of(i).name
                                )
                            }
                        }
                    }
                }
            }
        }
        var showTime by remember { mutableStateOf(false) }
        SettingsElement(
            title = "Формат времени",
            icon = R.drawable.time,
            click = {
                showTime = true
            }
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(0.5.dp)
                .background(colorResource(id = R.color.secondary_gray))
        )
        if (showTime) {
            Dialog(onDismissRequest = { showTime = false }) {
                Box(
                    modifier = Modifier
                        .width(200.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colors.secondary)
                ) {
                    Column() {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {

                                }
                        ) {
                            Text(
                                text = "12-hour 1:00 PM",
                                fontSize = 18.sp,
                                modifier = Modifier
                                    .padding(vertical = 12.dp, horizontal = 24.dp)
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {

                                }
                        ) {
                            Text(
                                text = "24-hour 13:00",
                                fontSize = 18.sp,
                                modifier = Modifier
                                    .padding(vertical = 12.dp, horizontal = 24.dp)
                            )
                        }
                    }
                }
            }
        }
        SettingsElement(
            title = "Категории",
            icon = R.drawable.categories,
            click = {
                    navController.navigate(AdditionalScreens.EditCategoriesListScreen.rout) {
                        popUpTo(Screens.SettingsScreen.rout) {
                            saveState = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.5.dp)
                .background(colorResource(id = R.color.secondary_gray))
        )
        SettingsElement(
            title = "Язык",
            icon = R.drawable.language,
            click = {

            }
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.5.dp)
                .background(colorResource(id = R.color.secondary_gray))
        )
        var showCurrency by remember { mutableStateOf(false) }
        SettingsElement(
            title = "Валюта",
            icon = R.drawable.dollar,
            click = {
                showCurrency = true
            }
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.5.dp)
                .background(colorResource(id = R.color.secondary_gray))
        )
        if (showCurrency){
            Dialog(onDismissRequest = { showCurrency = false }) {
                Box(
                    modifier = Modifier
                        .width(150.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colors.secondary)
                ) {
                    Column {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {

                                },
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Text(
                                text = "₽",
                                modifier = Modifier
                                    .padding(vertical = 12.dp)
                            )
                            Text(
                                text = "RUB",
                                modifier = Modifier
                                    .padding(vertical = 12.dp)
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {

                                },
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Text(
                                text = "$",
                                modifier = Modifier
                                    .padding(vertical = 12.dp)
                            )
                            Text(
                                text = "USD",
                                modifier = Modifier
                                    .padding(vertical = 12.dp)
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {

                                },
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Text(
                                text = "€",
                                modifier = Modifier
                                    .padding(vertical = 12.dp)
                            )
                            Text(
                                text = "EUR",
                                modifier = Modifier
                                    .padding(vertical = 12.dp)
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {

                                },
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Text(
                                text = "¥",
                                modifier = Modifier
                                    .padding(vertical = 12.dp)
                            )
                            Text(
                                text = "CNY",
                                modifier = Modifier
                                    .padding(vertical = 12.dp)
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {

                                },
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Text(
                                text = "₩",
                                modifier = Modifier
                                    .padding(vertical = 12.dp)
                            )
                            Text(
                                text = "KPW"
                                ,
                                modifier = Modifier
                                    .padding(vertical = 12.dp)
                            )
                        }
                    }
                }
            }
        }
        SettingsElement(
            title = "Помочь разработчикам",
            icon = R.drawable.pay,
            click = {

            }
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.5.dp)
                .background(colorResource(id = R.color.secondary_gray))
        )
    }
}