package ru.cyclone.cycfinans.presentation.screens.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import ru.cyclone.cycfinans.presentation.ui.components.Calendar
import ru.cyclone.cycfinans.presentation.ui.components.DayBox
import ru.cyclone.cycfinans.presentation.ui.components.FastNotes
import ru.cyclone.cycfinans.presentation.ui.components.MainBox
import ru.cyclone.cycfinans.presentation.navigation.AdditionalScreens
import ru.cyclone.cycfinans.presentation.navigation.Screens
import java.time.LocalDate
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
    val actualDate = Pair(LocalDate.now().dayOfMonth, YearMonth.now())
    val fullIncome = history.value?.filter {
        c.timeInMillis = it.time.time
        it.type
    }?.sumOf { it.price }?:0
    val fullExpenses = history.value?.filter {
        c.timeInMillis = it.time.time
        !it.type
    }?.sumOf { it.price }?:0

    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

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
                    modifier = Modifier
                        .padding(start = 8.dp),
                    text = date,
                    fontSize = 32.sp,
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

            val monthList = (1..Month.of(currentMonth.value + 1).length(Year.now().isLeap)).toList()
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                state = scrollState
            ) {
                items(monthList.size) { index ->
                    val i = index+1
                    if (
                        (i == 1) and
                        (currentMonth.value + 1 != actualDate.second.month.value) or
                        (currentYear != actualDate.second.year)
                    ) {
                        FastNotes()
                    }
                    val income = history.value?.filter {
                        c.timeInMillis = it.time.time
                        (c.get(Calendar.DAY_OF_MONTH) == i) and (it.type)
                    }?.sumOf { it.price }
                    val expenses = history.value?.filter {
                        c.timeInMillis = it.time.time
                        (c.get(Calendar.DAY_OF_MONTH) == i) and (!it.type)
                    }?.sumOf { it.price }
                    if (
                        (i == actualDate.first) and
                        (currentMonth.value + 1 == actualDate.second.month.value) and
                        (currentYear == actualDate.second.year)
                    ) {
                        FastNotes()
                    }

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
                if (
                    (currentMonth.value + 1 == actualDate.second.month.value) and
                    (currentYear == actualDate.second.year)
                ) {
                    coroutineScope.launch {
                        scrollState.animateScrollToItem(actualDate.first - 1)
                    }
                } else {
                    coroutineScope.launch {
                        scrollState.animateScrollToItem(0)
                    }
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