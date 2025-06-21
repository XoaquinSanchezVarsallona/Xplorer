package com.example.xplorer.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.xplorer.ui.theme.MediumPadding
import com.example.xplorer.ui.theme.SmallPadding

@Composable
fun CountryCard(name: String, image: String, onclick: (name : String) -> Unit) {
    val (currentWidth, currentHeight) = getScreenSize()

    Box(
        modifier = Modifier
            .width((currentWidth * 0.7).dp)
            .height((currentHeight * 0.7).dp)
            .clip(RoundedCornerShape(MediumPadding))
            .shadow(MediumPadding, RoundedCornerShape(MediumPadding))
            .animateContentSize()
            .clickable { onclick (name) }
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(image)
                .crossfade(true)
                .build(),
            contentDescription = "Background image of this country card.",
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(MediumPadding))
                .background(Color.LightGray),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.6f)),
                        startY = (currentHeight * 0.4f)
                    )
                )
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomStart)
                .padding(MediumPadding)
        ) {
            Column (
                modifier = Modifier.padding(SmallPadding)
            ) {
                Text(
                    text = name,
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}

@Composable
fun getScreenSize(): Pair<Int, Int> {
    val size = LocalWindowInfo. current. containerSize
    val screenWidth = size.width
    val screenHeight = size.height
    return Pair(screenWidth, screenHeight)
}

@Preview(showBackground = true, name = "Country Card Preview")
@Composable
fun CountryCardPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(MediumPadding)
    ) {
        CountryCard(
            name = "Argentina",
            image = "https://images.unsplash.com/photo-1567550207563-6503c9ff6995?crop=entropy&cs=srgb&fm=jpg&ixid=M3w3NDI1OTl8MHwxfHNlYXJjaHwxMHx8Q3plY2hpYXxlbnwwfDB8fHwxNzQ1NTI1Njk3fDA&ixlib=rb-4.0.3&q=85",
            onclick = {}
        )
    }
}