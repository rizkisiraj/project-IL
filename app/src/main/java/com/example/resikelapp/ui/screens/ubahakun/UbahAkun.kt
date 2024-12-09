package com.example.resikelapp.ui.screens.ubahakun

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.resikelapp.R
import com.example.resikelapp.ui.screens.ubahsandi.CustomDialogSimpan

@Composable
fun UbahAkun(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    // State untuk kata sandi baru dan konfirmasi kata sandi
    val (namaDepan, setNamaDepan) = remember { mutableStateOf("") }
    val (namaBelakang, setNamaBelakang) = remember { mutableStateOf("") }
    val (email, setEmail) = remember { mutableStateOf("") }
    val (kota, setKota) = remember { mutableStateOf("") }

    Scaffold(
        content = { innerPadding ->
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(vertical = 70.dp) // Menjaga jarak atas dan bawah
                    .systemBarsPadding() // Menyesuaikan dengan status bar atau navigation bar
            ) {
                // Membuat kolom untuk inputan dan tombol simpan
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState()) // Membuat layar bisa di-scroll
                        .padding(horizontal = 35.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxWidth(), // Membuat Box utama memenuhi lebar layar
                        contentAlignment = Alignment.Center // Menyelaraskan konten ke tengah secara horizontal
                    ) {
                        Box(contentAlignment = Alignment.TopEnd) {
                            Box(
                                modifier = Modifier
                                    .size(120.dp) // Ukuran gambar
                                    .clip(CircleShape) // Bentuk lingkaran pada gambar
                                    .background(Color.LightGray) // Warna background jika gambar gagal dimuat
                            ) {
                                // Menampilkan gambar utama
                                Image(
                                    painter = painterResource(id = R.drawable.imageagnesia), // Ganti dengan resource gambar Anda
                                    contentDescription = "Profile Image",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .offset(x = 14.dp, y = (0).dp)
                                    .size(30.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.edit), // Replace with your edit icon resource
                                    contentDescription = "Edit Profile",
                                    modifier = Modifier.size(25.dp),
                                    tint = Color.Unspecified,
                                )
                            }
                        }
                    }

                    // Input Kata Sandi Baru
                    Text(
                        modifier = Modifier.padding(top = 8.dp),
                        text = "Nama Depan", color = colorResource(R.color.primary), fontWeight = FontWeight.Medium,)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .background(Color.Transparent),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        BasicTextField(
                            value = namaDepan,
                            onValueChange = setNamaDepan,
                            modifier = Modifier
                                .weight(1f), // TextField akan mengisi ruang yang tersisa
                            singleLine = true,
                            textStyle = TextStyle(fontSize = 18.sp)
                        )
                    }
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(2.dp)
                            .background(color = colorResource(R.color.primary))
                    )

                    Text(
                        modifier = Modifier.padding(top = 8.dp),
                        text = "Nama Belakang", color = colorResource(R.color.primary), fontWeight = FontWeight.Medium,)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .background(Color.Transparent),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        BasicTextField(
                            value = namaBelakang,
                            onValueChange = setNamaBelakang,
                            modifier = Modifier
                                .weight(1f), // TextField akan mengisi ruang yang tersisa
                            singleLine = true,
                            textStyle = TextStyle(fontSize = 18.sp)
                        )
                    }
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(2.dp)
                            .background(color = colorResource(R.color.primary))
                    )

                    Text(
                        modifier = Modifier.padding(top = 8.dp),
                        text = "Alamat Email", color = colorResource(R.color.primary), fontWeight = FontWeight.Medium,)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .background(Color.Transparent),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        BasicTextField(
                            value = email,
                            onValueChange = setEmail,
                            modifier = Modifier
                                .weight(1f), // TextField akan mengisi ruang yang tersisa
                            singleLine = true,
                            textStyle = TextStyle(fontSize = 18.sp)
                        )
                    }
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(2.dp)
                            .background(color = colorResource(R.color.primary))
                    )

                    Text(
                        modifier = Modifier.padding(top = 8.dp),
                        text = "Kota", color = colorResource(R.color.primary), fontWeight = FontWeight.Medium,)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .background(Color.Transparent),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        BasicTextField(
                            value = kota,
                            onValueChange = setKota,
                            modifier = Modifier
                                .weight(1f), // TextField akan mengisi ruang yang tersisa
                            singleLine = true,
                            textStyle = TextStyle(fontSize = 18.sp)
                        )
                    }
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(2.dp)
                            .background(color = colorResource(R.color.primary))
                    )

                    // Tombol Simpan
                    CustomDialogSimpan("Simpan")
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun UbahAkunPreview() {
    MaterialTheme {
        UbahAkun(navController = rememberNavController())
    }
}
