package com.grupo1.lojasocial.ui.screens.volunteers

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.grupo1.lojasocial.navigation.Screen
import com.grupo1.lojasocial.ui.components.utils.search.SearchBar
import com.grupo1.lojasocial.ui.screens.header.SubHeaderScreen
import com.grupo1.lojasocial.viewmodel.SearchViewModel

@Composable
fun ManageVolunteersScreen(
    navController: NavController,
    searchViewModel: SearchViewModel
) {
    var searchQuery by remember { mutableStateOf("") }
    val searchResults by searchViewModel.searchResultsUsers.collectAsState()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    LaunchedEffect(searchQuery) {
        if (searchQuery.length >= 3) {
            searchViewModel.searchUsers(searchQuery)
        } else {
            searchViewModel.getAllUsers()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SubHeaderScreen(
                title = "Definições",
                subtitle = "Gerir Voluntários",
                navController
            )

            SearchBar(
                query = searchQuery,
                onQueryChange = { searchQuery = it },
                onCancelClick = { searchQuery = "" }
            )

            when {
                searchResults?.isEmpty() != true -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        searchResults?.forEach { user ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp)
                                    .background(Color(0x1FA3A3A3), shape = RoundedCornerShape(8.dp))
                                    .padding(horizontal = 16.dp)
                                    .clickable{
                                        navController.navigate("${Screen.ProfileVolunteer.route}/${user.id}")
                                    },
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "${user.name} ${user.surname}",
                                    fontSize = 14.sp,
                                    color = Color.Black
                                )
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowRight,
                                    contentDescription = "Remove",
                                    tint = Color.Gray
                                )
                            }
                        }
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = {
                if (currentRoute === Screen.ManageVolunteers.route) {
                    navController.navigate(Screen.RegisterVolunteer.route)
                }
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = Color.White,
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add",
                tint = Color.Black
            )
        }
    }
}