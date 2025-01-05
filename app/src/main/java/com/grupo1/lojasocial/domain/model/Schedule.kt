package com.grupo1.lojasocial.domain.model

data class Schedule(
    val id: String,
    val date: String,
    val time: String,
    val users: List<User> = emptyList()
)