package com.example.resikelapp.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.resikelapp.R
import com.example.resikelapp.ui.components.ResourceItem

@Preview(showBackground = true)
@Composable
fun BerandaScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.profile),
                contentDescription = "image profile",
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Text(
                "Welcome, Resikel",
                modifier = Modifier.weight(1f)
            )
            Icon(
                Icons.Outlined.Notifications,
                contentDescription = "Notifikasi"
            )
        }

        Spacer(modifier = Modifier.height(48.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Green)
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ResourceItem(
                R.drawable.coin_image,
                "Rp1.000"
            )
            ResourceItem(
                R.drawable.coin_image,
                "Rp1.000"
            )
        }
    }
}