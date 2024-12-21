package com.grupo1.lojasocial.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.grupo1.lojasocial.domain.enums.AlertLevel
import com.grupo1.lojasocial.domain.model.Beneficiary
import com.grupo1.lojasocial.domain.model.BeneficiaryWithNotes
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

    suspend fun getBeneficiaryProfile(profileId: String): BeneficiaryWithNotes {
        return try {
            val documentSnapshot = beneficiariesCollection
                .document(profileId)
                .get()
                .await()

            if (documentSnapshot.exists()) {
                val data = documentSnapshot.data
                val name = (data?.get("name") as? String) ?: ""
                val email = (data?.get("email") as? String) ?: ""
                val phone_number = (data?.get("phone_number") as? String) ?: ""
                val household_number = (data?.get("household_number") as? String) ?: ""
                val city = (data?.get("city") as? String) ?: ""
                val nationality = (data?.get("nationality") as? String) ?: ""
                val alert_level: AlertLevel = when (data?.get("alert_level") as? String) {
                    "HIGH" -> AlertLevel.HIGH
                    "MEDIUM" -> AlertLevel.MEDIUM
                    "LOW" -> AlertLevel.LOW
                    else -> AlertLevel.NONE
                }
                val notes = (data?.get("notes") as? List<String>) ?: emptyList()

                val beneficiary = Beneficiary(
                    name = name,
                    email = email,
                    phone_number = phone_number,
                    household_number = household_number,
                    city = city,
                    nationality = nationality,
                    alert_level = alert_level
                )
                BeneficiaryWithNotes(beneficiary, notes)
            } else {
                BeneficiaryWithNotes(Beneficiary(), emptyList())
            }
        } catch (e: Exception) {
            println("Error getting beneficiary profile: ${e.message}")
            BeneficiaryWithNotes(Beneficiary(), emptyList())
        }
    }

    suspend fun updateBeneficiaryProfile(profileId: String, beneficiary: Map<String, Any>): Boolean {
        return try {
            beneficiariesCollection
                .document(profileId)
                .update(beneficiary)
                .await()
            true
        } catch (e: Exception) {
            println("Error updating beneficiary profile: ${e.message}")
            false
        }
    }
}
