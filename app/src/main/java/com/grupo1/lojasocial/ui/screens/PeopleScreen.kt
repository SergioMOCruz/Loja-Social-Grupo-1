package com.grupo1.lojasocial.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.grupo1.lojasocial.navigation.Screen
import com.grupo1.lojasocial.ui.components.utils.search.ProfileList
import com.grupo1.lojasocial.ui.components.utils.search.SearchBar
import com.grupo1.lojasocial.viewmodel.LocalHistoryViewModel
import com.grupo1.lojasocial.viewmodel.SearchViewModel

@Composable
fun PeopleScreen(
    navController: NavController,
    searchViewModel: SearchViewModel,
    localHistoryViewModel: LocalHistoryViewModel
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    var searchQuery by remember { mutableStateOf("") }
    val searchResults by searchViewModel.searchResultsBeneficiaries.collectAsState()
    val recentSearches by localHistoryViewModel.recentSearches.collectAsState()

    LaunchedEffect(searchQuery) {
        if (searchQuery.length >= 3) {
            searchViewModel.searchBeneficiaries(searchQuery)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            SearchBar(
                query = searchQuery,
                onQueryChange = { searchQuery = it },
                onCancelClick = { searchQuery = "" }
            )

            when {
                searchQuery.length >= 3 && searchResults?.isEmpty() == true -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                searchQuery.length >= 3 -> {
                    searchResults?.let {
                        ProfileList(
                            type = "enter_profile",
                            profiles = it,
                            onClick = { profile ->
                                localHistoryViewModel.insertBeneficiaryHistory(profile)
                                navController.navigate("${Screen.ProfileBeneficiary.route}/${profile.id}")
                            },
                            onRemoveClick = {}
                        )
                    }
                }

                searchQuery.isNotEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Digite pelo menos 3 letras para pesquisar",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Gray
                        )
                    }
                }

                recentSearches.isNotEmpty() -> {
                    ProfileList(
                        type = "recent_profile",
                        profiles = recentSearches,
                        onClick = { profile ->
                            localHistoryViewModel.insertBeneficiaryHistory(profile)
                            navController.navigate("${Screen.ProfileBeneficiary.route}/${profile.id}")
                        },
                        onRemoveClick = { searchToRemove ->
                            localHistoryViewModel.deleteBeneficiaryHistory(searchToRemove)
                        }
                    )
                }

                else -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Não foram encontrados beneficiários",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Gray
                        )
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = {
                if (currentRoute === Screen.People.route) {
                    navController.navigate(Screen.RegisterBeneficiary.route)
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