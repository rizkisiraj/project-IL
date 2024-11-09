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
import com.example.resikelapp.data.model.Community
import com.example.resikelapp.data.model.CommunityData

@Composable
fun CommunityItem(
    nama: String,
    description: String,
    jenisSampah: List<String>,
    modifier: Modifier = Modifier,
) {
    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.third),
        ),
        shape = RectangleShape,
        elevation = CardDefaults.elevatedCardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = nama,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 24.sp
                ),
            )
            Text(
                text = description,
                style = MaterialTheme.typography.titleSmall,
            )

            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
            ) {
                jenisSampah.forEach { jenis ->
                    Card(
                        modifier = Modifier
                            .border(BorderStroke(1.dp, Color.Black), shape = RoundedCornerShape(30.dp)),
                        colors = CardDefaults.cardColors(containerColor = colorResource(R.color.third))
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 40.dp)
                                .padding(vertical = 8.dp)
                        ){
                            Text(
                                text = jenis,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(20.dp))
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun CommunityItemPreview() {
    MaterialTheme {
        CommunityItem(
            nama = "Bottle Busters",
            description = "Komunitas Pemerhati Botol Plastik",
            jenisSampah = listOf("Botol", "Plastik"),
        )
    }
}