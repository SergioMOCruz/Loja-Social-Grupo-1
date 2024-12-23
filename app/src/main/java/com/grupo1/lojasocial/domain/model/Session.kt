package com.grupo1.lojasocial.domain.model

import androidx.room.PrimaryKey
import com.google.firebase.Timestamp

data class Session(
    @PrimaryKey val id: String = "",
    val id_beneficiary: String = "",
    var beneficiaryName: String = "",
    val enterTime: Timestamp? = null,
    val exitTime: Timestamp? = null,
)