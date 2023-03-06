package ru.cyclone.cycfinans.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainBox(modifier: Modifier) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colors.secondary)

    ) {
        Row(
            modifier = modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "58 000 ₽",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 24.dp)
                ) {
                    Column(
                        modifier = Modifier
                    ) {
                        Text(
                            text = "Потрачено: 30 000 ₽",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal
                        )
                        Text(
                            text = "Осталось: 28 000 ₽",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal
                        )
                    }
                }
            }
        }
    }
}