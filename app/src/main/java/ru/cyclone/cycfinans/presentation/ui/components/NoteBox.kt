package ru.cyclone.cycfinans.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.cyclone.cycfinans.domain.model.Note
import java.sql.Date
import java.sql.Time
import java.text.DateFormat

@Composable
fun NoteBox(
    modifier: Modifier,
    note: Note
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colors.secondary)
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(start = 16.dp, top = 16.dp)
        ) {

            val time = DateFormat.getDateInstance().format(Date(note.time.time))
            Text(
                text = time,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal
            )
                Text(
                    text = note.content,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }

