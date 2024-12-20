package com.grupo1.lojasocial.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.grupo1.lojasocial.domain.model.Beneficiary
import kotlinx.coroutines.tasks.await

class SearchRepository {
    private val db = FirebaseFirestore.getInstance()
    private val searchCollection = db.collection("beneficiaries")

    suspend fun searchBeneficiaries(query: String): List<Beneficiary> {
        if (query.length >= 3) {
            return try {
                val querySnapshot = searchCollection
                    .whereEqualTo("name", query)
                    .limit(20)
                    .get()
                    .await()

                querySnapshot.documents.mapNotNull { doc ->
                    doc.toObject(Beneficiary::class.java)?.copy(id = doc.id)
                }
            } catch (e: Exception) {
                println("Error fetching user: ${e.message}")
                emptyList()
            }
        }
        return emptyList()
    }
}