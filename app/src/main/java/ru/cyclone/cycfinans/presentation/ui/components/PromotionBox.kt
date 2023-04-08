@file:Suppress("DEPRECATION")

package ru.cyclone.cycfinans.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.cyclone.cycfinans.data.local.preferences.PreferencesController
import ru.cyclone.cycfinans.domain.model.Promotion
import ru.cyclone.cycfinans.presentation.ui.theme.fab1
import ru.cyclone.cycfinans.presentation.ui.theme.fab2
import java.math.BigDecimal
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun PromotionBox(
    promotion: Promotion,
    modifier: Modifier
) {
    val preferencesController = PreferencesController("currency_table")
    val currency = preferencesController.fileNameList.last()
    Column(modifier = Modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colors.secondary)
        ) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(16.dp))
                    .padding(top = 24.dp)
            ) {
                val price = NumberFormat.getNumberInstance(Locale.US).format(BigDecimal(promotion.price)).replace(',', ' ')
                val text = if (promotion.type) "+ $price $currency" else "- $price $currency"
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    text = text,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )
                if (promotion.category.isEmpty()) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally),
                        text = "Другое",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center,
                        color = if(promotion.type) fab1 else fab2
                    )
                } else {
                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally),
                        text = promotion.category,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center,
                        color = if(promotion.type) fab1 else fab2
                    )
                }
                Text(
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(end = 6.dp),
                    text = SimpleDateFormat("hh:mm", Locale.getDefault()).format(promotion.time),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}