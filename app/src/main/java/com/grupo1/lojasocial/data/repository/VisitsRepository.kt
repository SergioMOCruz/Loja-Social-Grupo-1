package com.grupo1.lojasocial.data.repository

import android.util.Log
import com.google.firebase.Timestamp
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

    suspend fun getVisitsByBeneficiaryId(beneficiaryId: String) : List<Visit>? {
        Log.d("VisitsRepository", "Beneficiary id: $beneficiaryId")
        return try {
            val querySnapshot = visitsCollection
                .whereEqualTo("beneficiaryId", beneficiaryId)
                .orderBy("date", Query.Direction.DESCENDING)
                .get()
                .await()

            querySnapshot.documents.mapNotNull { document ->
                val userId = document.getString("beneficiaryId") ?: return@mapNotNull null
                val date = document.getTimestamp("date") ?: return@mapNotNull null

                Visit(userId, "", date)
            }
        } catch (e: Exception) {
            println("Error fetching visits by beneficiary id: ${e.message}")
            null
        }
    }

    suspend fun regVisit(beneficiaryId: String, beneficiaryRepository: BeneficiaryRepository) {
        val beneficiary = beneficiaryRepository.getBeneficiaryByUid(beneficiaryId) ?: return

        val visit = Visit(beneficiaryId, beneficiary.name + " " + beneficiary.name, Timestamp.now())

        try {
            visitsCollection.add(visit).await()
        } catch (e: Exception) {
            println("Error registering visit: ${e.message}")
        }
    }
}
