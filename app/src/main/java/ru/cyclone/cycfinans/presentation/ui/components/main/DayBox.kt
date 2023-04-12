package ru.cyclone.cycfinans.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.cyclone.cycfinans.data.local.preferences.PreferencesController
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.*

@Composable
fun DayBox(
    colorDay: Color,
    day: Int,
    modifier: Modifier = Modifier,
    income: BigDecimal,
    expenses: BigDecimal
) {
    val preferencesController = PreferencesController("currency_table")
    val currency = preferencesController.fileNameList.last()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(colorDay)
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
                Text(
                    text = day.toString(),
                    fontWeight = FontWeight.Bold
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val incomeString = NumberFormat.getNumberInstance(Locale.US).format(income).replace(',', ' ')
                val expensesString = NumberFormat.getNumberInstance(Locale.US).format(expenses).replace(',', ' ')
                Row(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(0.5f),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "+ $incomeString $currency"
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "- $expensesString $currency"
                    )
                }
            }
        }
    }
}
