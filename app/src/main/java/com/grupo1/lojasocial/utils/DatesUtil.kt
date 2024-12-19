package com.grupo1.lojasocial.utils

import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale

fun formatTimestampToDateAndHour(timestamp: Timestamp): String {
    val date = timestamp.toDate()
    val dateFormat = SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.getDefault())
    return dateFormat.format(date)
}