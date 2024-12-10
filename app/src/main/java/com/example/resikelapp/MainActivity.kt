package com.example.resikelapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.example.resikelapp.data.navigation.NavigationGraph
import com.example.resikelapp.ui.screens.MainScreen
import com.example.resikelapp.ui.theme.ResikelAppTheme
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        setContent {
            ResikelAppTheme {
                AppContent()
            }
        }
    }
}

@Composable
fun AppContent() {
    MainScreen()
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    ResikelAppTheme {
        AppContent()
    }
}
