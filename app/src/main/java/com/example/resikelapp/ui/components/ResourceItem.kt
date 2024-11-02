package com.example.resikelapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForwardIos
import androidx.compose.material.icons.outlined.ArrowForwardIos
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.resikelapp.R

@Composable
fun ResourceItem(
    imageId: Int,
    value: String,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Image(
            painter = painterResource(imageId),
            contentDescription = "coin icon"
        )
        Text(value)
        Icon(
            Icons.AutoMirrored.Outlined.ArrowForwardIos,
            contentDescription = "Show Detail"
        )
    }
}