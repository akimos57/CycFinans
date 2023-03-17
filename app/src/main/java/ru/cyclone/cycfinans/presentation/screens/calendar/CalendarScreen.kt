package ru.cyclone.cycfinans.presentation.screens.calendar

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ru.cyclone.cycfinans.domain.model.CalendarInput
import ru.cyclone.cycfinans.presentation.navigation.AdditionalScreens
import ru.cyclone.cycfinans.presentation.ui.components.MyCalendar
import ru.cyclone.cycfinans.presentation.ui.components.NoteBox
import ru.cyclone.cycfinans.presentation.ui.theme.gold

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CalendarScreen(navController: NavHostController) {
    val vm = hiltViewModel<CalendarScreenVM>()
    val notes = vm.notes.observeAsState()
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
                            clickedCalendarElem?.toDos?.forEach{
                                Text(
                                    text = if(it.contains("Day")) it else "- $it",
                                    fontSize = if(it.contains("Day")) 25.sp else 18.sp,
                                    fontWeight = FontWeight.Light
                                )
                            }
                        }
                }

            notes.value?.forEach { note ->
                NoteBox(
                    note = note,
                    modifier = Modifier
                        .clickable { navController.navigate(AdditionalScreens.AddNoteScreen.rout + note.id + '/' + note.content.ifBlank { "" }) }
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