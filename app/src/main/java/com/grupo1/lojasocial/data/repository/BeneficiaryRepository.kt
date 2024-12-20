package com.grupo1.lojasocial.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class BeneficiaryRepository {
    private val db = FirebaseFirestore.getInstance()
    private val beneficiariesCollection = db.collection("beneficiaries")

    suspend fun registerBeneficiary(beneficiary: Map<String, Any>): Boolean {
        return try {
            beneficiariesCollection
                .add(beneficiary)
                .await()
            true
        } catch (e: Exception) {
            println("Error registering beneficiary: ${e.message}")
            false
        }
    }
}
