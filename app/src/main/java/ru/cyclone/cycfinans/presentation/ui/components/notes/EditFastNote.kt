@file:Suppress("DEPRECATION")
package ru.cyclone.cycfinans.presentation.ui.components

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.cyclone.cycfinans.domain.model.Note
import ru.cyclone.cycfinans.presentation.ui.theme.*
import ru.cyclone.cycnote.R
import java.sql.Time
import java.util.*

@Composable
fun EditFastNote(
    note: MutableState<Note>,
    showDialog: MutableState<Boolean>,
    addNote: (Note) -> Unit,
    deleteNote: (Note) -> Unit
) {
    if (showDialog.value) {
        var content by rememberSaveable { mutableStateOf(note.value.content) }
        var time by remember { mutableStateOf(Time(System.currentTimeMillis())) }
        var isCompleted by rememberSaveable { mutableStateOf(note.value.isCompleted) }

        val c = Calendar.getInstance()
        val dp = DatePickerDialog(LocalContext.current,
            { _, _year: Int, _month: Int, _dayOfMonth: Int ->
                val q = Calendar.getInstance()
                q.timeInMillis = time.time
                q.set(_year, _month, _dayOfMonth)
                time = Time(q.timeInMillis)
            }, c[Calendar.YEAR], c[Calendar.MONTH], c[Calendar.DAY_OF_MONTH]
        )
        val tp = TimePickerDialog(LocalContext.current,
            { _, selectedHour: Int, selectedMinute: Int ->
                val q = Calendar.getInstance()
                q.timeInMillis = time.time
                q.set(Calendar.HOUR_OF_DAY, selectedHour)
                q.set(Calendar.MINUTE, selectedMinute)
                time = Time(q.timeInMillis)
            }, c[Calendar.HOUR_OF_DAY], c[Calendar.MINUTE], true
        )

        dp.setOnCancelListener { tp.dismiss() }
        dp.setOnDismissListener {
            if (content.isNotBlank()) {
                isCompleted = true
                note.value = note.value.copy(time = time)
            }
        }
        tp.setOnDismissListener {
            if (content.isNotBlank()) {
                isCompleted = true
                note.value = note.value.copy(time = time)
            }
        }

        AlertDialog(
            onDismissRequest = {
                showDialog.value = false
            },
            backgroundColor = MaterialTheme.colors.secondary,
            shape = RoundedCornerShape(8),
            text = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colors.secondary)
                ) {
                        TextField(
                            value = if (content == "0") "" else content,
                            onValueChange = { content = it },
                            keyboardOptions = KeyboardOptions(
                                capitalization = KeyboardCapitalization.Sentences
                            ),
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = MaterialTheme.colors.secondary,
                                cursorColor = blue,
                                focusedIndicatorColor = blue,
                                unfocusedIndicatorColor = blue,
                                textColor = MaterialTheme.colors.primaryVariant
                            ),
                            placeholder = {
                                Text(
                                    text = stringResource(id = R.string.reminder),
                                    fontSize = 18.sp
                                )
                            },
                            textStyle = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Normal)
                        )
                    }
            },
            buttons = {
                Column(
                    modifier = Modifier
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(30.dp)
                        ,
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(horizontal = 24.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(id = R.string.done),
                                color = MaterialTheme.colors.primaryVariant
                            )
                            Checkbox(
                                checked = isCompleted,
                                onCheckedChange = { isCompleted = it },
                                colors = CheckboxDefaults.colors(blue),
                                modifier = Modifier
                                    .size(16.dp)
                                    .padding(start = 16.dp,end = 16.dp)
                            )
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 8.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        OutlinedButton(
                            shape = CircleShape,
                            border = BorderStroke(1.dp, color = Color.Transparent),
                            colors = ButtonDefaults.outlinedButtonColors(
                                backgroundColor = MaterialTheme.colors.secondary,
                            ),
                            onClick = { showDialog.value = false }
                        ) {
                            Text(
                                text = stringResource(id = R.string.cancel),
                                color = MaterialTheme.colors.primaryVariant,
                                fontSize = 14.sp
                            )
                        }
                        OutlinedButton(
                            onClick = {
                                if (content.isBlank()) {
                                    deleteNote(note.value)
                                } else {
                                    addNote(
                                        Note(
                                            id = note.value.id,
                                            content = content,
                                            time = time,
                                            isCompleted = isCompleted
                                        )
                                    )
                                }
                                showDialog.value = false
                            },
                            shape = CircleShape,
                            colors = ButtonDefaults.outlinedButtonColors(
                                backgroundColor = blue,
                            )
                        ) {
                            Text(
                                text = stringResource(id = R.string.save),
                                color = MaterialTheme.colors.primaryVariant,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        )
    }
}











