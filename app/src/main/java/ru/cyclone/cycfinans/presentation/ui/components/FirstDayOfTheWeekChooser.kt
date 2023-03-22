package ru.cyclone.cycfinans.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import ru.cyclone.cycfinans.data.local.preferences.PreferencesController
import java.time.DayOfWeek

@Composable
fun FirstDayOfTheWeekChooser() {
    val preferencesController = PreferencesController("firstDayOfWeek_table")

    var show by remember { mutableStateOf(false) }
    TextButton(
        onClick = {
            show = true
        },
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            textAlign = TextAlign.Start,
            text = "Choose first day of week",
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colors.onSurface,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 10.dp)
        )
    }
    if (show) {
        Dialog(onDismissRequest = { show = false }) {
            Column {
                for (i in 1..7) {
                    Text(
                        modifier = Modifier
                            .padding(
                                horizontal = 20.dp,
                                vertical = 12.dp
                            )
                            .clickable {
                                preferencesController.fileNameList = mutableListOf((i).toString())
                                preferencesController.saveLists()

                                show = false
                            },
                        text = DayOfWeek.of(i).name
                    )
                }
            }
        }
    }
}