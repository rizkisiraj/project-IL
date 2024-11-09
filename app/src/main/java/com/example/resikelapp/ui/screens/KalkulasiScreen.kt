package com.example.resikelapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.resikelapp.R

@Composable
fun KalkulasiScreen(onBackClick: () -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "Back",
                    tint = Color(0xFF1E5631)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Kalkulasi Poin",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E5631)
            )
        }

        Spacer(modifier = Modifier.height(106.dp))


        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SampahItemCard("Kertas", "50 Pts")
            SampahItemCard("Plastik", "50 Pts")


            IconButton(
                onClick = { /* TODO: Add item action */ },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(48.dp)
                    .padding(top = 8.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = "Tambah",
                    tint = Color(0xFF1E5631)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFE0E0E0))
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Total",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "100 Pts",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { /* TODO: Confirm action */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFF1E5631))
        ) {
            Text(text = "Konfirmasi", color = Color.White, fontSize = 16.sp)
        }
    }
}

@Composable
fun SampahItemCard(title: String, points: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp), // Menambah tinggi card agar lebih fleksibel
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E5631))
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Konten card (Text dan Arrow DropDown)
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(19.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Column for Sampah Title and kg
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f), // Menambahkan weight agar kolom ini mengambil ruang yang lebih besar
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = title,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Arrow Down",
                            tint = Color.White,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp)) // Spacer to add some space between title and kg text
                    Text(
                        text = "___ Kg", // Teks default yang ditampilkan
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 14.sp
                    )
                }
            }

            // Teks poin di sebelah kanan dengan styling dari Figma
            Text(
                text = points, // Menampilkan poin yang diteruskan sebagai parameter
                style = TextStyle(
                    fontSize = 25.sp,
                    lineHeight = 36.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_bold)), // Pastikan font Roboto sudah ada di project
                    fontWeight = FontWeight(600),
                    color = Color(0xFFFFFFFF),
                ),
                modifier = Modifier
                    .align(Alignment.CenterEnd) // Menyusun teks di bagian kanan card
                    .padding(end = 16.dp) // Memberikan sedikit ruang dari tepi kanan
            )

            // Tanda silang di pojok kanan atas
            IconButton(
                onClick = { /* TODO: Add action for close */ },
                modifier = Modifier
                    .size(24.dp) // Menetapkan ukuran ikon
                    .align(Alignment.TopEnd) // Mengatur ikon agar berada di pojok kanan atas
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_close), // Pastikan path dan nama file sesuai
                    contentDescription = "Close",
                    modifier = Modifier.fillMaxSize(),
                    tint = Color.White // Mengatur warna ikon agar terlihat dengan baik
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewKalkulasiScreen() {
    KalkulasiScreen()
}
