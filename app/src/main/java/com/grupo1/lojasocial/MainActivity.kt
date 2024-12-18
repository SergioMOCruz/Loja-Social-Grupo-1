package com.grupo1.lojasocial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.grupo1.lojasocial.navigation.AppNavHost
import com.grupo1.lojasocial.ui.theme.LojaSocialTheme
import com.grupo1.lojasocial.ui.theme.primaryBackground
import com.grupo1.lojasocial.viewmodel.AuthViewModel


class MainActivity : ComponentActivity() {
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LojaSocialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = primaryBackground
                ) {
                    AppNavHost(
                        authViewModel = authViewModel
                    )
                }
            }
        }
    }
}