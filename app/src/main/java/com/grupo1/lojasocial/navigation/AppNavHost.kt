package com.grupo1.lojasocial.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.play.integrity.internal.u
import com.grupo1.lojasocial.ui.components.BottomNavigationBar
import com.grupo1.lojasocial.ui.screens.HomeScreen
import com.grupo1.lojasocial.ui.screens.LoginScreen
import com.grupo1.lojasocial.ui.screens.PeopleScreen
import com.grupo1.lojasocial.ui.screens.SettingsScreen
import com.grupo1.lojasocial.ui.screens.sessions.SessionsListScreen
import com.grupo1.lojasocial.viewmodel.AuthViewModel
import com.grupo1.lojasocial.viewmodel.BeneficiaryViewModel
import com.grupo1.lojasocial.viewmodel.SearchViewModel
import com.grupo1.lojasocial.viewmodel.SessionsViewModel
import com.grupo1.lojasocial.viewmodel.UserViewModel
import com.grupo1.lojasocial.viewmodel.VisitsViewModel


@Composable
fun AppNavHost(
    authViewModel: AuthViewModel,
    userViewModel: UserViewModel,
    visitsViewModel: VisitsViewModel,
    searchViewModel: SearchViewModel,
    beneficiaryViewModel: BeneficiaryViewModel,
    sessionsViewModel: SessionsViewModel
) {
    val navController = rememberNavController()

    val isLoggedIn by authViewModel.isLoggedIn.collectAsState()

    val startDestination = if (isLoggedIn) "home" else "login"

    Scaffold(
        bottomBar = {
            if (isLoggedIn) {
                run {
                    BottomNavigationBar(
                        navController = navController,
                        items = listOf(
                            Screen.Home,
                            Screen.People,
                            Screen.Register,
                            Screen.Statistics,
                            Screen.Settings
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            Modifier.padding(innerPadding)
        ) {
            composable(Screen.Login.route) {
                val loginState by authViewModel.loginState.collectAsState()

                val loginError = if (loginState?.isFailure == true) {
                    loginState!!.exceptionOrNull()?.localizedMessage
                } else null

                LoginScreen(
                    onLoginClick = { email, password ->
                        authViewModel.login(email, password)
                    },
                    loginError = loginError
                )

                if (loginState?.isSuccess == true) {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                    authViewModel.resetLoginState()
                }
            }

            composable(Screen.Home.route) {
                HomeScreen(
                    userViewModel = userViewModel,
                    visitsViewModel = visitsViewModel
                )
            }

            composable(Screen.People.route) {
                PeopleScreen(
                    searchViewModel = searchViewModel,
                    beneficiaryViewModel = beneficiaryViewModel
                )
            }

            composable(Screen.Register.route) {  }
            composable(Screen.People.route) {  }
            composable(Screen.Register.route) {
                SessionsListScreen(
                    sessionsViewModel = sessionsViewModel,
                    onBackClick = { navController.navigate("home") },
                )
            }
            composable(Screen.Statistics.route) {  }
            composable(Screen.Settings.route) {
                SettingsScreen(
                    authViewModel = authViewModel,
                    onLogout = { navController.navigate("login") }
                )
            }
        }
    }
}