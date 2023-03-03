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
import androidx.navigation.NavHostController
import ru.cyclone.cycfinans.presentation.navigation.AdditionalScreens

@Composable
fun TargetBox(
    modifier: Modifier,
//    title: String,

) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colors.secondary)
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, top = 16.dp)
            ) {
                Text(
                    text = "Поездка в Москву",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp)
            ) {
                Column() {
                    Text(
                        text = "Собрано 32 000 ₽",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal
                    )
                    Text(
                        text = "Осталось 8 000 ₽",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal
                    )
                }

            }


        }

    }
}