package com.grupo1.lojasocial.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.grupo1.lojasocial.ui.components.BottomNavigationBar


@Composable
fun AppNavHost(
) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                items = listOf(Screen.Home, Screen.People, Screen.Register, Screen.Statistics, Screen.Settings)
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {  }
            composable(Screen.People.route) {  }
            composable(Screen.Register.route) {  }
            composable(Screen.Statistics.route) {  }
            composable(Screen.Settings.route) {  }
        }
    }
}