package com.example.resikelapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.resikelapp.data.navigation.NavigationGraph
import com.example.resikelapp.ui.components.BottomNav
import com.example.resikelapp.ui.theme.GreenBase

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNav(navController)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Handle FAB click */ },
                containerColor = GreenBase,
                shape = CircleShape,
                modifier = Modifier
                    .size(80.dp)
                    .offset(y = 60.dp)
            ) {
                Icon(Icons.Filled.ShoppingCart, contentDescription = "Add", tint = Color.White, modifier = Modifier.size(45.dp))
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        content = { innerPadding ->
            NavigationGraph(navController)
        }
    )
}