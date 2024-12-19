package com.grupo1.lojasocial.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
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
import com.grupo1.lojasocial.ui.components.utils.search.ProfileList
import com.grupo1.lojasocial.ui.components.utils.search.RecentSearch
import com.grupo1.lojasocial.ui.components.utils.search.RecentSearchesList
import com.grupo1.lojasocial.ui.components.utils.search.SearchBar
import com.grupo1.lojasocial.viewmodel.SearchViewModel

@Composable
fun PeopleScreen(
    searchViewModel: SearchViewModel,
) {
    var searchQuery by remember { mutableStateOf("") }
    var recentSearches by remember { mutableStateOf(emptyList<RecentSearch>()) }

    val searchResults by searchViewModel.searchResults.collectAsState()

    LaunchedEffect(searchQuery) {
        if (searchQuery.length >= 3) {
            searchViewModel.searchBeneficiaries(searchQuery)
        }
    }

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
                        profiles = it,
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
                RecentSearchesList(
                    recentSearches = recentSearches,
                    onRemoveClick = { searchToRemove ->
                        recentSearches = recentSearches.filter { it.name != searchToRemove.name }
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
}