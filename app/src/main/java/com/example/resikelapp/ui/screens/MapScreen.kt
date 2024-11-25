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

    // Permissions state for location
    val locationPermissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    LaunchedEffect(Unit) {
        locationPermissionState.launchMultiplePermissionRequest()
    }

    // If permissions are granted, show map content
    if (locationPermissionState.allPermissionsGranted) {
        MapContent()
    } else {
        // Show progress indicator while waiting for permissions
        Box(Modifier.fillMaxSize()) {
            CircularProgressIndicator()
        }
    }
}

@SuppressLint("MissingPermission")
@Composable
fun MapContent() {
    // Center position of the map (default)
    val centerLocation = LatLng(1.1108, 104.0485)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(centerLocation, 14f)
    }

    // Dummy data for locations, replace with real data from Firestore
    val locations = listOf(
        LatLng(1.1098, 104.0478) to LocationDetail(
            name = "Nongsa Waste Center",
            imageUrl = listOf(
                "https://www.wmnorthwest.com/smartrecycling/gif/smart2.jpg",
                "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/2b/1b/06/43/caption.jpg?w=500&h=400&s=1"
            ),
            location = com.google.firebase.firestore.GeoPoint(1.1098, 104.0478),
            address = "Jl. Raya Nongsa, Batam",
            openHours = "08:00 - 17:00"
        )
    )

    var selectedLocation by remember { mutableStateOf<LocationDetail?>(null) }

    // Layout for the map and the selected location detail
    Box(modifier = Modifier.fillMaxSize()) {
        // Google Map with markers for each location
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            locations.forEach { (position, detail) ->
                Marker(
                    state = MarkerState(position = position),
                    title = detail.name,
                    onClick = {
                        // Set selected location on marker click
                        selectedLocation = detail
                        true
                    }
                )
            }
        }

        // Show the LocationDetailCard when a location is selected
        selectedLocation?.let { locationDetail ->
            LocationDetailCard(
                locationDetail = locationDetail,
                onDismissRequest = { selectedLocation = null }
            )
        }
    }
}
