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
import ru.cyclone.cycfinans.presentation.components.ChartDonut
import ru.cyclone.cycfinans.presentation.navigation.Screens
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.*

@Composable
fun StatisticsScreen(navController: NavHostController) {
    var visible by remember {
        mutableStateOf(false)
    }

    val currentMonth = Calendar.getInstance().get(Calendar.MONTH)
    val currentYear = Calendar.getInstance().get(Calendar.YEAR)

    var date by remember { mutableStateOf(YearMonth.of(currentYear, currentMonth + 1)) }
    val vm = hiltViewModel<StatisticsScreenVM>()
    vm.date = date

    val categories = vm.categories.observeAsState()

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
                    text = date.format(DateTimeFormatter.ofPattern("LLL, y", Locale.getDefault()))
                        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Light
                )
            }

            Calendar(
                visible = visible,
                currentMonth = currentMonth,
                currentYear = currentYear,
                confirmButtonClicked = {
                        _month, _year ->
                    date = YearMonth.of(_year, _month)
                    visible = false
                },
                cancelClicked = {
                    visible = false
                },
                onDismiss = { visible = false }
            )

        }
        categories.value?.let {
            ChartDonut(
                data = it
            )
        }
    }
}





