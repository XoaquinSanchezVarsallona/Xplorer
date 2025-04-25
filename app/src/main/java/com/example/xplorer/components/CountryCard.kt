import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.xplorer.ui.theme.Typography

@Composable
fun CountryCard(name: String, image: String) {
    val (currentWidth, currentHeight) = getScreenSize()

    Box(
        modifier = Modifier
            .width((currentWidth * 0.7).dp)
            .height((currentHeight * 0.7).dp)
            .clip(RoundedCornerShape(16.dp))
            .shadow(16.dp, RoundedCornerShape(16.dp))
            .animateContentSize()
    ) {
        // Imagen de fondo
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(image)
                .crossfade(true)
                .build(),
            contentDescription = "Background image of this country card.",
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp))
                .background(Color.LightGray),
            contentScale = ContentScale.Crop
        )

        // 🔥 Degradado oscuro en la parte baja
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

        // Texto del país y descripción
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomStart)
                .padding(12.dp)
        ) {
            Column (
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = name,
                    color = Color.White,
                    style = Typography.titleLarge

                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "little description",
                    color = Color.White.copy(alpha = 0.9f),
                    style = Typography.bodySmall
                )
            }
        }
    }
}

@Composable
fun getScreenSize(): Pair<Int, Int> {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val screenHeight = configuration.screenHeightDp
    return Pair(screenWidth, screenHeight)
}

@Preview(showBackground = true, name = "Country Card Preview")
@Composable
fun CountryCardPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        CountryCard(
            name = "Argentina",
            image = "https://images.unsplash.com/photo-1567550207563-6503c9ff6995?crop=entropy&cs=srgb&fm=jpg&ixid=M3w3NDI1OTl8MHwxfHNlYXJjaHwxMHx8Q3plY2hpYXxlbnwwfDB8fHwxNzQ1NTI1Njk3fDA&ixlib=rb-4.0.3&q=85"
        )
    }
}