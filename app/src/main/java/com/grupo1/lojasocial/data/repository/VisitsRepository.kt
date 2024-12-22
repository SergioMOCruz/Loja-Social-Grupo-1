package com.grupo1.lojasocial.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.grupo1.lojasocial.domain.model.Visit
import kotlinx.coroutines.tasks.await

class VisitsRepository {
    private val db = FirebaseFirestore.getInstance()

    private val visitsCollection = db.collection("visits")

    suspend fun getLast5Visits(): List<Visit>? {
        return try {
            val querySnapshot = visitsCollection
                .orderBy("date", Query.Direction.DESCENDING)
                .limit(5)
                .get()
                .await()

            querySnapshot.documents.mapNotNull { document ->
                val userId = document.getString("beneficiaryId") ?: return@mapNotNull null
                val date = document.getTimestamp("date") ?: return@mapNotNull null

                Visit(userId, "", date)
            }
        } catch (e: Exception) {
            println("Error fetching last 5 visits: ${e.message}")
            null
        }
    }

}
