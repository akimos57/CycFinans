package ru.cyclone.cycfinans.presentation.screens.calendar

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ru.cyclone.cycfinans.presentation.navigation.AdditionalScreens
import ru.cyclone.cycfinans.presentation.ui.components.Calendar
import ru.cyclone.cycfinans.presentation.ui.components.EditNote
import ru.cyclone.cycfinans.presentation.ui.components.MyCalendar
import ru.cyclone.cycfinans.presentation.ui.components.NoteBox
import ru.cyclone.cycfinans.presentation.ui.theme.blue
import ru.cyclone.cycfinans.presentation.ui.theme.fab2
import java.time.Month
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CalendarScreen(navController: NavHostController, onAddNoteReturned: MutableState<() -> Unit>) {
    val vm = hiltViewModel<CalendarScreenVM>()
    val notes = vm.notes.observeAsState(listOf()).value
    var visible by remember {
        mutableStateOf(false)
    }
    val yearMonth by vm.yearMonth.observeAsState()
    val day by vm.day.observeAsState()

    val currentYearMonth = remember { mutableStateOf(yearMonth) }
    var currentDay by remember { mutableStateOf(day) }

    var monthString by remember { mutableStateOf(
        Month.of(currentYearMonth.value!!.monthValue).getDisplayName(TextStyle.FULL_STANDALONE, Locale.getDefault())
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
    ) }

    var dateString by remember {
        mutableStateOf("$monthString, ${ currentYearMonth.value!!.year }")
    }

    onAddNoteReturned.value = {
        vm.updateNotes()
    }

//    var time2 by remember { mutableStateOf(Time()) }
//    val time3 = SimpleDateFormat("HH:mm", Locale.getDefault()).format(time2)

    val s = remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val calendar = Calendar.Builder()
                        .setDate(currentYearMonth.value!!.year, currentYearMonth.value!!.monthValue - 1, currentDay!!)
                        .build()
                    navController.navigate(AdditionalScreens.AddNoteScreen.rout + calendar.timeInMillis) {
                        launchSingleTop = true
                    }
//                          s.value = true
                },
                backgroundColor = blue
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "add",
                    modifier = Modifier
                        .height(33.dp)
                        .width(33.dp)
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            TextButton(
                modifier = Modifier
                    .padding(top = 20.dp, start = 20.dp, end = 20.dp)
                    .clip(RoundedCornerShape(16.dp)),
                onClick = { visible = true },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.background
                )
            ) {
                Text(
                    modifier = Modifier,
                    text = dateString,
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
            Column {
//                val daysOfWeek = mutableListOf(
//                    "Пн",
//                    "Вт",
//                    "Ср",
//                    "Чт",
//                    "Пт",
//                    "Сб",
//                    "Вс",
//                )
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(horizontal = 20.dp)
//                        .background(blue)
//                ) {
//                    Text(text = "$daysOfWeek")
//                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
//                    contentAlignment = Alignment.Center
                ) {
                    MyCalendar(
                        onDaySelected = { day ->
                            currentDay = day
                        },
                        yearMonth = currentYearMonth,
                        modifier = Modifier
                            .padding(20.dp)
                            .fillMaxWidth()
                            .aspectRatio(1.3f)
                    )
                }
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    text = " $monthString, $currentDay",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Light
                )
            }

            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                notes.filter { note ->
                    val c = Calendar.getInstance()
                    c.timeInMillis = note.time.time
                    (currentYearMonth.value?.year == c.get(Calendar.YEAR)) and
                            ((currentYearMonth.value?.month?.value?.minus(1)) == c.get(Calendar.MONTH)) and
                            (currentDay == c.get(Calendar.DATE))
                }.forEach { note ->
                    val showDialog = s
                    s.value = true
                    val showDialog1 = remember { mutableStateOf(false) }
                    EditNote(show = s.value, onDismiss = { s.value = false }, date = 7)
                    if (showDialog1.value)
                    {
                        Dialog(
                            onDismissRequest = { showDialog1.value = false }) {
                            Box(
                                modifier = Modifier
                                    .height(120.dp)
                                    .width(250.dp)
                                    .clip(RoundedCornerShape(24.dp))
                                    .background(MaterialTheme.colors.secondary)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 24.dp),
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = "Удалить?",
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Medium,
                                    )
                                }

                                Column(
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    verticalArrangement = Arrangement.Bottom
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        horizontalArrangement = Arrangement.End
                                    ) {
                                        OutlinedButton(
                                            modifier = Modifier
                                                .padding(end = 20.dp),
                                            shape = CircleShape,
                                            border = BorderStroke(1.dp, color = Color.Transparent),
                                            colors = ButtonDefaults.outlinedButtonColors(
                                                backgroundColor = MaterialTheme.colors.secondary,
                                                contentColor = MaterialTheme.colors.primaryVariant
                                            ),
                                            onClick = { s.value = false }) {
                                            Text(
                                                text = "Отмена",
                                                fontSize = 14.sp,
                                                fontWeight = FontWeight.Medium
                                            )
                                        }
                                        OutlinedButton(
                                            modifier = Modifier
                                                .padding(end = 20.dp),
                                            shape = CircleShape,
                                            border = BorderStroke(1.dp, color = Color.Transparent),
                                            colors = ButtonDefaults.outlinedButtonColors(
                                                backgroundColor = fab2,
                                                contentColor = MaterialTheme.colors.primaryVariant
                                            ),
                                            onClick = { s.value = false; vm.deleteNote(note = note) }) {
                                            Text(
                                                text = "Удалить",
                                                fontSize = 14.sp,
                                                fontWeight = FontWeight.Medium,
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                    NoteBox(
                        note = note,
                        modifierClickable = Modifier
                            .combinedClickable (
                                onClick =  {navController.navigate(AdditionalScreens.AddNoteScreen.rout +
                                        note.id + '/' +
                                        note.content.ifBlank { "" } + '/' +
                                        note.time.time.toString()
                                )},
                                onLongClick = {showDialog1.value = true}
                            ),
                        modifierSizeBox = Modifier
                            .padding(horizontal = 20.dp, vertical = 4.dp),
                    )
                }
            }
        }
        Calendar(
            visible = visible,
            currentMonth = currentYearMonth.value!!.monthValue - 1,
            currentYear = currentYearMonth.value!!.year,
            confirmButtonClicked = { _month, _year ->
                currentYearMonth.value = YearMonth.of(_year, _month)
                monthString =
                    Month.of(_month).getDisplayName(TextStyle.FULL_STANDALONE, Locale.getDefault())
                        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
                dateString = "$monthString, $_year"
                visible = false
            },
            cancelClicked = {
                visible = false
            },
            onDismiss = { visible = false }
        )
    }
}
