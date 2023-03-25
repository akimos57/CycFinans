package ru.cyclone.cycfinans.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import ru.cyclone.cycfinans.data.local.preferences.PreferencesController
import java.time.DayOfWeek

@Composable
fun FirstDayOfTheWeekChooser(
    show: MutableState<Boolean>
) {
    val preferencesController = PreferencesController("firstDayOfWeek_table")
    if (show.value) {
        Dialog(onDismissRequest = { show.value = false }) {
            Box(
                modifier = Modifier
                    .width(200.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colors.secondary)
            ) {
                Column {
                    for (i in 1..7) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    preferencesController.fileNameList =
                                        mutableListOf((i).toString())
                                    preferencesController.saveLists()

                                    show.value = false
                                }
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(
                                        horizontal = 20.dp,
                                        vertical = 12.dp
                                    ),
                                text = DayOfWeek.of(i).name
                            )
                        }
                    }
                }
            }
        }
    }
}