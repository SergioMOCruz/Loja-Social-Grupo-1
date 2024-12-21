package com.grupo1.lojasocial.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.grupo1.lojasocial.domain.enums.AlertLevel
import java.sql.Timestamp


@Entity(tableName = "beneficiaries")
data class Beneficiary(
    @PrimaryKey val id: String = "",
    var name: String = "",
    var email: String = "",
    var phone_number: String = "",
    var household_number: String = "",
    var city: String = "",
    var nationality: String = "",
    var alert_level: AlertLevel = AlertLevel.NONE,
    val createdAt: String = Timestamp(System.currentTimeMillis()).toString()
)

data class BeneficiaryWithNotes(
    var beneficiary: Beneficiary,
    var notes: List<String>
)