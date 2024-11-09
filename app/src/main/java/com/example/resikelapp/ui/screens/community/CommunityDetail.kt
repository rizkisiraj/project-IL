package com.example.resikelapp.ui.screens.community

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.resikelapp.R
import com.example.resikelapp.data.model.AcaraData
import com.example.resikelapp.ui.components.AcaraItem
import com.example.resikelapp.ui.components.CommunityItem
import com.example.resikelapp.ui.theme.ResikelAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommunityDetailScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        TopAppBar(
            title = {
                Text(text = "", fontSize = 20.sp)
            },
            navigationIcon = {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_left), // Ganti dengan nama drawable Anda
                        contentDescription = "Back",
                        tint = colorResource(id = R.color.primary)
                    )
                }
            },
            actions = {},
        )

        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            Column {
                Row(
                    modifier = Modifier.padding(top = 20.dp, start = 16.dp, end = 16.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.gbr_detail),
                        contentDescription = "Gambar Detail",
                        modifier = Modifier.size(136.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = "Paper Fighter",
                            modifier = Modifier.padding(bottom = 8.dp),
                            style = TextStyle(fontSize = 26.sp),

                        )
                        Text(
                            text = "Mengubah kertas bekas jadi tas belanja keren – bantu kami kurangi sampah!",
                            style = TextStyle(fontSize = 12.sp)
                        )
                        Button(
                            onClick = { /* Aksi tombol */ },
                            shape = RoundedCornerShape(50), // Membuat tombol berbentuk oval
                            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.primary)), // Ubah warna tombol
                        ) {
                            // Menggunakan Row untuk menata ikon dan teks secara horizontal
                            Row (verticalAlignment = Alignment.CenterVertically)
                            {
                                // Ikon plus di sebelah kiri teks
                                Icon(
                                    imageVector = Icons.Filled.Add,
                                    contentDescription = "Add",
                                    tint = Color.White // Ikon berwarna putih
                                )
                                Spacer(modifier = Modifier.width(6.dp)) // Memberikan jarak antara ikon dan teks
                                Text(text = "Gabung", color = Color.White) // Teks pada tombol dengan warna putih
                            }
                        }
                    }
                }
                Text(
                    modifier = Modifier.padding(30.dp),
                    text = "Paper Fighter, adalah komunitas yang berfokus pada daur ulang sampah kertas demi menjaga lingkungan. Anggota kami berkumpul untuk berbagi pengetahuan dalam mengolah kertas bekas menjadi produk baru yang kreatif dan bermanfaat. \n" +
                            "\n" +
                            "Melalui berbagai kegiatan menarik, kami bertujuan mengurangi limbah kertas serta meningkatkan kesadaran masyarakat tentang pentingnya daur ulang. Kami berkomitmen untuk membangun jaringan yang solid di antara para pecinta lingkungan.",
                    textAlign = TextAlign.Justify
                )
                Text(
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                    text = "Acara",
                    color = colorResource(R.color.primary),
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 14.dp, end = 14.dp)
                ) {
                    items(AcaraData.acara) { acara ->
                        AcaraItem(acara = acara)
                    }
                }


            }

        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun DetailScreenPreview() {
//    // You can preview the DetailScreen without navigation logic for now
//    ResikelAppTheme {
//        DetailScreen(navController = NavHostController(context = LocalContext.current))
//    }
//}
