package com.example.resikelapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.example.resikelapp.ui.components.SampahItemCard

@Composable
fun KalkulasiScreen(onBackClick: () -> Unit = {}) {
    var sampahCards by remember { mutableStateOf(listOf("Pilih Jenis Sampah", "Pilih Jenis Sampah")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Header Row
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

        Spacer(modifier = Modifier.height(16.dp))

        // SampahItemCard list
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            sampahCards.forEachIndexed { index, selectedWasteType ->
                SampahItemCard(
                    selectedWasteType = selectedWasteType,
                    onWasteTypeSelected = { newSelection ->
                        sampahCards = sampahCards.toMutableList().apply {
                            set(index, newSelection)
                        }
                    },
                    points = "50 Pts"
                )
            }
        }

        // Add Button to add more cards
        IconButton(
            onClick = {
                sampahCards = sampahCards + "Pilih Jenis Sampah"
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(48.dp)
                .padding(top = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Tambah",
                tint = Color(0xFF1E5631)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Total Points Row
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
                text = "${sampahCards.size * 50} Pts", // Assuming each item gives 50 points
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Confirmation Button
        Button(
            onClick = { },
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
