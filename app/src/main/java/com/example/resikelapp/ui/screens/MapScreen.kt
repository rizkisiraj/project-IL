package com.example.resikelapp.ui.screens

import android.Manifest
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.resikelapp.data.model.LocationDetail
import com.example.resikelapp.data.model.LocationDetailData
import com.example.resikelapp.ui.components.LocationDetailCard
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
    // Pusat peta
    val centerLocation = LatLng(1.1108, 104.0485)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(centerLocation, 14f)
    }

    // Daftar lokasi
    val locations = listOf(
        LatLng(1.1098, 104.0478) to LocationDetailData.sampleLocation,
    )

    var selectedLocation by remember { mutableStateOf<LocationDetail?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            locations.forEach { (position, detail) ->
                Marker(
                    state = MarkerState(position = position),
                    title = detail.name,
                    onClick = {
                        selectedLocation = detail
                        true
                    }
                )
            }
        }

        selectedLocation?.let { locationDetail ->
            LocationDetailCard(
                locationDetail = locationDetail,
                onDismissRequest = { selectedLocation = null }
            )
        }
    }
}
