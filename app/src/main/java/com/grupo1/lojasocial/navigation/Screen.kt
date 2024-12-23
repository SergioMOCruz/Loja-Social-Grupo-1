package com.grupo1.lojasocial.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    /* MAIN SCREENS */
    object Login : Screen("login", "Login", Icons.Default.Lock)
    object Home : Screen("home", "Início", Icons.Default.Home)
    object People : Screen("people", "Pessoas", Icons.Default.Person)
    object Register : Screen("register", "Registo", Icons.Default.Add)
    object Statistics : Screen("statistics", "Estatísticas", Icons.Default.Menu)
    object Settings : Screen("settings", "Definições", Icons.Default.Settings)

    /* SUB SCREENS */
    object ManageVolunteers : Screen("manage_volunteers", "Gerir Voluntários", Icons.Default.Person)
    object ProfileBeneficiary : Screen("profile_beneficiary", "Perfil Beneficiário", Icons.Default.Person)
    object ProfileVolunteer : Screen("profile_volunteer", "Perfil Voluntário", Icons.Default.Person)
    object RegisterBeneficiary : Screen("register_beneficiary", "Registar Beneficiário", Icons.Default.Add)
    object RegisterVolunteer : Screen("register_volunteer", "Registar Voluntário", Icons.Default.Add)
    object RegisterSession : Screen("register_session", "Registar Sessão", Icons.Default.Add)
    object OpenSessionsList : Screen("open_sessions_list", "Sessões Abertas", Icons.Default.Menu)
    object ClosedSessionsList : Screen("closed_sessions_list", "Sessões Encerradas", Icons.Default.Menu)
}
