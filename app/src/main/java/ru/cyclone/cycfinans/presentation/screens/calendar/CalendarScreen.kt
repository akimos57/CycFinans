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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ru.cyclone.cycfinans.presentation.navigation.AdditionalScreens
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 26.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
//                Box(
//                    modifier = Modifier
//                        .width(48.dp)
//                        .height(48.dp)
//                        .clip(RoundedCornerShape(24.dp))
//                        .clickable { navController.navigate(Screens.MainScreen.rout) },
//                    contentAlignment = Alignment.Center
//                ) {
//                    Icon(
//                        imageVector = Icons.Filled.ArrowBack,
//                        contentDescription = "back",
//                        modifier = Modifier
//                            .height(25.dp)
//                            .width(25.dp)
//                    )
//                }
                Text(
                    text = "Цели",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier
                        .padding(start = 16.dp, bottom = 16.dp)
                )
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