package ru.cyclone.cycfinans.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.cyclone.cycfinans.presentation.ui.theme.fab1

@Composable
fun WidgetMain() {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(130.dp)
                .padding(start = 12.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
                .border(width = 1.3.dp, color = fab1, shape = RoundedCornerShape(16.dp))
                .clickable {  }
        ) {
            Text(
                text = "Создайте быструю заметку",
                modifier = Modifier
                    .padding(16.dp)
            )
        }
    }
