package com.grupo1.lojasocial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.grupo1.lojasocial.navigation.AppNavHost
import com.grupo1.lojasocial.ui.theme.LojaSocialTheme
import com.grupo1.lojasocial.ui.theme.primaryBackground


class MainActivity : ComponentActivity() {
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

                    )
                }
            }
        }
    }
}