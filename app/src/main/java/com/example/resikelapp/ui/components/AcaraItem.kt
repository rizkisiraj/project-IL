package com.example.resikelapp.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.resikelapp.R
import com.example.resikelapp.data.model.Acara
import com.example.resikelapp.data.model.AcaraData
import com.example.resikelapp.data.model.Community
import com.example.resikelapp.data.model.CommunityData

@Composable
fun AcaraItem(
    acara: Acara,
    modifier: Modifier = Modifier,
) {
    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.third),
        ),
        elevation = CardDefaults.elevatedCardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(), // Agar Row mengisi lebar penuh
                verticalAlignment = Alignment.CenterVertically // Menyelaraskan teks secara vertikal
            ) {
                Text(
                    text = acara.nama,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 24.sp
                    ),
                    modifier = Modifier.weight(1f) // Membuat nama teks mengisi ruang tersisa
                )
                Text(
                    text = acara.tanggal,
                    style = MaterialTheme.typography.titleSmall, // Style teks tanggal
                )
            }
            Text(
                modifier = Modifier.padding(top = 20.dp),
                text = acara.description,
                style = MaterialTheme.typography.titleSmall,
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun AcaraItemPreview() {
    MaterialTheme {
        AcaraItem(
            acara = AcaraData.acara[1],
            modifier = Modifier.padding(8.dp)
        )
    }
}
