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
import com.example.xplorer.R
import com.example.xplorer.components.CountryCarousel
import com.example.xplorer.components.ExpandableSearchBar
import com.example.xplorer.ui.theme.Greyscale500
import com.example.xplorer.ui.theme.MediumPadding
import com.example.xplorer.viewModels.XplorerViewModel

@Composable
fun HomePage(viewModel: XplorerViewModel) {
    val worldBankData by viewModel.countryList.collectAsState(emptyList())
    val imageMap by viewModel.imageMap.collectAsState(initial = emptyMap())

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        ExpandableSearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(MediumPadding)),
            items = worldBankData,
            onItemSelected = {
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
