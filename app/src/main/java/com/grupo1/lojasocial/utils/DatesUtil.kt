package com.grupo1.lojasocial.utils

import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale

fun formatTimestampToDateAndHour(timestamp: Timestamp?): String {
    return if (timestamp != null) {
        val date = timestamp.toDate()
        val dateFormat = SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.getDefault())
        dateFormat.format(date)
    } else {
        "Unknown Date"
    }
}

fun formatTimestampToDate(timestamp: Timestamp?): String {
    return if (timestamp != null) {
        val date = timestamp.toDate()
        val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        dateFormat.format(date)
    } else {
        "Unknown Date"
    }
}
