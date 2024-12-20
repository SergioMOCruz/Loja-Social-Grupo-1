package com.grupo1.lojasocial.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp

@Entity(tableName = "beneficiaries")
data class Beneficiary(
    @PrimaryKey val id: String = "",
    val name: String = "",
    val email: String = "",
    val phone_number: String = "",
    val household_number: String = "",
    val city: String = "",
    val nationality: String = "",
    val createdAt: String = Timestamp(System.currentTimeMillis()).toString()
)
