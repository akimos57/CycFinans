package ru.cyclone.cycfinans.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun SetCurrencyDialog(
    show: MutableState<Boolean>
) {
    if (show.value) {
        Dialog(onDismissRequest = { show.value = false }) {
            Box(
                modifier = Modifier
                    .width(150.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colors.secondary)
            ) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(
                            text = "₽",
                            modifier = Modifier
                                .padding(vertical = 12.dp)
                        )
                        Text(
                            text = "RUB",
                            modifier = Modifier
                                .padding(vertical = 12.dp)
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(
                            text = "$",
                            modifier = Modifier
                                .padding(vertical = 12.dp)
                        )
                        Text(
                            text = "USD",
                            modifier = Modifier
                                .padding(vertical = 12.dp)
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(
                            text = "€",
                            modifier = Modifier
                                .padding(vertical = 12.dp)
                        )
                        Text(
                            text = "EUR",
                            modifier = Modifier
                                .padding(vertical = 12.dp)
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(
                            text = "¥",
                            modifier = Modifier
                                .padding(vertical = 12.dp)
                        )
                        Text(
                            text = "CNY",
                            modifier = Modifier
                                .padding(vertical = 12.dp)
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(
                            text = "₩",
                            modifier = Modifier
                                .padding(vertical = 12.dp)
                        )
                        Text(
                            text = "KPW",
                            modifier = Modifier
                                .padding(vertical = 12.dp)
                        )
                    }
                }
            }
        }
    }
}