package ru.cyclone.cycfinans.presentation.ui.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import ru.cyclone.cycfinans.presentation.navigation.AdditionalScreens
import ru.cyclone.cycfinans.presentation.screens.main.MainDetailsScreenVM
import ru.cyclone.cycfinans.presentation.ui.theme.fab2

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NotesInCalendar(navController: NavHostController) {
    val viewModel = hiltViewModel<MainDetailsScreenVM>()
    val notes by viewModel.notes.observeAsState()
    LazyColumn(
        modifier = Modifier
            .padding(start = 4.dp)
    ) {
        notes?.let {
            items(it.size) { index ->
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
                                        onClick = { showDialog1.value = false; viewModel.deleteNote(note = notes!![index]) }) {
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
//                NoteBox(
//                    note = notes!![index],
//                    modifier = Modifier
//                        .combinedClickable (
//                            onClick = { navController.navigate(
//                                AdditionalScreens.AddNoteScreen.rout +
//                                        notes!![index].id + '/' +
//                                        notes!![index].content.ifBlank { "" })},
//                            onLongClick = { showDialog1.value = true }
//                        )
//                )
            }
        }
    }
}