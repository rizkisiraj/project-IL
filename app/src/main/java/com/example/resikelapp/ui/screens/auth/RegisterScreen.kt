package com.example.resikelapp.ui.screens.auth

import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.resikelapp.R
import com.example.resikelapp.utils.GoogleSignInHelper
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.ui.platform.LocalContext
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.Timestamp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    onRegister: (String, String, String, String) -> Unit = { _, _, _, _ -> },
    onNavigateToLogin: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val firebaseAuth = FirebaseAuth.getInstance()
    val firestore = Firebase.firestore
    val googleSignInHelper = remember { GoogleSignInHelper(context as ComponentActivity, firebaseAuth) }

    googleSignInHelper.initGoogleSignInClient(webClientId = "336611640021-ap6c66q7qh30sa09cpeff3mnfab6arl7.apps.googleusercontent.com")

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
                        Toast.makeText(context, "Registrasi dengan Google berhasil!", Toast.LENGTH_SHORT).show()
                    },
                    onError = { error ->
                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                    }
                )
            },
            onError = { error ->
                Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
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

        // Judul
        Text(
            text = "Daftar",
            style = TextStyle(
                fontSize = 40.sp,
                fontFamily = FontFamily(Font(R.font.roboto_bold)),
                fontWeight = FontWeight.Bold,
                color = Color(0xFF236A4C)
            ),
            modifier = Modifier.padding(bottom = 38.dp)
        )

        // Input Nama Depan
        Text(
            text = "Nama Depan",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.roboto_regular)),
                color = Color(0xFF236A4C),
                fontWeight = FontWeight.Medium
            ),
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 8.dp, bottom = 4.dp)
        )
        InputField(label = "Nama Depan", value = firstName, onValueChange = { firstName = it })
        Spacer(modifier = Modifier.height(18.dp))

        // Input Nama Belakang
        Text(
            text = "Nama Belakang",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.roboto_regular)),
                color = Color(0xFF236A4C),
                fontWeight = FontWeight.Medium
            ),
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 8.dp, bottom = 4.dp)
        )
        InputField(label = "Nama Belakang", value = lastName, onValueChange = { lastName = it })
        Spacer(modifier = Modifier.height(18.dp))

        // Input Nomor Telepon
        Text(
            text = "Nomor Telepon",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.roboto_regular)),
                color = Color(0xFF236A4C),
                fontWeight = FontWeight.Medium
            ),
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 8.dp, bottom = 4.dp)
        )
        InputField(label = "Nomor Telepon", value = phoneNumber, onValueChange = { phoneNumber = it })
        Spacer(modifier = Modifier.height(18.dp))

        // Input Alamat Email
        Text(
            text = "Alamat Email",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.roboto_regular)),
                color = Color(0xFF236A4C),
                fontWeight = FontWeight.Medium
            ),
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 8.dp, bottom = 4.dp)
        )
        InputField(label = "Alamat Email", value = email, onValueChange = { email = it })
        Spacer(modifier = Modifier.height(16.dp))

        // Input Password
        Text(
            text = "Password",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.roboto_regular)),
                color = Color(0xFF236A4C),
                fontWeight = FontWeight.Medium
            ),
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 8.dp, bottom = 4.dp)
        )
        Box(
            modifier = Modifier
                .width(380.dp)
                .height(56.dp)
                .background(color = Color(0xFF236A4C), shape = RoundedCornerShape(size = 14.dp))
                .padding(horizontal = 12.dp)
        ) {
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text("Masukkan Password", color = Color.White, style = TextStyle(fontSize = 16.sp)) },
                singleLine = true,
                trailingIcon = {
                    val image = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, contentDescription = "Toggle password visibility", tint = Color.White)
                    }
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxSize(),
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

        // Tombol Daftar
        Button(
            onClick = {
                if (firstName.isNotEmpty() && lastName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val currentUser = firebaseAuth.currentUser
                                val fullName = "$firstName $lastName"

                                // Data untuk Firestore
                                val userData = mapOf(
                                    "name" to fullName,
                                    "email" to email,
                                    "phoneNumber" to phoneNumber,
                                    "fotoProfil" to "https://static.vecteezy.com/system/resources/previews/020/765/399/non_2x/default-profile-account-unknown-icon-black-silhouette-free-vector.jpg",
                                    "listKomunitas" to listOf<String>(),
                                    "role" to "User",
                                    "tanggalRegistrasi" to Timestamp.now(),
                                    "isActive" to true
                                )

                                // Menyimpan ke Firestore
                                currentUser?.let { user ->
                                    firestore.collection("users")
                                        .document(user.uid)
                                        .set(userData)
                                        .addOnSuccessListener {
                                            Toast.makeText(context, "Registrasi berhasil!", Toast.LENGTH_SHORT).show()
                                            onNavigateToLogin()
                                        }
                                        .addOnFailureListener { e ->
                                            Toast.makeText(context, "Gagal menyimpan data: ${e.message}", Toast.LENGTH_SHORT).show()
                                        }
                                }
                            } else {
                                Toast.makeText(context, "Registrasi gagal: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                } else {
                    Toast.makeText(context, "Semua field harus diisi!", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .width(240.dp)
                .height(40.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF236A4C))
        ) {
            Text(
                text = "Daftar",
                color = Color.White,
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Link ke halaman Login
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Sudah Punya Akun? ",
                fontSize = 12.sp,
                color = Color(0xFF236A4C),
            )
            Text(
                text = "Masuk",
                fontSize = 12.sp,
                color = Color(0xFF236A4C),
                textDecoration = androidx.compose.ui.text.style.TextDecoration.Underline,
                modifier = Modifier.clickable { onNavigateToLogin() }
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

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

        Spacer(modifier = Modifier.height(10.dp))

        // Tombol Masuk dengan Google
        Button(
            onClick = {
                val signInIntent = googleSignInHelper.signInWithGoogle()
                signInIntent?.let {
                    googleSignInLauncher.launch(it)
                } ?: run {
                    Toast.makeText(context, "Gagal mendapatkan intent untuk Google SignIn", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .width(240.dp)
                .height(40.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF236A4C))
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_google),
                    contentDescription = "Google Icon",
                    modifier = Modifier.size(20.dp),
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Masuk dengan Google", color = Color.White)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()

            .background(color = Color(0xFF236A4C), shape = RoundedCornerShape(size = 14.dp)),
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(label, color = Color.White, style = TextStyle(fontSize = 16.sp)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
                .height(56.dp),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                cursorColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedTextColor = Color.White
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    RegisterScreen()
}
