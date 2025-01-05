package com.grupo1.lojasocial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.grupo1.lojasocial.navigation.AppNavHost
import com.grupo1.lojasocial.ui.theme.LojaSocialTheme
import com.grupo1.lojasocial.ui.theme.primaryBackground
import com.grupo1.lojasocial.viewmodel.AuthViewModel
import com.grupo1.lojasocial.viewmodel.BeneficiaryViewModel
import com.grupo1.lojasocial.viewmodel.LocalHistoryViewModel
import com.grupo1.lojasocial.viewmodel.SearchViewModel
import com.grupo1.lojasocial.viewmodel.UserViewModel
import com.grupo1.lojasocial.viewmodel.VisitsViewModel
import com.grupo1.lojasocial.viewmodel.SessionsViewModel
import com.grupo1.lojasocial.viewmodel.StatisticsViewModel


class MainActivity : ComponentActivity() {

    private val authViewModel: AuthViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()
    private val visitsViewModel: VisitsViewModel by viewModels()
    private val searchViewModel: SearchViewModel by viewModels()
    private val beneficiaryViewModel: BeneficiaryViewModel by viewModels()
    private val sessionsViewModel: SessionsViewModel by viewModels()
    private val statisticsViewModel: StatisticsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LojaSocialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = primaryBackground
                ) {
                    AppNavHost(
                        authViewModel = authViewModel,
                        userViewModel = userViewModel,
                        visitsViewModel = visitsViewModel,
                        searchViewModel = searchViewModel,
                        localHistoryViewModel = ViewModelProvider(this).get(LocalHistoryViewModel::class.java),
                        beneficiaryViewModel = beneficiaryViewModel,
                        sessionsViewModel = sessionsViewModel,
                        statisticsViewModel = statisticsViewModel
                    )
                }
            }
        }
    }
}