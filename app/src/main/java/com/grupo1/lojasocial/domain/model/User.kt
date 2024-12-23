package com.grupo1.lojasocial.domain.model

import com.grupo1.lojasocial.domain.enums.RoleLevel

data class User(
    val id: String = "",
    val name: String = "",
    val surname: String = "",
    val email: String = "",
    val password: String = "",
    val phoneNumber: String = "",
    val role: RoleLevel = RoleLevel.VOLUNTEER
)