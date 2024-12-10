package com.example.resikelapp.ui.screens.auth

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.resikelapp.R
import com.example.resikelapp.ui.components.OtpDialog
import com.example.resikelapp.utils.GoogleSignInHelper
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onLogin: () -> Unit,
    onNavigateToForgotPassword: (String) -> Unit,
    onNavigateToRegister: () -> Unit,
    modifier: Modifier = Modifier,
) {
    // State variables
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var showOtpDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    // Firebase and Google Sign-In setup
    val firebaseAuth = FirebaseAuth.getInstance()
    val googleSignInHelper =
        remember { GoogleSignInHelper(context as ComponentActivity, firebaseAuth) }
    val authViewModel = remember { AuthViewModel(googleSignInHelper, firebaseAuth) }
    val isLoading by authViewModel.isLoading.observeAsState(false)

    // Initialize Google Sign-In Client
    googleSignInHelper.initGoogleSignInClient(
        webClientId = context.getString(R.string.default_web_client_id)
    )
    val googleSignInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val data = result.data
        googleSignInHelper.handleSignInResult(
            data = data,
            onSuccess = { account ->
                googleSignInHelper.authenticateWithFirebase(
                    account = account,
                    onSuccess = {
                        Toast.makeText(context, "Login berhasil!", Toast.LENGTH_SHORT).show()
                        onLogin()
                    },
                    onError = { error ->
                        Toast.makeText(context, "Firebase error: $error", Toast.LENGTH_SHORT).show()
                    }
                )
            },
            onError = { error ->
                Toast.makeText(context, "Google Sign-In gagal: $error", Toast.LENGTH_SHORT).show()
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        // Title
        Text(
            text = "Masuk",
            style = TextStyle(
                fontSize = 40.sp,
                fontFamily = FontFamily(Font(R.font.roboto_bold)),
                fontWeight = FontWeight.Bold,
                color = Color(0xFF236A4C)
            ),
            modifier = Modifier.padding(bottom = 68.dp)
        )
        // Username Input
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Username",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF236A4C)
                ),
                modifier = Modifier.padding(bottom = 4.dp)
            )
            InputField(
                label = "Username",
                value = username,
                onValueChange = { username = it },
                placeholder = "Masukkan Username"
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Password Input
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Password",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF236A4C)
                ),
                modifier = Modifier.padding(bottom = 4.dp)
            )
            PasswordField(
                label = "Password",
                value = password,
                onValueChange = { password = it },
                passwordVisible = passwordVisible,
                onPasswordVisibilityChange = { passwordVisible = !passwordVisible }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Lupa Password
        Text(
            text = "Lupa Password?",
            fontSize = 12.sp,
            color = Color(0xFF236A4C),
            modifier = Modifier
                .clickable {
                    if (username.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
                        Toast.makeText(context, "Masukkan email yang valid", Toast.LENGTH_SHORT).show()
                    } else {
                        authViewModel.sendEmailVerification(
                            onSuccess = {
                                Toast.makeText(context, "Link verifikasi telah dikirim.", Toast.LENGTH_SHORT).show()
                                showOtpDialog = true
                            },
                            onError = { error ->
                                Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
                }
                .align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Login Button
        Button(
            onClick = {
                if (username.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
                    if (password.isNotEmpty()) {
                        // Login dengan Email dan Password
                        authViewModel.loginWithEmail(
                            email = username,
                            password = password,
                            onSuccess = {
                                Toast.makeText(context, "Login berhasil!", Toast.LENGTH_SHORT).show()
                                onLogin()
                            },
                            onError = { error ->
                                Toast.makeText(context, "Login gagal: $error", Toast.LENGTH_SHORT).show()
                            }
                        )
                    } else {
                        authViewModel.checkUserWithGoogleSignIn(
                            email = username,
                            onGoogleAccountFound = {
                                val signInIntent = googleSignInHelper.signInWithGoogle()
                                if (signInIntent != null) {
                                    googleSignInLauncher.launch(signInIntent)
                                } else {
                                    Toast.makeText(context, "Google Sign-In tidak dapat dimulai.", Toast.LENGTH_SHORT).show()
                                }
                            },
                            onNotGoogleAccount = {
                                Toast.makeText(context, "Harap isi password!", Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
                } else {
                    Toast.makeText(context, "Harap masukkan email yang valid", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .width(240.dp)
                .height(40.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF236A4C))
        ) {
            Text("Masuk", color = Color.White, style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold))
        }


        Spacer(modifier = Modifier.height(16.dp))

        // Register
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Belum punya akun? ", fontSize = 14.sp)
            ClickableText(
                text = AnnotatedString("Daftar"),
                onClick = { onNavigateToRegister() },
                style = TextStyle(color = Color(0xFF236A4C), fontSize = 14.sp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Google Sign-In Button
        Button(
            onClick = {
                val signInIntent = googleSignInHelper.signInWithGoogle()
                if (signInIntent != null) {
                    googleSignInLauncher.launch(signInIntent)
                } else {
                    Toast.makeText(context, "Google Sign-In tidak dapat dimulai.", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .width(240.dp)
                .height(40.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF236A4C))
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_google),
                    contentDescription = "Google Icon",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Masuk dengan Google", color = Color.White)
            }
        }
    }

    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }

    if (showOtpDialog) {
        OtpDialog(
            email = username,
            authViewModel = authViewModel,
            onDismiss = { showOtpDialog = false },
            onVerificationSuccess = {
                showOtpDialog = false
                Toast.makeText(context, "Email berhasil diverifikasi!", Toast.LENGTH_SHORT).show()
                onNavigateToForgotPassword(username)
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String
) {
    Column {
        Text(
            text = label,
            style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color.White),
            modifier = Modifier.align(Alignment.Start).padding(bottom = 4.dp)
        )
        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder, color = Color.White) },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(Color(0xFF236A4C), RoundedCornerShape(10.dp)),
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                cursorColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedTextColor = Color.White,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            )
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    passwordVisible: Boolean,
    onPasswordVisibilityChange: () -> Unit
) {
    Column {
        Text(
            text = label,
            style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color.White),
            modifier = Modifier.align(Alignment.Start).padding(bottom = 4.dp)
        )

        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text("Masukkan Password", color = Color.White) },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(Color(0xFF236A4C), RoundedCornerShape(10.dp)),
            singleLine = true,
            trailingIcon = {
                IconButton(onClick = onPasswordVisibilityChange) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = "Toggle password visibility",
                        tint = Color.White
                    )
                }
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                cursorColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedTextColor = Color.White,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            )
        )
    }
}
