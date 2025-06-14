package com.example.xplorer.ui.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.xplorer.R
import com.example.xplorer.api.world_bank.Country
import com.example.xplorer.api.world_bank.WorldBankData
import com.example.xplorer.components.CountryCarousel
import com.example.xplorer.components.ExpandableSearchBar
import com.example.xplorer.navigator.XplorerScreens
import com.example.xplorer.ui.theme.Greyscale500
import com.example.xplorer.ui.theme.MediumPadding
import com.example.xplorer.viewModels.HomeViewModel

@Composable
fun HomePage(navController: NavController) {
    val viewModel = hiltViewModel<HomeViewModel>()
    val imageMap by viewModel.imageMap.collectAsState(initial = emptyMap())
    val countries by viewModel.countries.collectAsState(initial = emptyList())

    val items = countries.map {
        WorldBankData(Country(it.id, it.name), it.tourism)
    }

    if (items.isEmpty()) {
        Text(
            text = "No hay datos disponibles",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.fillMaxSize(),
            color = Greyscale500
        )
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            ExpandableSearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(MediumPadding)),
                items = items,
                onItemSelected = { id ->
                    navController.navigate(XplorerScreens.Country.withArgs(id)) {
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )

            Spacer(modifier = Modifier.height(MediumPadding))

            Text(
                text = stringResource(R.string.explore_text),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(horizontal = MediumPadding),
                color = Greyscale500
            )

            Spacer(modifier = Modifier.height(MediumPadding))

            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CountryCarousel(imageMap = imageMap)
            }

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}