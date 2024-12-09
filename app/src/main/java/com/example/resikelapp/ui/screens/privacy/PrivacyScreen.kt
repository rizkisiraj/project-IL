package com.example.resikelapp.ui.screens.privacy

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.resikelapp.R
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun PrivacyScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    var (checkedState, onStateChange) = remember { mutableStateOf(false) }

    Scaffold(
        content = { innerPadding ->
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(vertical = 70.dp)
                    .systemBarsPadding()
                    .padding(horizontal = 35.dp)
                    .verticalScroll(rememberScrollState()) // Enable scrolling
            ) {
                Text(
                    modifier = Modifier
                        .padding(bottom = 18.dp)
                        .fillMaxWidth(),
                    fontSize = 25.sp,
                    text = "Syarat dan Ketentuan",
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                )

                Text(
                    modifier = Modifier
                        .padding(bottom = 20.dp),
                    text = "Dengan menggunakan layanan Resikel, Anda setuju untuk mematuhi syarat dan ketentuan berikut:",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black,
                    lineHeight = 18.sp
                )

                // Daftar syarat dan ketentuan
                val syaratList = listOf(
                    "1. Pengguna yang Layak: Anda harus berusia minimal [umur minimal] tahun untuk menggunakan layanan kami.",
                    "2. Perubahan Layanan: Kami berhak untuk mengubah syarat dan ketentuan kapan saja. Perubahan akan berlaku setelah dipublikasikan.",
                    "3. Akun Pengguna: Anda bertanggung jawab atas keamanan akun dan aktivitas di dalamnya.",
                    "4. Kekayaan Intelektual: Semua konten dilindungi oleh hak cipta. Penggunaan tanpa izin dilarang.",
                    "5. Penggunaan yang Dilarang: Anda dilarang menggunakan layanan kami untuk tujuan ilegal atau merugikan.",
                    "6. Pembatasan Tanggung Jawab: Kami tidak bertanggung jawab atas kerugian yang timbul dari penggunaan layanan.",
                    "7. Hukum yang Berlaku: Syarat ini diatur oleh hukum Indonesia."
                )

                // Menampilkan daftar syarat dan ketentuan
                syaratList.forEach { syarat ->
                    Text(
                        text = syarat,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Black,
                        lineHeight = 18.sp
                    )
                }

                Text(
                    modifier = Modifier
                        .padding(top = 20.dp),
                    text = "Jika ada pertanyaan, hubungi kami di kontak kami.",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black,
                    lineHeight = 18.sp
                )

                // Checkbox untuk menyetujui syarat dan ketentuan
                Row(
                    Modifier.fillMaxWidth()
                        .height(56.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = checkedState,
                        onCheckedChange = { checked -> onStateChange(checked) }, // Mengubah status checkedState
                        colors = CheckboxDefaults.colors(
                            checkedColor = colorResource(id = R.color.primary), // Ganti dengan warna yang sesuai
                            uncheckedColor = Color.Gray // Atur warna ketika checkbox tidak dicentang
                        )
                    )
                    Text(
                        text = "Syarat dan Ketentuan berlaku",
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }

                // Tombol Selesai
                Button(
                    modifier = Modifier
                        .padding(bottom = 60.dp)
                        .width(200.dp)
                        .align(alignment = Alignment.CenterHorizontally),
                    onClick = {
                        navController.navigateUp()
                    },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = colorResource(id = R.color.third),
                        containerColor = colorResource(R.color.primary)
                    ),
                ) {
                    Text(text = "Selesai", fontWeight = FontWeight.Medium)
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PrivacyScreenPreview() {
    MaterialTheme {
        PrivacyScreen(navController = rememberNavController())
    }
}
