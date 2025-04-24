package com.example.xplorer.components

import CountryCard
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.util.lerp
import com.example.xplorer.api.unsplash.UnsplashImage
import com.example.xplorer.api.unsplash.UnsplashUrls
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue
//
//
//val cardMapper = mapOf<String, @Composable (List<Any?>) -> Unit> (
//    "country" to {arg -> CountryCard(arg[0].toString()) },
//
//)
//
//@Composable
//fun MainHorizontalCarrusel (pagerState : PagerState, cardType : String) {
//    Box (
//        modifier = Modifier
//                    .width(100.dp)
//                    .height(100.dp)
//    ) {
//        HorizontalPager(
//            state = pagerState,
//            pageSize =  PageSize.Fixed(3.dp),
//        ) { page ->
//            cardMapper.get(cardType)
//
//        }
//    }
//
//}

@Composable
fun CountryCarousel(imageMap : Map<String, UnsplashImage>) {
    val listState = rememberLazyListState()
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val itemSpacing = 16.dp
    val itemWidth = screenWidth * 0.7f // Mismo ancho que tu tarjeta

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
        modifier = Modifier
            .fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = (screenWidth - itemWidth) / 2),
        horizontalArrangement = Arrangement.spacedBy(itemSpacing)
    ) {
        itemsIndexed(imageMap.toList()) { index, (countryName, image) ->
            val scale = calculateCardScale(index, listState)
            Box(
                modifier = Modifier
                    .graphicsLayer(
                        scaleX = scale,
                        scaleY = scale
                    )
            ) {
                CountryCard(name = countryName, image = image.urls.raw)
            }
        }
    }
}

@Composable
fun calculateCardScale(
    index: Int,
    listState: LazyListState,
): Float {
    val center = listState.layoutInfo.viewportEndOffset / 2
    val itemInfo = listState.layoutInfo.visibleItemsInfo.find { it.index == index }

    return if (itemInfo != null) {
        val itemCenter = (itemInfo.offset + itemInfo.size / 2)
        val distanceFromCenter = (itemCenter - center).absoluteValue

        val normDist = distanceFromCenter / center.toFloat()
        lerp(1f, 0.85f, normDist.coerceIn(0f, 1f)) // escala entre 1 y 0.85
    } else {
        0.85f
    }
}

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
            .padding(vertical = 16.dp)
    ) {
        CountryCarousel(countryMap)
    }
}