package com.example.xplorer.ui.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.xplorer.components.FavoriteButton
import com.example.xplorer.components.FlagLabel
import com.example.xplorer.room.entities.Country
import com.example.xplorer.ui.theme.SmallPadding
import com.example.xplorer.viewModels.FavoriteViewModel
import kotlinx.coroutines.launch

@Composable
fun Favorite () {
    val favoriteViewModel = hiltViewModel<FavoriteViewModel>()
    val favoriteList = favoriteViewModel.favorites.asFlow().collectAsStateWithLifecycle(initialValue = emptyList())

    if (favoriteList.value.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background),
            contentAlignment = androidx.compose.ui.Alignment.Center
        ) {
            Text(
                text = "No favorites added yet",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    } else {
        LazyColumn {
            items(favoriteList.value) { favoriteCountry ->
                FavoriteItem(country = favoriteCountry, viewModel = favoriteViewModel)
            }
        }
    }
}

@Composable
fun FavoriteItem(country : Country, viewModel: FavoriteViewModel) {
    val coroutineScope = rememberCoroutineScope()

    Row (
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        FlagLabel(
            modifier = Modifier.padding(SmallPadding),
            countryFlag = country.countryCodeToFlagEmoji(),
            countryName = country.name
        )
        FavoriteButton(
            isFavorite = country.isFavorite,
            onClick = {
                coroutineScope.launch {
                    val updatedCountry = country.copy(isFavorite = !country.isFavorite)
                    viewModel.updateCountry(updatedCountry)
                }
            }
        )

    }
}