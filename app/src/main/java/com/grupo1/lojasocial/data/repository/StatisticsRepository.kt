package com.grupo1.lojasocial.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.grupo1.lojasocial.utils.formatDateToFirestore


class StatisticsRepository {
    private val db = FirebaseFirestore.getInstance()
    private val beneficiariesCollection = db.collection("beneficiaries")
    private val visitsCollection = db.collection("visits")
    private val schedulesCollection = db.collection("schedules")

    fun fetchBeneficiariesCount(onResult: (Int) -> Unit) {
        beneficiariesCollection
            .get()
            .addOnSuccessListener { documents ->
                onResult(documents.size())
            }
            .addOnFailureListener { exception ->
                Log.e("StatisticsRepository", "Erro ao procurar número de beneficiários", exception)
                onResult(0)
            }
    }

    fun fetchVisitsCount(onResult: (Int) -> Unit) {
        visitsCollection
            .get()
            .addOnSuccessListener { documents ->
                onResult(documents.size())
            }
            .addOnFailureListener { exception ->
                Log.e("StatisticsRepository", "Erro ao procurar número de visitas", exception)
                onResult(0)
            }
    }

    fun fetchUsersBetweenDates(
        startDateMillis: Long,
        endDateMillis: Long,
        onResult: (List<String>) -> Unit
    ) {
        val startDate =
            formatDateToFirestore(startDateMillis)
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
                    "Erro ao procurar utilizadores no intervalo de datas",
                    exception
                )
                onResult(emptyList())
            }
    }

    fun fetchBeneficiariesByHouseHoldNumber(
        houseHoldNumber: String,
        onResult: (List<String>) -> Unit
    ) {
        beneficiariesCollection
            .whereEqualTo("householdNumber", houseHoldNumber)
            .get()
            .addOnSuccessListener { documents ->
                val beneficiaries = documents.map { it.getString("name") ?: "Nome não disponível" }
                onResult(beneficiaries)
            }
            .addOnFailureListener { exception ->
                Log.e(
                    "StatisticsRepository",
                    "Erro ao procurar beneficiários por HouseHoldNumber",
                    exception
                )
                onResult(emptyList())
            }
    }
}