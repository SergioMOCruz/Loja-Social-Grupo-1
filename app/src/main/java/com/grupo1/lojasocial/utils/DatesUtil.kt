package com.grupo1.lojasocial.utils

import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatTimestampToDateAndHour(timestamp: Timestamp?): String {
    return if (timestamp != null) {
        val date = timestamp.toDate()
        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        dateFormat.format(date)
    } else {
        "Unknown Date"
    }
}

fun formatTimestampToDate(timestamp: Timestamp?): String {
    return if (timestamp != null) {
        val date = timestamp.toDate()
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        dateFormat.format(date)
    } else {
        "Unknown Date"
    }
}

fun convertLongToTimestamp(milliseconds: Long?): Timestamp? {
    return if (milliseconds != null) {
        Timestamp(milliseconds / 1000, ((milliseconds % 1000) * 1000000).toInt())
    } else {
        null
    }
}

fun formatDateToFirestore(millis: Long): String {
    val date = Date(millis)
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return dateFormat.format(date)
}

fun formatDate(dateInMillis: Long): String {
    val date = Date(dateInMillis)
    val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return format.format(date)
}