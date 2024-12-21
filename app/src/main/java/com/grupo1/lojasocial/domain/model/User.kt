package com.grupo1.lojasocial.domain.model

import com.grupo1.lojasocial.domain.enums.RoleLevel

data class User(
    val id: String = "",
    val name: String = "",
    val surname: String = "",
    val email: String = "",
    val phone_number: String = "",
    val role: RoleLevel = RoleLevel.VOLUNTEER
)