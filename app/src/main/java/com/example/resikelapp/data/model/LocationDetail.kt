package com.example.resikelapp.data.model

import com.google.firebase.firestore.GeoPoint

data class LocationDetail(
    val name: String,
    val imageUrl: List<String>,
    val openHours: String,
    val address: String,
    val location: GeoPoint
)
