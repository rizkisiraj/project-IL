package com.example.resikelapp.data.model

import com.google.firebase.Timestamp

data class JadwalPenjemputan(
    val id: String = "",        // Document ID
    val tanggal: Timestamp = Timestamp.now() // Firestore Timestamp
)
