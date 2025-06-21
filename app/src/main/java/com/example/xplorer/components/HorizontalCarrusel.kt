package com.example.xplorer.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.xplorer.api.unsplash.UnsplashImage
import com.example.xplorer.navigator.XplorerScreens
import com.example.xplorer.ui.theme.MediumPadding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@Composable
fun CountryCarousel(imageMap: Map<String, UnsplashImage>, navController: NavController) {
    val listState = rememberLazyListState()
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val cardWidth = screenWidth * 0.7f

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(listState.isScrollInProgress) {
        if (!listState.isScrollInProgress) {
            delay(50)

            val center = listState.layoutInfo.viewportEndOffset / 2

            val closest = listState.layoutInfo.visibleItemsInfo.minByOrNull { item ->
                val itemCenter = item.offset + item.size / 2
                (itemCenter - center).absoluteValue
            }

            closest?.let {
                coroutineScope.launch {
                    listState.animateScrollToItem(it.index)
                }
            }
        }
    }

    LazyRow(
        state = listState,
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = (screenWidth - cardWidth) / 2 - MediumPadding),
        horizontalArrangement = Arrangement.spacedBy(MediumPadding)
    ) {
        itemsIndexed(imageMap.toList()) { _, (countryName, image) ->
            Box(
                modifier = Modifier
                    .width(cardWidth)
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                CountryCard(name = countryName, image = image.urls.raw,  onclick = { name ->
                    navController.navigate(XplorerScreens.Country.withArgs(name))
                })
            }
        }
    }
}

/*
@Preview(showBackground = true)
@Composable
fun CountryCarouselPreview() {
    val countries = listOf("Argentina", "Brazil", "Chile", "Uruguay", "Mexico")
    val unsplashImage = UnsplashImage(
        id = "1",
        urls = UnsplashUrls(
            raw = "https://images.unsplash.com/photo-1567550207563-6503c9ff6995?crop=entropy\\u0026cs=srgb\\u0026fm=jpg\\u0026ixid=M3w3NDI1OTl8MHwxfHNlYXJjaHwxMHx8Q3plY2hpYXxlbnwwfDB8fHwxNzQ1NTI1Njk3fDA\\u0026ixlib=rb-4.0.3\\u0026q=85",
            regular = "",
            small = "",
        ),
        description = null
    )
    val countryMap = countries.associateWith { unsplashImage }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
            .padding(vertical = MediumPadding)
    ) {
        CountryCarousel(countryMap, navController = NavController(LocalConfiguration.current))
    }
}*/
