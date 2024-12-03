package com.example.resikelapp.data.model

import com.google.firebase.Timestamp

data class SampahTransaksi(
    val id: String = "",
    val idUser: String = "",
    val status: String = "pending",
    val tanggal: Timestamp = Timestamp.now(),
    val totalPoints: Int = 0
)
