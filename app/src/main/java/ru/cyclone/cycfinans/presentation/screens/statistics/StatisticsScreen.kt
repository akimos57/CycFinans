package ru.cyclone.cycfinans.presentation.screens.statistics

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import ru.cyclone.cycfinans.presentation.navigation.Screens
import ru.cyclone.cycfinans.presentation.ui.components.Calendar
import ru.cyclone.cycfinans.presentation.ui.components.ChartDonut
import ru.cyclone.cycnote.R
import java.time.Month
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.*

@OptIn(ExperimentalPagerApi::class)
@Composable
fun StatisticsScreen(
    navController: NavHostController,
    month: String?,
    year: String?
) {
    var visible by remember {
        mutableStateOf(false)
    }

    // Create a YearMonth object with the current year and month
    var currentMonth = Calendar.getInstance().get(Calendar.MONTH)
    var currentYear = Calendar.getInstance().get(Calendar.YEAR)

    if (!month.isNullOrBlank() and !year.isNullOrBlank()) {
        currentMonth = month?.toInt()!!
        currentYear = year?.toInt()!!
    }

    var date by remember { mutableStateOf(YearMonth.of(currentYear, currentMonth + 1)) }

    // Get the view model from Hilt
    val vm = hiltViewModel<StatisticsScreenVM>()

    // Observe the categories and categories1 states
    val categories = vm.categories.observeAsState()
    val categories1 = vm.categories1.observeAsState()

    val pagerState = rememberPagerState()
    val coroutine = rememberCoroutineScope()

    val animationState = remember { mutableStateOf(false) }
    val animationState1 = remember { mutableStateOf(false) }
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
                Row(
                    modifier = Modifier
                        .padding(horizontal = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val monthString =
                        Month.of(date.month.value).getDisplayName(TextStyle.FULL_STANDALONE, Locale.getDefault())
                    Text(
                        modifier = Modifier
                            .padding(start = 8.dp),
                        text = "$monthString ${date.year}"
                            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Light
                    )
                    Icon(
                        imageVector = Icons.Filled.ArrowDropDown,
                        contentDescription = "arrow",
                        modifier = Modifier
                            .size(28.dp)
                    )
                }

            }
            Calendar(
                visible = visible,
                currentMonth = currentMonth,
                currentYear = currentYear,
                confirmButtonClicked = {
                        _month, _year ->
                    date = YearMonth.of(_year, _month)
                    vm.updateAllPromotions(date)
                    visible = false
                },
                cancelClicked = {
                    visible = false
                },
                onDismiss = { visible = false }
            )
        }
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    when (pagerState.currentPage) {
                        0 -> {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                            ) {
                                Row(modifier = Modifier) {
                                    Column(
                                        modifier = Modifier
                                            .width(IntrinsicSize.Min)
                                            .background(MaterialTheme.colors.primary),
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        Text(
                                            modifier = Modifier
                                                .padding(horizontal = 15.dp, vertical = 4.dp),
                                            text = stringResource(id = R.string.expenses)
                                        )
                                    }

                                    Column(
                                        modifier = Modifier
                                            .width(IntrinsicSize.Min)
                                            .background(MaterialTheme.colors.secondary)
                                            .clickable {
                                                coroutine.launch {
                                                    pagerState.scrollToPage(1)
                                                    animationState1.value = true
                                                    animationState.value = false
                                                }
                                            },
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        Text(
                                            modifier = Modifier
                                                .padding(horizontal = 15.dp, vertical = 4.dp),
                                            text = stringResource(id = R.string.income)
                                        )
                                    }
                                }
                            }

                        }
                        1 -> {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                            ) {
                                Row(modifier = Modifier) {
                                    Column(
                                        modifier = Modifier
                                            .width(IntrinsicSize.Min)
                                            .background(MaterialTheme.colors.secondary)
                                            .clickable {
                                                coroutine.launch {
                                                    pagerState.scrollToPage(0)
                                                    animationState.value = true
                                                    animationState1.value = false
                                                }
                                            },
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        Text(
                                            modifier = Modifier
                                                .padding(horizontal = 15.dp, vertical = 4.dp),
                                            text = stringResource(id = R.string.expenses)
                                        )
                                    }
                                    Column(
                                        modifier = Modifier
                                            .width(IntrinsicSize.Min)
                                            .background(MaterialTheme.colors.primary),
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        Text(
                                            modifier = Modifier
                                                .padding(horizontal = 15.dp, vertical = 4.dp),
                                            text = stringResource(id = R.string.income)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        HorizontalPager(
            count = 2,
            state = pagerState
        ) { page ->
            when (page) {
                0 -> categories.value?.let { ChartDonut(
                    data = it,
                    animationState = animationState
                ) }
                1 -> categories1.value?.let { ChartDonut(
                    data = it,
                    animationState = animationState1
                ) }
            }
        }
    }
}