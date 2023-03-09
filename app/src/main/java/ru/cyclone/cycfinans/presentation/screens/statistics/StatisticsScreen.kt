package ru.cyclone.cycfinans.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import ru.cyclone.cycfinans.presentation.components.Calendar
import ru.cyclone.cycfinans.presentation.components.ChartDonut
import ru.cyclone.cycfinans.presentation.navigation.Screens
import java.util.*

@Composable
fun StatisticsScreen(navController: NavHostController) {

    var visible by remember {
        mutableStateOf(false)
    }

    val currentMonth = Calendar.getInstance().get(Calendar.MONTH)

    val currentMonth2 = currentMonth + 1

    val currentYear = Calendar.getInstance().get(Calendar.YEAR)

    val currentMonth3 = when(currentMonth2) {
        1 -> "Январь"
        2 -> "Февраль"
        3 -> "Март"
        4 -> "Апрель"
        5 -> "Май"
        6 -> "Июнь"
        7 -> "Июль"
        8 -> "Август"
        9 -> "Сентябрь"
        10 -> "Октябрь"
        11 -> "Ноябрь"
        12 -> "Декабрь"
        else -> {}
    }

    var date by remember {
        mutableStateOf("$currentMonth3, $currentYear")
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(48.dp)
                    .height(48.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .clickable { navController.navigate(Screens.MainScreen.rout) },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "back",
                    modifier = Modifier
                        .height(25.dp)
                        .width(25.dp)
                )
            }
            Box(
                modifier = Modifier
                    .height(48.dp)
                    .padding(start = 16.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colors.background)
                    .clickable { visible = true },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier
                        .padding(horizontal = 8.dp),
                    text = "$date",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Light
                )
            }

            Calendar(
                visible = visible,
                currentMonth = currentMonth,
                currentYear = currentYear,
                confirmButtonClicked = {
                        month_, year_ ->
//                    date = "$month_, $year_"
                    val i = when(month_){
                        1 -> "Январь"
                        2 -> "Февраль"
                        3 -> "Март"
                        4 -> "Апрель"
                        5 -> "Май"
                        6 -> "Июнь"
                        7 -> "Июль"
                        8 -> "Август"
                        9 -> "Сентябрь"
                        10 -> "Октябрь"
                        11 -> "Ноябрь"
                        12 -> "Декабрь"
                        else -> {}
                    }
                    date = "$i, $year_"
                    visible = false
                },
                cancelClicked = {
                    visible = false
                },
                onDismiss = { visible = false }
            )

        }
        
        ChartDonut(
            data = mapOf(
                Pair("Еда", 150),
                Pair("Одежда", 120),
                Pair("Налоги", 50),
                Pair("Развлечения", 170),
                Pair("Остальное", 20),
            )
        )

    }
}





