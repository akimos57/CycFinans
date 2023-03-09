package ru.cyclone.cycfinans.presentation.screens.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import ru.cyclone.cycfinans.presentation.components.Calendar
import ru.cyclone.cycfinans.presentation.components.DayBox
import ru.cyclone.cycfinans.presentation.components.MainBox
import ru.cyclone.cycfinans.presentation.components.WidgetMain
import ru.cyclone.cycfinans.presentation.navigation.AdditionalScreens
import ru.cyclone.cycfinans.presentation.navigation.Screens
import java.time.Month
import java.time.format.TextStyle
import java.util.*

@Composable
fun MainScreen(navController: NavHostController) {
    var visible by remember {
        mutableStateOf(false)
    }

    val currentMonth = Calendar.getInstance().get(Calendar.MONTH)

    var month = Month.of(currentMonth + 1).getDisplayName(TextStyle.FULL_STANDALONE, Locale.getDefault())
        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
    val currentYear = Calendar.getInstance().get(Calendar.YEAR)

    var date by remember {
        mutableStateOf("$month, $currentYear")
    }
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            TextButton(
                modifier = Modifier
                    .padding(20.dp)
                    .clip(RoundedCornerShape(16.dp)),
                onClick = { visible = true },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.background
                )
            ) {
                    Text(
                        text = date,
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Light
                    )
                    Icon(
                        imageVector = Icons.Filled.ArrowDropDown,
                        contentDescription = "arrow",
                        modifier = Modifier
                            .size(33.dp)
                    )
                }

            MainBox(
                modifier = Modifier
                    .clickable { navController.navigate(Screens.StatisticsScreen.rout) }
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .padding(bottom = 12.dp, start = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .height(48.dp)
                        .width(48.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .clickable { },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "new",
                    )
                }

                WidgetMain()
                WidgetMain()
                WidgetMain()
            }

            DayBox(
                modifier = Modifier
                .clickable { navController.navigate(AdditionalScreens.MainDetailsScreen.rout) }
            )
            }
        Calendar(
            visible = visible,
            currentMonth = currentMonth,
            currentYear = currentYear,
            confirmButtonClicked = {
                    month_, year_ ->
                month = Month.of(month_).getDisplayName(TextStyle.FULL_STANDALONE, Locale.getDefault())
                    .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
                date = "$month, $year_"
                visible = false
            },
            cancelClicked = {
                visible = false
            },
            onDismiss = { visible = false }
        )
    }
}