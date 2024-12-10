package com.example.resikelapp.ui.screens.ubahsandi

import android.widget.Toast
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
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.resikelapp.R
import com.example.resikelapp.ui.screens.profile.CustomDeleteAccountDialog

@Composable
fun UbahSandi(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    // State untuk kata sandi baru dan konfirmasi kata sandi
    val (kataSandiBaru, setKataSandiBaru) = remember { mutableStateOf("") }
    val (konfirmasiSandi, setKonfirmasiSandi) = remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isConfirmPasswordVisible by remember { mutableStateOf(false) }

    // Scrollable state untuk layar

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
                        .align(Alignment.Center) // Menyusun kolom di tengah layar
                        .padding(horizontal = 35.dp)
                ) {
                    // Input Kata Sandi Baru
                    Text("Kata Sandi Baru", color = colorResource(R.color.primary), fontWeight = FontWeight.Medium)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .background(Color.Transparent),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        BasicTextField(
                            value = kataSandiBaru,
                            onValueChange = setKataSandiBaru,
                            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            modifier = Modifier
                                .weight(1f), // TextField akan mengisi ruang yang tersisa
                            singleLine = true,
                            textStyle = TextStyle(fontSize = 18.sp)
                        )

                        IconButton(
                            onClick = { isPasswordVisible = !isPasswordVisible }
                        ) {
                            Icon(
                                imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = if (isPasswordVisible) "Hide Password" else "Show Password",
                                tint = colorResource(R.color.primary)
                            )
                        }
                    }
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(2.dp)
                            .background(color = colorResource(R.color.primary))
                    )

                    Text("Konfirmasi Sandi", color = colorResource(R.color.primary), fontWeight = FontWeight.Medium, modifier = Modifier.padding(top = 18.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .background(Color.Transparent),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        BasicTextField(
                            value = konfirmasiSandi,
                            onValueChange = setKonfirmasiSandi,
                            visualTransformation = if (isConfirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            modifier = Modifier
                                .weight(1f), // TextField akan mengisi ruang yang tersisa
                            singleLine = true,
                            textStyle = TextStyle(fontSize = 18.sp),

                        )

                        IconButton(
                            onClick = { isConfirmPasswordVisible = !isConfirmPasswordVisible }
                        ) {
                            Icon(
                                imageVector = if (isConfirmPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = if (isConfirmPasswordVisible) "Hide Password" else "Show Password",
                                tint = colorResource(R.color.primary)

                            )
                        }
                    }
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(2.dp)
                            .background(colorResource(R.color.primary))
                    )

                    // Tombol Simpan
                    CustomDialogSimpan("Simpan")
                }
            }
        }
    )
}

@Composable
fun CustomDialogSimpan(text: String) {
    var showDialog by remember { mutableStateOf(false) }

    Button(
        modifier = Modifier
            .padding(top = 18.dp)
            .fillMaxWidth(),
        onClick = {
            showDialog = true
        },
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.White,
            containerColor = colorResource(R.color.primary)
        ),
    ) {
        Text(text = "Simpan", fontSize = 16.sp)
    }

    // Dialog when `showDialog` is true
    if (showDialog) {
        CustomDeleteAccountDialog(
            text = "Simpan?",
            drawable = R.drawable.icon_dialog_simpan,
            onDismiss = { showDialog = false },
            onConfirm = {
                // Handle the account deletion logic here
                showDialog = false
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UbahSandiPreview() {
    MaterialTheme {
        UbahSandi(navController = rememberNavController())
    }
}
