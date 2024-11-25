package com.example.resikelapp.data.model

import com.google.firebase.firestore.GeoPoint

object LocationDetailData {
    val sampleLocation = LocationDetail(
        name = "Nongsa Waste Center",
        imageUrl = listOf(
            "https://example.com/image1.jpg",
            "https://example.com/image2.jpg",
            "https://example.com/image3.jpg"
        ),
        openHours = "08:00 - 20:00",
        address = "Digital Park, Sambau, Kecamatan Nongsa, Kota Batam, Kepulauan Riau 29466",
        location = GeoPoint(1.1098, 104.0478) // Contoh GeoPoint
    )
}
