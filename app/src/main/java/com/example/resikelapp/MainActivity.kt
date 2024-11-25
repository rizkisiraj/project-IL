package com.example.resikelapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.resikelapp.ui.screens.auth.LoginScreen
import com.example.resikelapp.ui.screens.MainScreen
import com.example.resikelapp.ui.screens.auth.RegisterScreen
import com.example.resikelapp.ui.theme.ResikelAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        setContent {
            ResikelAppTheme {
                var currentScreen by remember { mutableStateOf("Login") }

                when (currentScreen) {
                    "Login" -> LoginScreen(
                        onLogin = { _, _ -> currentScreen = "Main" },
                        onNavigateToForgotPassword = {},
                        onNavigateToRegister = { currentScreen = "Register" }
                    )
                    "Register" -> RegisterScreen(
                        onRegister = { _, _, _, _ -> currentScreen = "Main" },
                        onNavigateToLogin = { currentScreen = "Login" }
                    )
                    "Main" -> MainScreen()
                }
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
