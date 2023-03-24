package ru.cyclone.cycfinans.presentation.ui.components

import android.media.Image
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.cyclone.cycnote.R

@Composable
fun SettingsElement(
    title: String,
    icon: Int,
    click: () -> Unit
) {
    TextButton(
        onClick = click,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(0.2f),
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = Color.Gray
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                textAlign = TextAlign.Start,
                text = title,
                fontSize = 17.sp,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .background(colorResource(id = R.color.secondary_gray))
            )
        }
    }
//    Column {
//        Row(
//            modifier = modifier
//                .fillMaxWidth()
//                .height(60.dp),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.Start
//        ) {
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth(0.2f),
//                horizontalArrangement = Arrangement.Center
//            ) {
//                Icon(
//                    painter = painterResource(id = icon),
//                    contentDescription = null,
//                    tint = Color.Gray
//                )
//            }
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//            ) {
//                Text(
//                    textAlign = TextAlign.Start,
//                    text = title,
//                    fontSize = 17.sp,
//                    fontWeight = FontWeight.Normal,
//                    color = MaterialTheme.colors.onSurface,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(vertical = 10.dp)
//                )
//                Spacer(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(4.dp)
//                        .background(colorResource(id = R.color.secondary_gray))
//                )
//            }
//        }
//    }
}