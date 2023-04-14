package ru.cyclone.cycfinans.presentation.ui.components.settings.selectors

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import ru.cyclone.cycfinans.data.local.preferences.PreferencesController

object AvailableTimeFormats {
    val availableTimeFormats = mapOf(
        Pair("12-hour", "h:mm a"),
        Pair("24-hour", "HH:mm")
    )
}

@Composable
fun TimeFormatSelector(
    show: MutableState<Boolean>
) {
    val preferencesController = PreferencesController("time_format_table")
    if (show.value) {
        Dialog(onDismissRequest = { show.value = false }) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colors.secondary)
            ) {
                Column {
                    for (itemTimeFormat in AvailableTimeFormats.availableTimeFormats) {
                        Text(
                            text = itemTimeFormat.key,
                            fontSize = 18.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp, horizontal = 24.dp)
                                .clickable {
                                    show.value = false
                                    preferencesController.fileNameList =
                                        mutableListOf(itemTimeFormat.value)
                                    preferencesController.saveLists()
                                }
                        )
                    }
                }
            }
        }
    }
}