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
import com.grupo1.lojasocial.ui.components.BottomNavigationBar
import com.grupo1.lojasocial.ui.screens.HomeScreen
import com.grupo1.lojasocial.ui.screens.LoginScreen
import com.grupo1.lojasocial.ui.screens.PeopleScreen
import com.grupo1.lojasocial.ui.screens.SettingsScreen
import com.grupo1.lojasocial.ui.screens.profile.ProfileBeneficiaryScreen
import com.grupo1.lojasocial.ui.screens.register.RegisterBeneficiaryScreen
import com.grupo1.lojasocial.ui.screens.register.RegisterVolunteerScreen
import com.grupo1.lojasocial.viewmodel.AuthViewModel
import com.grupo1.lojasocial.viewmodel.BeneficiaryViewModel
import com.grupo1.lojasocial.viewmodel.LocalHistoryViewModel
import com.grupo1.lojasocial.viewmodel.SearchViewModel
import com.grupo1.lojasocial.viewmodel.UserViewModel
import com.grupo1.lojasocial.viewmodel.VisitsViewModel


@Composable
fun AppNavHost(
    authViewModel: AuthViewModel,
    userViewModel: UserViewModel,
    visitsViewModel: VisitsViewModel,
    searchViewModel: SearchViewModel,
    localHistoryViewModel: LocalHistoryViewModel,
    beneficiaryViewModel: BeneficiaryViewModel
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
            /* MAIN SCREENS */
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
                    navController = navController,
                    searchViewModel = searchViewModel,
                    localHistoryViewModel = localHistoryViewModel
                )
            }

            composable(Screen.Register.route) {
                PeopleScreen(
                    navController = navController,
                    searchViewModel = searchViewModel,
                    localHistoryViewModel = localHistoryViewModel
                )
            }

            composable(Screen.Statistics.route) {  }
            composable(Screen.Settings.route) {
                SettingsScreen(
                    navController = navController,
                    authViewModel = authViewModel,
                    userViewModel = userViewModel,
                    onLogout = { navController.navigate("login") }
                )
            }

            /* SUB SCREENS */
            composable(Screen.ProfileBeneficiary.route + "/{profileId}") {
                ProfileBeneficiaryScreen(
                    navController = navController,
                    beneficiaryViewModel = beneficiaryViewModel
                )
            }

            composable(Screen.RegisterBeneficiary.route) {
                RegisterBeneficiaryScreen(
                    navController = navController,
                    beneficiaryViewModel = beneficiaryViewModel
                )
            }

            composable(Screen.RegisterVolunteer.route) {
                RegisterVolunteerScreen(
                    navController = navController,
                    userViewModel = userViewModel
                )
            }
        }
    }
}