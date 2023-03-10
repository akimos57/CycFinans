package ru.cyclone.cycfinans.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import java.text.NumberFormat
import java.util.*

@Composable
fun DayBox(
    day: Int,
    modifier: Modifier = Modifier,
    income: Int,
    expenses : Int
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colors.secondary)
    ) {
        Row(
            modifier = modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.1f),
                contentAlignment = Alignment.Center
            ) {
                Text(text = day.toString())
            }
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val incomeString = NumberFormat.getNumberInstance(Locale.US).format(income).replace(',', ' ')
                val expensesString = NumberFormat.getNumberInstance(Locale.US).format(expenses).replace(',', ' ')
                Text(
                    text = "+$incomeString ₽"
                )
                Text(
                    text = "-$expensesString ₽"
                )
            }

        }
    }
}
