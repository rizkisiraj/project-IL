package com.example.resikelapp.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.resikelapp.R
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.resikelapp.data.model.Screen


@Composable
fun ProfileScreen(
    navController: NavController,
    ) {
    var isNightModeEnabled by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.imageagnesia),
                contentDescription = "image profile",
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Agnesia", fontSize = 25.sp)
                Text(text = "agnesia@gmail.com")
            }

            Icon(
                painter = painterResource(id = R.drawable.edit), // Replace with your edit icon resource
                contentDescription = "Edit Profile",
                modifier = Modifier.size(35.dp),
                tint = Color.Unspecified
            )
        }
        Spacer(modifier = Modifier.height(25.dp))


        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.LightGray)
        )

        Spacer(modifier = Modifier.height(35.dp))

        ElevatedCard(
            colors = CardDefaults.cardColors(
                containerColor = colorResource(id = R.color.third)
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth()
        ) {
            Column (
                modifier = Modifier
                    .padding(16.dp)
            ){
                Text(
                    text = "PENJEMPUTAN SELANJUTNYA",
                    fontSize = 14.sp,
                    style = MaterialTheme.typography.titleSmall,
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween, // Menempatkan teks di kiri dan ikon di kanan
                    verticalAlignment = Alignment.CenterVertically // Menjaga teks dan ikon berada di tengah vertikal
                ) {
                    Column {
                        Text(
                            text = "13 September",
                            fontSize = 28.sp,
                            style = MaterialTheme.typography.titleSmall,
                        )
                        Text(
                            text = "12.30 WIB",
                            Modifier.padding(top = 8.dp),
                            style = MaterialTheme.typography.titleSmall,
                        )
                    }
                    Icon(
                        painter = painterResource(id = R.drawable.dump_truck), // Replace with your icon resource
                        contentDescription = "Dump Truck Icon",
                        modifier = Modifier
                            .size(80.dp) // Membesarkan ukuran ikon
                            .align(Alignment.CenterVertically), // Menjaga ikon sejajar vertikal dengan teks
                        tint = Color.Unspecified // This preserves the original color of the icon
                    )
                }


            }
        }

        // Two cards side by side
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            // Card 2
            ElevatedCard(
                colors = CardDefaults.cardColors(
                    containerColor = colorResource(id = R.color.third)
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 6.dp
                ),
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .weight(1f)
            ) {
                Column (
                    modifier = Modifier
                        .padding(16.dp)
                ){
                    Text(
                        text = "KOIN RESIKEL",
                        fontSize = 14.sp,
                        style = MaterialTheme.typography.titleSmall,
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween, // Menempatkan teks di kiri dan ikon di kanan
                        verticalAlignment = Alignment.CenterVertically // Menjaga teks dan ikon berada di tengah vertikal
                    ) {
                        Text(
                            text = "2000",
                            fontSize = 28.sp,
                            style = MaterialTheme.typography.titleSmall,
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.coin), // Replace with your icon resource
                            contentDescription = "Dump Truck Icon",
                            modifier = Modifier
                                .size(50.dp) // Membesarkan ukuran ikon
                                .align(Alignment.CenterVertically), // Menjaga ikon sejajar vertikal dengan teks
                            tint = Color.Unspecified // This preserves the original color of the icon
                        )
                    }


                }
            }

            // Card 3
            ElevatedCard(
                colors = CardDefaults.cardColors(
                    containerColor = colorResource(id = R.color.third)
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 6.dp
                ),
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .weight(1f)
            ) {
                Column (
                    modifier = Modifier
                        .padding(16.dp)
                ){
                    Text(
                        text = "TOTAL SAMPAH",
                        fontSize = 14.sp,
                        style = MaterialTheme.typography.titleSmall,
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "156 Kg",
                            fontSize = 28.sp,
                            style = MaterialTheme.typography.titleSmall,
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.trash),
                            contentDescription = "Dump Truck Icon",
                            modifier = Modifier
                                .size(50.dp)
                                .align(Alignment.CenterVertically),
                            tint = Color.Unspecified
                        )
                    }


                }
            }
        }

        Spacer(
            modifier = Modifier
                .padding(top = 28.dp, bottom = 28.dp)
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.LightGray)
        )

        Button(
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.primary),
                contentColor = Color.White
            ),
            onClick = { }
        ) {
            Row(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.aktivitas),
                        contentDescription = "Left Icon",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Aktivitas")
                }
                Icon(
                    painter = painterResource(id = R.drawable.arrow_right),
                    contentDescription = "Right Icon",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
        Button(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.primary),
                contentColor = Color.White
            ),
            onClick = {
                navController.navigate(Screen.News.route!!)
            }
        ) {
            Row(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.archive),
                        contentDescription = "Left Icon",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Disimpan")
                }
                Icon(
                    painter = painterResource(id = R.drawable.arrow_right),
                    contentDescription = "Right Icon",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
        Spacer(
            modifier = Modifier
                .padding(top = 28.dp, bottom = 28.dp)
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.LightGray)
        )

        Button(
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.primary),
                contentColor = Color.White
            ),
            onClick = { }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.moon),
                        contentDescription = "Left Icon",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Mode Malam")
                }
                Switch(
                    checked = isNightModeEnabled,
                    onCheckedChange = { isChecked ->
                        isNightModeEnabled = isChecked
                        // Lakukan aksi tambahan jika diperlukan
                    }
                )
            }
        }
        Button(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.primary),
                contentColor = Color.White
            ),
            onClick = { }
        ) {
            Row(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.global),
                        contentDescription = "Left Icon",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Bahasa")
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        modifier = Modifier
                            .padding(end = 8.dp),
                        text = "Indonesia"
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.bahasa_icon),
                        contentDescription = "Right Icon",
                    )
                }
            }
        }

        Spacer(
            modifier = Modifier
                .padding(top = 28.dp, bottom = 28.dp)
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.LightGray)
        )

        Button(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.primary),
                contentColor = Color.White
            ),
            onClick = { }
        ) {
            Row(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.security_safe),
                        contentDescription = "Left Icon",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Privasi dan Keamanan")
                }
                Icon(
                    painter = painterResource(id = R.drawable.arrow_right),
                    contentDescription = "Right Icon",
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        Button(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.primary),
                contentColor = Color.White
            ),
            onClick = { }
        ) {
            Row(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.key),
                        contentDescription = "Left Icon",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Ubah Sandi")
                }
                Icon(
                    painter = painterResource(id = R.drawable.arrow_right),
                    contentDescription = "Right Icon",
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        Button(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.primary),
                contentColor = Color.White
            ),
            onClick = { }
        ) {
            Row(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.profile_delete),
                        contentDescription = "Left Icon",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Hapus Akun")
                }
            }
        }

        Button(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.primary),
                contentColor = Color.White
            ),
            onClick = { }
        ) {
            Row(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.logout),
                        contentDescription = "Left Icon",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Keluar")
                }
            }
        }

        Text(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth(),
            text = "v.2.2.10",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleSmall
        )


    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    MaterialTheme {
        ProfileScreen(navController = rememberNavController())
    }
}
