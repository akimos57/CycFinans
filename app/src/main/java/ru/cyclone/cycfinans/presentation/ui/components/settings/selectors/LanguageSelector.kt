package ru.cyclone.cycfinans.presentation.ui.components.settings.selectors

import android.content.res.Configuration
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import ru.cyclone.cycfinans.data.local.preferences.PreferencesController
import java.util.*


@Composable
fun LanguageSelector(
    show: MutableState<Boolean>
) {
    val context = LocalContext.current
    val preferencesController = PreferencesController("locale_table")
    val availableLocales = listOf(
        "en",
        "ru",
        "zh",
        "ko",
    )
    if (show.value) {
        Dialog(onDismissRequest = { show.value = false }) {
            Box(
                modifier = Modifier
                    .width(200.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colors.secondary)
            ) {
                Column(
                    Modifier.verticalScroll(rememberScrollState())
                ) {
                    for (itemLocale in availableLocales) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    preferencesController.fileNameList =
                                        mutableListOf(itemLocale)
                                    preferencesController.saveLists()

                                    show.value = false
                                    context.resources.apply {
                                        val locale = Locale.forLanguageTag(itemLocale)
                                        val config = Configuration(configuration)

                                        context.createConfigurationContext(configuration)
                                        Locale.setDefault(locale)
                                        config.setLocale(locale)
                                        @Suppress("DEPRECATION")
                                        context.resources.updateConfiguration(
                                            config,
                                            displayMetrics
                                        )
                                    }
                                }
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(
                                        horizontal = 20.dp,
                                        vertical = 12.dp
                                    ),
                                text = Locale.forLanguageTag(itemLocale)
                                    .getDisplayLanguage(Locale.forLanguageTag(itemLocale))
                                    .capitalize(androidx.compose.ui.text.intl.Locale.current)
                            )
                        }
                    }
                }
            }
        }
    }
}