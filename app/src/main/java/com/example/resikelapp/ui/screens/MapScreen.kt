package com.example.resikelapp.ui.screens

import android.Manifest
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MapScreen() {
    val context = LocalContext.current

    val locationPermissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    LaunchedEffect(Unit) {
        locationPermissionState.launchMultiplePermissionRequest()
    }

    if (locationPermissionState.allPermissionsGranted) {
        MapContent()
    } else {
        Box(Modifier.fillMaxSize()) {
            CircularProgressIndicator()
        }
    }
}

@SuppressLint("MissingPermission")
@Composable
fun MapContent() {
    val centerLocation = LatLng(1.1108, 104.0485) // Lokasi pusat peta (Contoh: Batam)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(centerLocation, 14f)
    }

    val locations = listOf(
        LatLng(1.1128, 104.0456) to "Infinite Frameworks Studios",
        LatLng(1.1098, 104.0478) to "Nongsa Digital Park",
        LatLng(1.1118, 104.0499) to "Pos Terpadu Nongsa Resort"
    )

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        locations.forEach { (position, title) ->
            Marker(
                state = MarkerState(position = position),
                title = title
            )
        }
    }
}
