package com.example.resikelapp.data.model

data class Community (
    val idCommunity: String = "",
    val nama: String = "",
    val description: String = "",
    val jenisSampah: List<String> = emptyList(),
    var gabungStatus: Boolean = false
)