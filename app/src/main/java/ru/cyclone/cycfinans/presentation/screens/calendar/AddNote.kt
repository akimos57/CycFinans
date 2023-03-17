package ru.cyclone.cycfinans.presentation.screens.calendar

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ru.cyclone.cycfinans.domain.model.Note
import ru.cyclone.cycfinans.presentation.ui.theme.gold
import java.sql.Time
import java.util.*


@Composable
fun AddNote(
    navController: NavHostController,
    noteId: String?,
    noteContent: String?,
    onReturned: MutableState<() -> Unit>
) {
    navController.enableOnBackPressed(true)
    val vm = hiltViewModel<AddNoteVM>()
    val note by vm.note.observeAsState()
    noteId?.toLong()?.let { vm.getNoteById(id = it) }

    var content by rememberSaveable { mutableStateOf(noteContent?:"") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(MaterialTheme.colors.background)
                    .clickable {
                        if (content.isBlank() and (note != null)) {
                            vm.deleteNote(note!!)
                        } else if (note != null) {
                            vm.addNote(
                                Note(
                                    id = note!!.id,
                                    content = content,
                                    time = note!!.time
                                ),
                                onSuccess = onReturned.value
                            )
                        } else if (content.isNotBlank()) {
                            vm.addNote(
                                Note(
                                    content = content,
                                    time = Time(Calendar.getInstance().timeInMillis)
                                ),
                                onSuccess = onReturned.value
                            )
                        }
                        onReturned.value()
                        navController.popBackStack()
                    },
                contentAlignment = Alignment.Center
            ){
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
                    .width(48.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .clickable { },
                contentAlignment = Alignment.Center
            ){
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = "back",
                    modifier = Modifier
                        .height(25.dp)
                        .width(25.dp)
                )
            }
        }
        TextField(
            value = content,
            onValueChange = { content = it },
            modifier = Modifier
                .fillMaxSize(),
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = gold,
                disabledLabelColor = MaterialTheme.colors.secondary,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                backgroundColor = MaterialTheme.colors.background
            ),
            textStyle = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Medium),
            placeholder = {
                Text(
                    text = "Enter note",
                    fontSize = 22.sp
                )
            },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                autoCorrect = true,
                keyboardType = KeyboardType.Text
            )
        )
    }
    BackHandler {
        if (content.isBlank() and (note != null)) {
            vm.deleteNote(note!!)
        } else if (note != null) {
            vm.addNote(
                Note(
                    id = note!!.id,
                    content = content,
                    time = note!!.time
                ),
                onSuccess = onReturned.value
            )
        } else if (content.isNotBlank()) {
            vm.addNote(
                Note(
                    content = content,
                    time = Time(Calendar.getInstance().timeInMillis)
                ),
                onSuccess = onReturned.value
            )
        }
        onReturned.value()
        navController.popBackStack()
    }
}