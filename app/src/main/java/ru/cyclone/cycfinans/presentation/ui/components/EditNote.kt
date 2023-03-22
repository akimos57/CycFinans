@file:Suppress("DEPRECATION")
package ru.cyclone.cycfinans.presentation.ui.components

import android.app.TimePickerDialog
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ru.cyclone.cycfinans.domain.model.Note
import ru.cyclone.cycfinans.presentation.screens.calendar.AddNoteVM
import ru.cyclone.cycfinans.presentation.ui.theme.*
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun EditNote(
    show: Boolean,
    onDismiss: () -> Unit,
    date: Long,
) {
    if (show) {
        var content by rememberSaveable { mutableStateOf("") }
        var time by remember { mutableStateOf(Time(date)) }
        val showDialog = remember { mutableStateOf(false) }

        val vm = hiltViewModel<AddNoteVM>()
        val note by vm.note.observeAsState()

        val c = Calendar.getInstance()
        val tp = TimePickerDialog(LocalContext.current,
            { _, selectedHour: Int, selectedMinute: Int ->
                val q = Calendar.getInstance()
                q.timeInMillis = date
                q.set(Calendar.HOUR_OF_DAY, selectedHour)
                q.set(Calendar.MINUTE, selectedMinute)
                time = Time(q.timeInMillis)
            }, c[Calendar.HOUR_OF_DAY], c[Calendar.MINUTE], true
        )

        AlertDialog(
            onDismissRequest = {

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
                                modifier = Modifier,
//                                    .fillMaxWidth(),
                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = MaterialTheme.colors.secondary,
                                    cursorColor = blue,
                                    focusedIndicatorColor = blue,
                                    unfocusedIndicatorColor = blue,
                                    textColor = MaterialTheme.colors.primaryVariant
                                ),
                                placeholder = {
                                    Text(
                                        text = "Напоминание",
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
                            .clickable { tp.show() },
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(horizontal = 24.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            val isChecked = remember { mutableStateOf(true) }
                            Text(
                                text = "Напомнить",
                                color = MaterialTheme.colors.primaryVariant
                            )
                            Checkbox(
                                checked = isChecked.value,
                                onCheckedChange = { isChecked.value = it },
                                colors = CheckboxDefaults.colors(blue),
                                modifier = Modifier
                                    .size(16.dp)
                                    .padding(start = 16.dp,end = 16.dp)
                            )
                        }
                        Text(
                            text = SimpleDateFormat("HH:mm", Locale.getDefault()).format(time),
                            color = MaterialTheme.colors.primaryVariant,
                            fontSize = 18.sp
                        )
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
                                text = "Отмена",
                                color = MaterialTheme.colors.primaryVariant,
                                fontSize = 14.sp
                            )
                        }
                        OutlinedButton(
                            onClick = {
                                showDialog.value = false
                            },
                            shape = CircleShape,
                            colors = ButtonDefaults.outlinedButtonColors(
                                backgroundColor = blue,
                            )
                        ) {
                            Text(
                                text = "Сохранить",
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











