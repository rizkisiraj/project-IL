package com.example.resikelapp.data.model

import com.google.firebase.Timestamp

data class News(
    val id: String = "",
    val judul: String = "",
    val imageUrl: String = "",
    val content: String = "",
    val date: Timestamp = Timestamp.now()
)
