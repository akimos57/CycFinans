package ru.cyclone.cycfinans.presentation.screens.calendar

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ru.cyclone.cycfinans.domain.model.CalendarInput
import ru.cyclone.cycfinans.presentation.navigation.AdditionalScreens
import ru.cyclone.cycfinans.presentation.ui.components.MyCalendar
import ru.cyclone.cycfinans.presentation.ui.components.NoteBox
import ru.cyclone.cycfinans.presentation.ui.theme.fab2
import ru.cyclone.cycfinans.presentation.ui.theme.gold
import java.util.*

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CalendarScreen(navController: NavHostController) {
    val vm = hiltViewModel<CalendarScreenVM>()
    val notes by vm.notes.observeAsState()
    Scaffold(
        floatingActionButton = {FloatingActionButton(
            onClick = { navController.navigate(AdditionalScreens.AddNoteScreen.rout) {
                launchSingleTop = true
            }},
            backgroundColor = gold
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "add",
                modifier = Modifier
                    .height(33.dp)
                    .width(33.dp)
            )
        }}
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val calendarInputList by remember {
                mutableStateOf(createCalendarList())
            }
            var clickedCalendarElem by remember {
                mutableStateOf<CalendarInput?>(null)
            }
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    MyCalendar(
                        calendarInput = calendarInputList,
                        onDayClick = { day ->
                            clickedCalendarElem = calendarInputList.first { it.day == day }
                        },
                        month = "Март",
                        modifier = Modifier
                            .padding(20.dp)
                            .fillMaxWidth()
                            .aspectRatio(1.3f)
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    clickedCalendarElem?.toDos?.forEach {
                        Text(
                            text = if (it.contains("Day")) it else "- $it",
                            fontSize = if (it.contains("Day")) 25.sp else 18.sp,
                            fontWeight = FontWeight.Light
                        )
                    }
                }
            }
            val calendar = Calendar.getInstance()
            notes?.filter{ note ->
                calendar.timeInMillis = note.time.time
                calendar[Calendar.DAY_OF_MONTH] == clickedCalendarElem?.day
            }?.forEach { note ->
                val showDialog1 = remember { mutableStateOf(false) }
                if (showDialog1.value) {
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
                                        onClick = { showDialog1.value = false }) {
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
                                        onClick = { showDialog1.value = false; vm.deleteNote(note = note) }) {
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
                    modifier = Modifier
                        .combinedClickable (
                            onClick =  {navController.navigate(AdditionalScreens.AddNoteScreen.rout + note.id + '/' + note.content.ifBlank { "" })},
                            onLongClick = {showDialog1.value = true}
                        )
                    )
                }
            }
        }
    }
private fun createCalendarList(): List<CalendarInput> {
    val calendarInputs = mutableListOf<CalendarInput>()
    for (i in 1..31) {
        calendarInputs.add(
            CalendarInput(
                i,
                toDos = listOf(
                    "Day $i:",
                    "title",
                    "content"
                )
            )
        )
    }
    return calendarInputs
}