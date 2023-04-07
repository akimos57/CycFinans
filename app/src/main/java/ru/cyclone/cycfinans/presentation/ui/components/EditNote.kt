@file:Suppress("DEPRECATION")
package ru.cyclone.cycfinans.presentation.ui.components

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import ru.cyclone.cycfinans.domain.usecases.NotificationController
import ru.cyclone.cycfinans.presentation.ui.theme.*
import ru.cyclone.cycnote.R
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun EditNote(
    note: MutableState<Note>,
    showDialog: MutableState<Boolean>,
    addNote: (Note) -> Unit,
    deleteNote: (Note) -> Unit
) {
    val context = LocalContext.current
    val alarmManager: AlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val hasPermission: Boolean = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        alarmManager.canScheduleExactAlarms()
    } else {
        true
    }

    if (!hasPermission and (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)) {
        @SuppressLint("InlinedApi")
        val intent = Intent().apply {
            action = Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
        }
        context.startActivity(intent)
    }

    if (showDialog.value and hasPermission) {
        var content by rememberSaveable { mutableStateOf(note.value.content) }
        var time by remember { mutableStateOf(note.value.time) }
        var isCompleted by rememberSaveable { mutableStateOf(note.value.isCompleted) }
        var remind by rememberSaveable { mutableStateOf(note.value.remind) }

        val c = Calendar.getInstance()
        val tp = TimePickerDialog(context,
            { _, selectedHour: Int, selectedMinute: Int ->
                val q = Calendar.getInstance()
                q.timeInMillis = time.time
                q.set(Calendar.HOUR_OF_DAY, selectedHour)
                q.set(Calendar.MINUTE, selectedMinute)
                time = Time(q.timeInMillis)
            }, c[Calendar.HOUR_OF_DAY], c[Calendar.MINUTE], true
        )

        tp.setOnDismissListener {
            if (content.isNotBlank()) {
                remind = true
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
                    Column {
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
                                Text(
                                    text = stringResource(id = R.string.remind),
                                    color = MaterialTheme.colors.primaryVariant
                                )
                                Checkbox(
                                    checked = remind,
                                    onCheckedChange = { remind = it },
                                    colors = CheckboxDefaults.colors(blue),
                                    modifier = Modifier
                                        .size(16.dp)
                                        .padding(start = 16.dp,end = 16.dp)
                                )
                            }
                            Text(
                                text = SimpleDateFormat("dd LLL yyyy hh:mm", Locale.getDefault()).format(time),
                                color = MaterialTheme.colors.primaryVariant,
                                fontSize = 18.sp
                            )
                        }
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
                                            isCompleted = isCompleted,
                                            remind = remind
                                        )
                                    )
                                    val calendar: Calendar = Calendar.getInstance().apply {
                                        timeInMillis = time.time
                                        set(Calendar.SECOND, 0)
                                    }
                                    val intent = Intent(context, AlarmReceiver::class.java)
                                    val receiver = ComponentName(context, AlarmReceiver::class.java)
                                    context.packageManager.setComponentEnabledSetting(
                                        receiver,
                                        PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                                        PackageManager.DONT_KILL_APP
                                    )

                                    val pendingIntent =
                                        PendingIntent.getBroadcast(context, calendar.timeInMillis.toInt(), intent, PendingIntent.FLAG_IMMUTABLE)
                                    if (remind) {
                                        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
                                    } else {
                                        alarmManager.cancel(pendingIntent)
                                    }
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
    else if (showDialog.value and !hasPermission) {
        Toast.makeText(context, "Permission error", Toast.LENGTH_SHORT).show()
    }
}

class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        NotificationController.launchSingleNotification()
    }
}











