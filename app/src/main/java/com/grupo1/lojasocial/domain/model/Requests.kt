package com.grupo1.lojasocial.domain.model

import androidx.room.PrimaryKey
import com.google.firebase.Timestamp

data class Requests(
    @PrimaryKey val id: String = "",
    val id_beneficiary: String = "",
    val date: Timestamp? = null,
    val products: List<Product> = emptyList(),
)

data class Product(
    val description: String = "",
    val quantity: Int = 0
)