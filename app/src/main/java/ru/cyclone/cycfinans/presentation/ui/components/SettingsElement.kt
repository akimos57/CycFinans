package ru.cyclone.cycfinans.presentation.ui.components

import android.media.Image
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.cyclone.cycnote.R

@Composable
fun SettingsElement(
    title: String,
    icon: Int,
    modifier: Modifier
) {
    Column {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(60.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.1f),
                horizontalArrangement = Arrangement.Center
            ) {
                
            }
            Text(
                textAlign = TextAlign.Start,
                text = title,
                fontSize = 17.sp,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp, horizontal = 10.dp)
            )
        }
        Spacer(modifier = Modifier
            .fillMaxWidth(0.8f)
            .height(0.5.dp)
            .background(colorResource(id = R.color.secondary_gray))
        )
    }

}