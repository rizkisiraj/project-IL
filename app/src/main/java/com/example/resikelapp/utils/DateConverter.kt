package com.example.resikelapp.utils

import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import java.text.NumberFormat
import java.util.Locale

fun formatTimestampToDate(timestamp: Timestamp?): String {
    return timestamp?.toDate()?.let { date ->
        val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()) // Format to "22 Jan 2023"
        formatter.format(date)
    } ?: "No Date" // Return "No Date" if the timestamp is null
}

fun formatToRupiah(amount: Int): String {
    // Create a NumberFormat instance for Indonesian Rupiah
    val rupiahFormat = NumberFormat.getCurrencyInstance(Locale("id", "ID"))

    return rupiahFormat.format(amount)
}

fun formatFirebaseTimestamp(timestamp: Timestamp): String {
    val date: Date = timestamp.toDate()

    val formatter = SimpleDateFormat("dd MMM yyyy - HH:mm:ss", Locale("id"))

    return formatter.format(date)
}

fun formatToDateLocal(timestamp: Timestamp): String {
    val date: Date = timestamp.toDate()

    val formatter = SimpleDateFormat("dd MMM", Locale("id"))

    return formatter.format(date)
}