package com.example.resikelapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.example.resikelapp.R
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtpDialog(
    onDismiss: () -> Unit,
    onSubmitOtp: (String) -> Unit
) {
    var otpCode by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnClickOutside = false),
        containerColor = Color.Transparent,
        title = {},
        text = {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                // Container utama
                Box(
                    modifier = Modifier
                        .width(320.dp)
                        .height(240.dp)
                        .background(color = Color(0xFF3C8161), shape = RoundedCornerShape(24.dp))
                        .padding(start = 40.dp, end = 40.dp, top = 34.dp ,bottom = 34.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(18.dp, Alignment.Top),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        // Title Text
                        Text(
                            text = "Masukkan Kode OTP",
                            style = TextStyle(
                                fontSize = 20.sp,
                                lineHeight = 32.sp,
                                fontFamily = FontFamily(Font(R.font.roboto_bold)),
                                fontWeight = FontWeight(350),
                                color = Color(0xFFFFFFFF),
                                textAlign = TextAlign.Center,
                            ),
                            textAlign = TextAlign.Center
                        )

                        // OTP Input Field
                        OutlinedTextField(
                            value = otpCode,
                            onValueChange = { otpCode = it.filter { char -> char.isDigit() } },
                            placeholder = {
                                Text(
                                    text = "     xxxx - xxxx",
                                    textAlign = TextAlign.Center,
                                    style = TextStyle(
                                        fontSize = 22.sp,
                                        lineHeight = 28.sp,
                                        fontFamily = FontFamily(Font(R.font.roboto_regular)),
                                        fontWeight = FontWeight(200),
                                        color = Color(0xFF000000),
                                    )
                                )
                            },
                            textStyle = TextStyle(
                                color = Color(0xFF3C8161),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            ),
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White, RoundedCornerShape(8.dp)),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = Color.Transparent,
                                unfocusedBorderColor = Color.Transparent,
                                cursorColor = Color(0xFF3C8161),
                                containerColor = Color.White
                            ),
                            visualTransformation = VisualTransformation.None
                        )

                        // Instruction Text
                        Text(
                            text = "Kami sudah mengirimkan kode OTP, silahkan periksa Email Anda",
                            style = TextStyle(
                                fontSize = 11.sp,
                                lineHeight = 16.sp,
                                fontFamily = FontFamily(Font(R.font.roboto_regular)),
                                fontWeight = FontWeight(500),
                                color = Color(0xFFFFFFFF),
                                textAlign = TextAlign.Center,
                                letterSpacing = 0.5.sp,
                            )
                        )
                    }
                }

                // ikon Lock
                Image(
                    painter = painterResource(id = R.drawable.ic_circle_lock),
                    contentDescription = "Lock Icon with Circle Background",
                    modifier = Modifier
                        .size(70.dp)
                        .align(Alignment.Center)
                        .offset(y = (-120).dp)
                )
            }
        },
        confirmButton = {},
        dismissButton = {}
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewOtpDialog() {
    OtpDialog(
        onDismiss = { /* Handle dismiss action */ },
        onSubmitOtp = { otp -> /* Handle OTP submission */ }
    )
}
