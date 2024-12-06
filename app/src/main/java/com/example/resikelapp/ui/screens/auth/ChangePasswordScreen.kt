package com.example.resikelapp.ui.screens.auth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.resikelapp.R
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangePasswordScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()

    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Title
        Text(
            text = "Ganti Password",
            style = TextStyle(
                fontSize = 32.sp,
                fontFamily = FontFamily(Font(R.font.roboto_bold)),
                fontWeight = FontWeight.Bold,
                color = Color(35 / 255f, 106 / 255f, 76 / 255f, 1f)
            ),
            modifier = Modifier.padding(bottom = 40.dp)
        )

        // New Password Field
        Text(
            text = "Password Baru",
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.roboto_regular)),
                color = Color(35 / 255f, 106 / 255f, 76 / 255f, 1f),
                fontWeight = FontWeight.Medium
            ),
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 8.dp, bottom = 4.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(color = Color(0xFF3C8161), shape = RoundedCornerShape(size = 10.dp))
        ) {
            OutlinedTextField(
                value = newPassword,
                onValueChange = { newPassword = it },
                placeholder = { Text("Masukkan Password Baru", color = Color.White) },
                singleLine = true,
                modifier = Modifier.fillMaxSize(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent,
                    cursorColor = Color.White,
                    focusedTextColor = Color.White
                )
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Confirm Password Field
        Text(
            text = "Konfirmasi Password Baru",
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.roboto_regular)),
                color = Color(35 / 255f, 106 / 255f, 76 / 255f, 1f),
                fontWeight = FontWeight.Medium
            ),
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 8.dp, bottom = 4.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(color = Color(0xFF3C8161), shape = RoundedCornerShape(size = 10.dp))
        ) {
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                placeholder = { Text("Masukkan Konfirmasi Password", color = Color.White) },
                singleLine = true,
                modifier = Modifier.fillMaxSize(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent,
                    cursorColor = Color.White,
                    focusedTextColor = Color.White
                )
            )
        }

        // Error Message Display
        errorMessage?.let {
            Text(
                text = it,
                color = Color.Red,
                style = TextStyle(fontSize = 12.sp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Change Password Button
        Button(
            onClick = {
                if (newPassword.isNotEmpty() && confirmPassword.isNotEmpty()) {
                    if (newPassword == confirmPassword) {
                        if (newPassword.length >= 6) {
                            isLoading = true
                            val user = auth.currentUser
                            user?.updatePassword(newPassword)?.addOnCompleteListener { task ->
                                isLoading = false
                                if (task.isSuccessful) {
                                    Toast.makeText(context, "Password berhasil diubah!", Toast.LENGTH_SHORT).show()
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Gagal mengubah password: ${task.exception?.message}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        } else {
                            errorMessage = "Password harus terdiri dari minimal 6 karakter"
                        }
                    } else {
                        errorMessage = "Password tidak cocok"
                    }
                } else {
                    errorMessage = "Mohon isi semua field"
                }
            },
            modifier = Modifier
                .width(240.dp)
                .height(40.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3C8161))
        ) {
            if (isLoading) {
                CircularProgressIndicator(color = Color.White, strokeWidth = 2.dp)
            } else {
                Text(text = "Ganti Password", color = Color.White)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewChangePasswordScreen() {
    ChangePasswordScreen()
}
