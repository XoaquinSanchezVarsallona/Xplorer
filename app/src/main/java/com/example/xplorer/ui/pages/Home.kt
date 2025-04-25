package com.example.xplorer.ui.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.xplorer.components.CountryCarousel
import com.example.xplorer.components.ExpandableSearchBar
import com.example.xplorer.viewModels.XplorerViewModel

@Composable
fun HomePage(viewModel: XplorerViewModel) {
    val worldBankData by viewModel.countryList.collectAsState(emptyList())
    val imageMap by viewModel.imageMap.collectAsState(initial = emptyMap())

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 72.dp) // deja espacio arriba para el SearchBar expandido
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Explora Países",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            CountryCarousel(imageMap = imageMap)
            Spacer(modifier = Modifier.weight(1f))
        }

        // Este composable se dibuja encima gracias a zIndex
        ExpandableSearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .zIndex(1f),
            items = worldBankData,
            onItemSelected = { countryName ->
                // Navegar a detalles
            }
        )
    }
}

@Preview
@Composable
fun HomePagePreview() {
    val viewModel = hiltViewModel<XplorerViewModel>()
    HomePage(viewModel)
}