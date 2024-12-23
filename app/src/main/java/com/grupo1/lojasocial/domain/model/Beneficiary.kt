package com.grupo1.lojasocial.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.grupo1.lojasocial.domain.enums.AlertLevel
import java.sql.Timestamp


@Entity(tableName = "beneficiaries")
data class Beneficiary(
    @PrimaryKey val id: String = "",
    var name: String = "",
    var surname: String = "",
    var email: String = "",
    var phoneNumber: String = "",
    var householdNumber: String = "",
    var city: String = "",
    var nationality: String = "",
    var alertLevel: AlertLevel = AlertLevel.NONE,
    val createdAt: String = Timestamp(System.currentTimeMillis()).toString()
)

data class BeneficiaryWithNotes(
    var beneficiary: Beneficiary,
    var notes: List<String>
)