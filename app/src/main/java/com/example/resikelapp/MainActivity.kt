package com.example.resikelapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.resikelapp.ui.screens.LoginScreen
import com.example.resikelapp.ui.screens.MainScreen
import com.example.resikelapp.ui.screens.RegisterScreen
import com.example.resikelapp.ui.theme.ResikelAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            ResikelAppTheme {
                var showLoginScreen by remember { mutableStateOf(true) } // Kontrol tampilan login atau register

                MainScreen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    ResikelAppTheme {
        LoginScreen(
            onLogin = { _, _ -> },
            onNavigateToForgotPassword = {},
            onNavigateToRegister = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    ResikelAppTheme {
        RegisterScreen(
            onRegister = { _, _, _, _ -> },
            onNavigateToLogin = {}
        )
    }
}
