package com.example.resikelapp.data.model

object CommunityData {
    val communities = listOf(
        Community(
            "1",
            "Paper Fighter",
            "Mengubah kertas bekas jadi tas belanja keren – bantu kami kurangi sampah!",
            jenisSampah = listOf("Kertas", "Daur Ulang")
        ),
        Community(
            "2",
            "Bottle Busters",
            "Komunitas pemerhati botol plastik",
            jenisSampah = listOf("Botol", "Plastik")
        ),
        Community(
            "3",
            "Kompos Jaya",
            "Bekerja sama mengolah pupuk kompos alami dari sampah rumahan",
            jenisSampah = listOf("Organik", "Pupuk")
        )
    )
}