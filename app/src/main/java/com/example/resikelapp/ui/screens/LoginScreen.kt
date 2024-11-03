package com.example.resikelapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.resikelapp.ui.components.OtpDialog
import com.example.resikelapp.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onLogin: (String, String) -> Unit,
    onNavigateToForgotPassword: () -> Unit,
    onNavigateToRegister: () -> Unit,
    modifier: Modifier = Modifier
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var showOtpDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        // Judul
        Text(
            text = "Masuk",
            style = TextStyle(
                fontSize = 40.sp,
                fontFamily = FontFamily(Font(R.font.roboto_bold)),
                fontWeight = FontWeight.Bold,
                color = Color(35 / 255f, 106 / 255f, 76 / 255f, 1f)
            ),
            modifier = Modifier.padding(bottom = 68.dp)
        )

        // Label dan Kolom Username
        Text(
            text = "Username",
            style = MaterialTheme.typography.bodyMedium.copy(
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
                .width(380.dp)
                .height(48.dp)
                .background(color = Color(0xFF3C8161), shape = RoundedCornerShape(size = 10.dp))
        ) {
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                placeholder = { Text("Masukkan Username", color = Color.White) },
                singleLine = true,
                modifier = Modifier.fillMaxSize(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent,
                    cursorColor = Color.White,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                )
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Label dan Kolom Password
        Text(
            text = "Password",
            style = MaterialTheme.typography.bodyMedium.copy(
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
                .width(380.dp)
                .height(48.dp)
                .background(color = Color(0xFF3C8161), shape = RoundedCornerShape(size = 10.dp))
        ) {
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text("Masukkan Password", color = Color.White) },
                singleLine = true,
                trailingIcon = {
                    val image = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, contentDescription = "Toggle password visibility", tint = Color.White)
                    }
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                modifier = Modifier.fillMaxSize(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent,
                    cursorColor = Color.White,
                    focusedTextColor = Color.White,  // Setting text color
                )
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        //Tampil OTP
        if (showOtpDialog) {
            OtpDialog(
                onDismiss = { showOtpDialog = false },
                onSubmitOtp = { otpCode ->
                    showOtpDialog = false
                    // Implementasi Kode OTP
                }
            )
        }


        // Link Lupa Password
        Text(
            text = "Lupa Password?",
            fontSize = 12.sp,
            fontFamily = FontFamily(Font(R.font.roboto_regular)),
            color = Color(35 / 255f, 106 / 255f, 76 / 255f, 1f),
            modifier = Modifier
                .clickable { showOtpDialog = true }
                .align(Alignment.Start)
                .padding(end = 8.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Tombol Masuk
        Button(
            onClick = {
                if (username.isNotEmpty() && password.isNotEmpty()) {
                    onLogin(username, password)
                }
            },
            modifier = Modifier
                .width(240.dp)
                .height(40.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFF3C8161))
        ) {
            Text(
                text = "Masuk",
                color = Color.White,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )
        }

        Spacer(modifier = Modifier.height(1.dp))

        // Link Daftar
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Belum Punya Akun? ",
                fontSize = 12.sp,
                color = Color(0xFF3C8161),
            )
            ClickableText(
                text = AnnotatedString("Daftar"),
                onClick = { onNavigateToRegister() },
                style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF3C8161)),
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Atau
        Text(
            text = "Atau",

            style = TextStyle(
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.roboto_regular)),
                fontWeight = FontWeight.W400,
                color = Color(35 / 255f, 106 / 255f, 76 / 255f, 1f)
            ),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Tombol Masuk dengan Google
        Button(
            onClick = { /* Handle Google login */ },
            modifier = Modifier
                .width(240.dp)
                .height(40.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3C8161))
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_google),
                    contentDescription = "Google Icon",
                    modifier = Modifier
                        .size(30.dp)
                        .padding(end = 8.dp),
                    tint = Color.White
                )

                Text(
                    text = "Masuk dengan Google",
                    color = Color.White,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        }
        }}
