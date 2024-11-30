package com.example.resikelapp.utils

import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.*

fun formatTimestampToDate(timestamp: Timestamp?): String {
    return timestamp?.toDate()?.let { date ->
        val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()) // Format to "22 Jan 2023"
        formatter.format(date)
    } ?: "No Date" // Return "No Date" if the timestamp is null
}