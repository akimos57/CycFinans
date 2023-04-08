package ru.cyclone.cycfinans.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import ru.cyclone.cycfinans.data.local.preferences.PreferencesController

@Composable
fun CurrencySelector(
    show: MutableState<Boolean>
) {
    val preferencesController = PreferencesController("currency_table")
    val availableCurrency = listOf(
        "₽\t\tRUB",
        "$\t\tUSD",
        "€\t\tEUR",
        "¥\t\tCNY",
        "₩\t\tKPW"
    )
    if (show.value) {
        Dialog(onDismissRequest = { show.value = false }) {
            Box(
                modifier = Modifier
                    .width(150.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colors.secondary)
            ) {
                Column(
                    Modifier.verticalScroll(rememberScrollState())
                ) {
                    for (itemCurrency in availableCurrency) {
                        Text(
                            text = itemCurrency,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp)
                                .clickable {
                                    show.value = false
                                    preferencesController.fileNameList =
                                        mutableListOf(itemCurrency.first().toString())
                                    preferencesController.saveLists()
                                }
                        )
                    }
                }
            }
        }
    }
}