package com.grupo1.lojasocial.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.grupo1.lojasocial.navigation.Screen

@Composable
fun BottomNavigationBar(
    navController: NavController,
    items: List<Screen>
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    BottomNavigation(
        modifier = Modifier.background(Color.White),
        backgroundColor = Color.White,
        contentColor = Color.Black
    ) {
        items.forEach { screen ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        imageVector = screen.icon,
                        contentDescription = screen.title,
                        tint = if (currentRoute == screen.route) Color.Black else Color.Gray,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = {
                    Text(
                        text = screen.title,
                        fontSize = 10.sp,
                        color = if (currentRoute == screen.route) Color.Black else Color.Gray,
                        fontWeight = if (currentRoute == screen.route) FontWeight.Bold else FontWeight.Normal,
                        maxLines = 1
                    )
                },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                alwaysShowLabel = true
            )
        }
    }
}
