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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.example.resikelapp.R
import com.example.resikelapp.ui.screens.auth.AuthViewModel
import com.example.resikelapp.ui.screens.auth.AuthResult
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.tooling.preview.Preview
import com.example.resikelapp.utils.GoogleSignInHelper
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtpDialog(
    email: String,
    authViewModel: AuthViewModel,
    onDismiss: () -> Unit,
    onVerificationSuccess: () -> Unit
) {
    var verificationStatus by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    // Mengamati status autentikasi dan verifikasi email dari AuthViewModel
    val authResult by authViewModel.authResult.observeAsState(AuthResult.Loading)

    LaunchedEffect(authResult) {
        when (authResult) {
            is AuthResult.Loading -> {
                verificationStatus = "Memeriksa status verifikasi..."
            }
            is AuthResult.Success -> {
                verificationStatus = "Email Anda berhasil diverifikasi!"
                onVerificationSuccess()
            }
            is AuthResult.Error -> {
                verificationStatus = (authResult as AuthResult.Error).message
            }
            null -> {
                verificationStatus = "Tidak ada data autentikasi saat ini."
            }
        }
    }


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
                Box(
                    modifier = Modifier
                        .width(320.dp)
                        .background(color = Color(0xFF3C8161), shape = RoundedCornerShape(24.dp))
                        .padding(start = 40.dp, end = 40.dp, top = 34.dp, bottom = 34.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(18.dp, Alignment.Top),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Title
                        Text(
                            text = "Verifikasi Email",
                            style = TextStyle(
                                fontSize = 20.sp,
                                lineHeight = 32.sp,
                                fontFamily = FontFamily(Font(R.font.roboto_bold)),
                                fontWeight = FontWeight(350),
                                color = Color(0xFFFFFFFF),
                                textAlign = TextAlign.Center
                            )
                        )

                        // Menampilkan loading indicator atau status verifikasi
                        if (authResult is AuthResult.Loading) {
                            CircularProgressIndicator(color = Color.White)
                        } else {
                            Text(
                                text = verificationStatus ?: "",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontFamily = FontFamily(Font(R.font.roboto_regular)),
                                    color = Color.White,
                                    textAlign = TextAlign.Center
                                )
                            )
                        }

                        // Instruction
                        Text(
                            text = "Kami sudah mengirimkan link verifikasi ke email Anda. Klik link tersebut untuk memverifikasi email.",
                            style = TextStyle(
                                fontSize = 11.sp,
                                lineHeight = 16.sp,
                                fontFamily = FontFamily(Font(R.font.roboto_regular)),
                                fontWeight = FontWeight(500),
                                color = Color(0xFFFFFFFF),
                                textAlign = TextAlign.Center,
                                letterSpacing = 0.5.sp
                            )
                        )
                    }
                }

                // Lock Icon
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