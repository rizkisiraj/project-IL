package com.example.resikelapp.data.model

data class Community (
    val id: Long,
    val nama: String,
    val description: String,
    val jenisSampah: List<String>,
    var gabungStatus: Boolean=false
)