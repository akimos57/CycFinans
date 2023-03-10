@file:Suppress("DEPRECATION")

package ru.cyclone.cycfinans.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import ru.cyclone.cycfinans.domain.model.Promotion
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun PromotionBox(
    promotion: Promotion,
    modifier: Modifier = Modifier
) {
    Column(modifier = Modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colors.secondary)
        ) {
            Column(
                modifier = modifier
                    .padding(16.dp)
            ) {
                val price = String.format(Locale.getDefault(), "%,d", promotion.price)
                val text = if (promotion.type) "+$price ₽" else "-$price ₽"
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    text = text,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center
                )
                if (promotion.category.isEmpty()) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally),
                        text = "Others",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center
                    )
                } else {
                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally),
                        text = promotion.category,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center
                    )
                }
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    text = SimpleDateFormat("hh:mm", Locale.getDefault()).format(promotion.time),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}