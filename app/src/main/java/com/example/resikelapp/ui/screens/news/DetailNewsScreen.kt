package com.example.resikelapp.ui.screens.news

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.resikelapp.R

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DetailNewsScreen() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.sampah_placeholder_image),
                contentDescription = "Sampul News",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(136.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    "Buruknya Pengelolaan Sampah di Batam",
                    style = MaterialTheme.typography.titleMedium,
                    )
                Text(
                    "Updated 20 Oktober 2024",
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        }
        
        Spacer(modifier =  Modifier.height(20.dp))

        Text(
            "Pengelolaan sampah di Batam menghadapi tantangan serius akibat kurangnya sumber daya dan rendahnya partisipasi masyarakat dalam proses daur ulang. Menurut laporan terbaru, volume sampah terus meningkat, sementara fasilitas pengelolaan sampah yang ada tidak mampu menangani beban tersebut secara optimal. \n" +
                    "\n" +
                    "Banyak sampah yang berakhir di tempat pembuangan akhir (TPA) tanpa melalui proses pemilahan yang benar, sehingga memperparah masalah pencemaran lingkungan. Selain itu, kurangnya edukasi tentang pentingnya pengelolaan sampah yang baik membuat masyarakat belum sepenuhnya sadar akan pentingnya memilah dan mendaur ulang sampah. ",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}