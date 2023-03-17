package ru.cyclone.cycfinans.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun EmptyNoteBox(
    modifier: Modifier = Modifier
) {
//    Box(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(120.dp)
//            .padding(horizontal = 16.dp, vertical = 4.dp)
//            .clip(RoundedCornerShape(16.dp))
//            .background(MaterialTheme.colors.secondary)
//    ) {
//        Icon(
//            imageVector = Icons.Filled.Edit,
//            contentDescription = "add",
//            modifier = modifier
//                .fillMaxSize()
//                .padding(16.dp)
//        )
//    }
    Box(
        modifier = modifier
            .padding()
            .size(48.dp)
            .clip(RoundedCornerShape(24.dp)),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Filled.Edit,
            contentDescription = "new",
        )
    }
}

