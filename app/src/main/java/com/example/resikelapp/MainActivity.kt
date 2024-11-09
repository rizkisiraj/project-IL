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
import com.example.resikelapp.ui.screens.RegisterScreen
import com.example.resikelapp.ui.theme.ResikelAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        setContent {
            ResikelAppTheme {
                var showLoginScreen by remember { mutableStateOf(true) }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    if (showLoginScreen) {
                        LoginScreen(
                            onLogin = { username, password -> },
                            onNavigateToForgotPassword = { },
                            onNavigateToRegister = {
                                showLoginScreen = false
                            },
                            modifier = Modifier.padding(innerPadding)
                        )
                    } else {
                        RegisterScreen(
                            onRegister = { firstName, lastName, email, password -> },
                            onNavigateToLogin = {
                                showLoginScreen = true
                            },
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
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
