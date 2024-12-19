package com.grupo1.lojasocial.domain.model

import com.google.firebase.Timestamp

data class Session(
    val id: String = "",
    val id_beneficiary: String = "",
    val id_volunteer: String = "",
    val enterTime: Timestamp? = null,
    val exitTime: Timestamp? = null,
)