package com.example.resikelapp.ui.screens.community

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.resikelapp.R
import com.example.resikelapp.ui.components.Search

@Composable
fun Community(
    modifier: Modifier = Modifier
) {
    Search()
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.align(Alignment.Center)
        ) {
            Image(
                painter = painterResource(id = R.drawable.gambar_belum_bergabung),
                contentDescription = "Centered Image"
            )
            Text(
                text = "Kamu Belum Bergabung Komunitas Nih,\n Yuk Gabung Sekarang",
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CommunityPreview() {
    Community()
}
