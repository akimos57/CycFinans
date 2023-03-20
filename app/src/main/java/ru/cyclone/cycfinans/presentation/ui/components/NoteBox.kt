package ru.cyclone.cycfinans.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.cyclone.cycfinans.domain.model.Note
import ru.cyclone.cycfinans.presentation.ui.theme.blue
import ru.cyclone.cycfinans.presentation.ui.theme.fab1
import java.sql.Date
import java.sql.Time
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun NoteBox(
    modifierClickable: Modifier,
    modifierSizeBox: Modifier,
    note: Note,
) {
//    var time2 by remember { mutableStateOf(Time(date)) }
//    val time3 = SimpleDateFormat("HH:mm", Locale.getDefault()).format(time2)
    Box(
        modifier = modifierSizeBox
            .fillMaxWidth()
            .height(120.dp)
            .padding()
            .clip(RoundedCornerShape(16.dp))
            .border(width = 1.3.dp, color = fab1, shape = RoundedCornerShape(16.dp))
            .background(MaterialTheme.colors.secondary)
    ) {
        Column(
            modifier = modifierClickable
                .fillMaxSize()
                .padding(start = 16.dp, top = 16.dp)
        ) {

            val time = DateFormat.getDateInstance().format(Date(note.time.time))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = time,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                )
                Row(
                    modifier = Modifier
                        .padding(horizontal = 24.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val isChecked = remember { mutableStateOf(true) }
                    Text(text = "Напомнить")
                    Checkbox(
                        checked = isChecked.value,
                        onCheckedChange = { isChecked.value = it },
                        colors = CheckboxDefaults.colors(fab1),
                        modifier = Modifier
                            .size(16.dp)
                            .padding(start = 16.dp)
                    )
                }

            }

                Text(
                    text = note.content,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )


            }
        }
    }

