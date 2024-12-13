package com.example.resikelapp.ui.screens.profile

import android.graphics.drawable.Drawable
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.*
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.resikelapp.data.model.Screen
import com.example.resikelapp.utils.ViewModelFactory
import com.example.resikelapp.utils.formatToDateLocal
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase


@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = viewModel(factory = ViewModelFactory(
        repository = null
    ))
    ) {
    var isNightModeEnabled by remember { mutableStateOf(false) }

    val userFirebase = FirebaseAuth.getInstance().currentUser
    val user by viewModel.user.collectAsState()
    val jadwalPenjemputan by viewModel.jadwalPenjemputan.collectAsState()
    val total by viewModel.totalObject.collectAsState()

    LaunchedEffect(Unit) {
        if(userFirebase != null) {
            viewModel.getUserData(userFirebase.uid)
            viewModel.getTanggalPenjemputanTerdekat()
            viewModel.fetchDataAndSum(userFirebase.uid)
        }
    }

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
            AsyncImage(
                model = user.fotoProfil,
                placeholder = painterResource(R.drawable.profile),
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
                Text(text = user.name, fontSize = 25.sp)
                Text(text = user.email)
            }

            IconButton(
                onClick = {
                    navController.navigate(Screen.UbahAkun.route!!)
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.edit), // Replace with your edit icon resource
                    contentDescription = "Edit Profile",
                    modifier = Modifier.size(35.dp),
                    tint = Color.Unspecified,
                )
            }


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
                            text = jadwalPenjemputan?.let { formatToDateLocal(it.tanggal) } ?: "No Date",
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
                            text = "${total["totalPoints"]}",
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
                            text = "${total["totalKilos"]} Kg",
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
                navController.navigate(Screen.Disimpan.route!!)
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
                        modifier = Modifier
                            .size(8.dp),
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
            onClick = {
                navController.navigate(Screen.Privacy.route!!)
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
            onClick = {
                navController.navigate(Screen.UbahSandi.route!!)
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

        CustomDialog("Hapus Akun", R.drawable.profile_delete)
        CustomDialogLogout("Logout", R.drawable.logout)

        Text(
            modifier = Modifier
                .padding(top=8.dp, bottom = 100.dp)
                .fillMaxWidth(),
            text = "v.2.2.10",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleSmall
        )


    }
}

@Composable
fun CustomDialog(text: String, drawable: Int) {
    var showDialog by remember { mutableStateOf(false) }

    Button(
        modifier = Modifier
            .padding(top = 8.dp)
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(id = R.color.primary),
            contentColor = Color.White
        ),
        onClick = { showDialog = true }
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
                    painter = painterResource(id = drawable),
                    contentDescription = "Left Icon",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text)
            }
        }
    }

    // Dialog when `showDialog` is true
    if (showDialog) {
        CustomDeleteAccountDialog(
            text = "$text?",
            drawable = R.drawable.icon_dialog_delete,
            onDismiss = { showDialog = false },
            onConfirm = {
                // Handle the account deletion logic here
                showDialog = false
            }
        )
    }
}

@Composable
fun CustomDialogLogout(text: String, drawable: Int) {
    var showDialog by remember { mutableStateOf(false) }

    Button(
        modifier = Modifier
            .padding(top = 8.dp)
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(id = R.color.primary),
            contentColor = Color.White
        ),
        onClick = { showDialog = true }
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
                    painter = painterResource(id = drawable),
                    contentDescription = "Left Icon",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text)
            }
        }
    }

    // Dialog when `showDialog` is true
    if (showDialog) {
        CustomDeleteAccountDialog(
            text = "Yakin Keluar?",
            drawable =R.drawable.icon_dialog_keluar,
            onDismiss = { showDialog = false },
            onConfirm = {
                // Handle the account deletion logic here
                showDialog = false
            }
        )
    }
}

@Composable
fun CustomDeleteAccountDialog(
    text: String,
    drawable: Int,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 40.dp)
                .background(color = colorResource(R.color.primary), shape = MaterialTheme.shapes.medium)
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Icon with half-circle effect
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .align(Alignment.CenterHorizontally)
                        .offset(y = (-40).dp) // make half circle go outside the dialog
                ) {
                    Image(
                        painter = painterResource(id = drawable),
                        contentDescription = "Delete Icon",
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Text(
                    text = text,
                    color = colorResource(R.color.white),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                // Buttons "Ya" and "Tidak"
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 30.dp, start = 20.dp, end = 20.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    // Button "Ya"
                    Button(
                        modifier = Modifier
                            .width(100.dp),
                        onClick = onConfirm,
                        colors = ButtonDefaults.buttonColors(Color.White)
                    ) {
                        Text("Ya", color = colorResource(R.color.primary))
                    }

                    // Spacer
                    Spacer(modifier = Modifier.width(30.dp))

                    // Button "Tidak"
                    Button(
                        modifier = Modifier
                            .width(100.dp),
                        onClick = onDismiss,
                        colors = ButtonDefaults.buttonColors(Color.White)
                    ) {
                        Text("Tidak", color = colorResource(R.color.primary))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    MaterialTheme {
        ProfileScreen(navController = rememberNavController())
    }
}
