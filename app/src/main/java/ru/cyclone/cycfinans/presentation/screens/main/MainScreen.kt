package ru.cyclone.cycfinans.presentation.screens.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ru.cyclone.cycfinans.presentation.components.Calendar
import ru.cyclone.cycfinans.presentation.components.DayBox
import ru.cyclone.cycfinans.presentation.components.MainBox
import ru.cyclone.cycfinans.presentation.components.WidgetMain
import ru.cyclone.cycfinans.presentation.navigation.AdditionalScreens
import ru.cyclone.cycfinans.presentation.navigation.Screens
import java.time.Month
import java.time.Year
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.*

@Composable
fun MainScreen(navController: NavHostController) {
    val vm = hiltViewModel<MainScreenVM>()
    var visible by remember {
        mutableStateOf(false)
    }

    val history = vm.promotions.observeAsState()

    val currentMonth = remember { mutableStateOf(Calendar.getInstance().get(Calendar.MONTH)) }

    var month = Month.of(currentMonth.value + 1).getDisplayName(TextStyle.FULL_STANDALONE, Locale.getDefault())
        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
    val currentYear = Year.now().value

    var date by remember {
        mutableStateOf("$month, $currentYear")
    }

    val c = Calendar.getInstance()
    val fullIncome = history.value?.filter {
        c.timeInMillis = it.time.time
        it.type
    }?.sumOf { it.price }?:0
    val fullExpenses = history.value?.filter {
        c.timeInMillis = it.time.time
        !it.type
    }?.sumOf { it.price }?:0

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
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
                    .clickable { navController.navigate(Screens.StatisticsScreen.rout) },
                income = fullIncome,
                expenses = fullExpenses
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
                        .size(48.dp)
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

            for (i in 1..Month.of(currentMonth.value + 1).length(Year.now().isLeap)) {
                val income = history.value?.filter {
                    c.timeInMillis = it.time.time
                    (c.get(Calendar.DAY_OF_MONTH) == i) and (it.type)
                }?.sumOf { it.price }
                val expenses = history.value?.filter {
                    c.timeInMillis = it.time.time
                    (c.get(Calendar.DAY_OF_MONTH) == i) and (!it.type)
                }?.sumOf { it.price }
                if ((income != null) and (expenses != null)) {
                    DayBox(
                        day = i,
                        modifier = Modifier
                            .clickable {
                                navController.navigate(
                                    AdditionalScreens.MainDetailsScreen.rout +
                                            "/$i/${currentMonth.value + 1}/$currentYear"
                                ) {
                                    launchSingleTop = true
                                }
                            },
                        income = income!!,
                        expenses = expenses!!
                    )
                }
            }
        }
        Calendar(
            visible = visible,
            currentMonth = currentMonth.value,
            currentYear = currentYear,
            confirmButtonClicked = { _month, _year ->
                currentMonth.value = _month - 1
                month =
                    Month.of(_month).getDisplayName(TextStyle.FULL_STANDALONE, Locale.getDefault())
                        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
                date = "$month, $_year"
                visible = false
                vm.getHistory(YearMonth.of(_year, _month))
            },
            cancelClicked = {
                visible = false
            },
            onDismiss = { visible = false }
        )
    }
}