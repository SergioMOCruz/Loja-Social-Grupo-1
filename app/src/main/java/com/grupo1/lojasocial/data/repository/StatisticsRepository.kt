package com.grupo1.lojasocial.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.grupo1.lojasocial.utils.convertLongToTimestamp
import com.grupo1.lojasocial.utils.formatDateToFirestore


class StatisticsRepository {
    private val db = FirebaseFirestore.getInstance()
    private val beneficiariesCollection = db.collection("beneficiaries")
    private val visitsCollection = db.collection("visits")
    private val schedulesCollection = db.collection("schedules")

    fun getBeneficiariesCount(onResult: (Int) -> Unit) {
        beneficiariesCollection
            .get()
            .addOnSuccessListener { documents ->
                onResult(documents.size())
            }
            .addOnFailureListener { exception ->
                Log.e("StatisticsRepository",
                    "Error when searching for number of beneficiaries",
                    exception)
                onResult(0)
            }
    }

    fun getVisitsCount(
        startDateMillis: Long,
        endDateMillis: Long,
        onResult: (Int) -> Unit
    ) {
        val startDate = convertLongToTimestamp(startDateMillis)
        val endDate = convertLongToTimestamp(endDateMillis)

        visitsCollection
            .whereGreaterThanOrEqualTo("date", startDate!!)
            .whereLessThanOrEqualTo("date", endDate!!)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val totalCount = querySnapshot.size()
                onResult(totalCount)
            }
            .addOnFailureListener { exception ->
                exception.printStackTrace()
                onResult(0)
            }
    }


    fun getUsersBetweenDates(
        startDateMillis: Long,
        endDateMillis: Long,
        onResult: (List<String>) -> Unit
    ) {
        val startDate = formatDateToFirestore(startDateMillis)
        val endDate = formatDateToFirestore(endDateMillis)

        schedulesCollection
            .whereGreaterThanOrEqualTo("date", startDate)
            .whereLessThanOrEqualTo("date", endDate)
            .get()
            .addOnSuccessListener { documents ->
                val users = mutableListOf<String>()
                for (document in documents) {
                    val usersList = document.get("users") as? List<String>
                    if (usersList != null) {
                        users.addAll(usersList)
                    }
                }
                onResult(users)
            }
            .addOnFailureListener { exception ->
                Log.e(
                    "StatisticsRepository",
                    "Error when searching for users between dates",
                    exception
                )
                onResult(emptyList())
            }
    }

    fun getDifferentNationalities(onResult: (List<String>) -> Unit) {
        beneficiariesCollection
            .get()
            .addOnSuccessListener { documents ->
                val nationalities = documents
                    .mapNotNull { it.getString("nationality") }
                    .distinct()
                onResult(nationalities)
            }
            .addOnFailureListener { exception ->
                Log.e(
                    "StatisticsRepository",
                    "Error when searching for different nationalities",
                    exception
                )
                onResult(emptyList())
            }
    }
}