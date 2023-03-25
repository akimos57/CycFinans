package ru.cyclone.cycfinans.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.NumberFormat
import java.util.*
import ru.cyclone.cycfinans.presentation.ui.theme.fab1
import ru.cyclone.cycnote.R

@Composable
fun MainBox(
    modifier: Modifier,
    income: Int,
    expenses: Int
) {
    // Средств осталось
    val currentPrice = income - expenses
    // for LinearProgress
    val progress = if (income != 0) currentPrice/income.toFloat() else 0F


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
                    val incomeString = NumberFormat.getNumberInstance(Locale.US).format(income).replace(',', ' ')
                    Text(
                        text = "$incomeString ₽",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    LinearProgress(
                        progress = progress,
                        color = fab1,
                        width = 250.dp,
                        height = 12.dp
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
                        val expensesString = NumberFormat.getNumberInstance(Locale.US).format(expenses).replace(',', ' ')
                        val currentPriceString = NumberFormat.getNumberInstance(Locale.US).format(currentPrice).replace(',', ' ')
                        Text(
                            text = stringResource(id = R.string.spent)+": $expensesString ₽",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = stringResource(id = R.string.left)+": $currentPriceString ₽",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}