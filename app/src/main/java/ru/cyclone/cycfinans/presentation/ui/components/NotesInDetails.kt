package ru.cyclone.cycfinans.presentation.ui.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import ru.cyclone.cycfinans.domain.model.Note
import ru.cyclone.cycfinans.presentation.screens.main.MainDetailsScreenVM
import ru.cyclone.cycfinans.presentation.ui.theme.fab2
import ru.cyclone.cycnote.R
import java.sql.Time

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NotesInDetails() {
    val viewModel = hiltViewModel<MainDetailsScreenVM>()
    val notes by viewModel.notes.observeAsState()
    val showEditNoteDialog = remember { mutableStateOf(false) }
    val editedNote = remember { mutableStateOf(Note(
        content = "",
        time = Time(System.currentTimeMillis())
    )) }
    Row(
        modifier = Modifier
            .horizontalScroll(rememberScrollState())
            .padding(start = 4.dp)
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        EmptyNoteBox (
            modifier = Modifier
                .clip(RoundedCornerShape(24.dp))
                .clickable {
                    editedNote.value = Note(
                        content = "",
                        time = Time(System.currentTimeMillis())
                    )
                    showEditNoteDialog.value = true
                }
        )
        notes?.forEach { note ->
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
                                text = stringResource(id = R.string.delete) +"?",
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
                                        text = stringResource(id = R.string.cancel),
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
                                    onClick = { showDialog1.value = false; viewModel.deleteNote(note = note) }) {
                                    Text(
                                        text = stringResource(id = R.string.delete),
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
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .combinedClickable (
                        onClick = {
                            editedNote.value = note
                            showEditNoteDialog.value = true
                        },
                        onLongClick = { showDialog1.value = true }
                    ),
                note = note,
                onNoteCompleteStateChanged = {
                    viewModel.addNote(note.copy(completed = it))
                }
            )
        }
        EditNote(
            note = editedNote,
            showDialog = showEditNoteDialog,
            addNote = { note -> viewModel.addNote(note) },
            deleteNote = { note -> viewModel.deleteNote(note) }
        )
    }
}