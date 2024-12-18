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
    object Login : Screen("login", "Login", Icons.Default.Lock)
    object Home : Screen("home", "Início", Icons.Default.Home)
    object People : Screen("people", "Pessoas", Icons.Default.Person)
    object Register : Screen("register", "Registo", Icons.Default.Add)
    object Statistics : Screen("statistics", "Estatísticas", Icons.Default.Menu)
    object Settings : Screen("settings", "Definições", Icons.Default.Settings)
}
