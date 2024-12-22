package com.grupo1.lojasocial.domain.model

import com.google.firebase.Timestamp

data class Visit(
    val beneficiaryId: String = "",
    val name : String = "",
    val date: Timestamp? = null
)