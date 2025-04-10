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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.xplorer.ui.theme.Typography

@Composable
fun CountryCard(name: String) {
    val (currentWidth, currentHeight) = getScreenSize()
    Box(
        modifier = Modifier
            .width((currentWidth * 0.7).dp)
            .height((currentHeight * 0.7).dp)
            .clip(RoundedCornerShape(16.dp))
            .shadow(8.dp, RoundedCornerShape(16.dp))
            .animateContentSize()
        // fillMaxSize eliminado para respetar el tamaño calculado
    ) {
        println((currentHeight * 0.7))
        println(currentWidth * 0.7)

        // Falta implementar busqueda de la imagen dentro de Unsplashed api.
        //
        // AsyncImage...
        //
        // o
        //
        // ImagePainter (
        //     painter = painterResource(id = UnsplashedApi.getImageOf(name)),
        //     contentDescription = "Background image of this country card.",
        //     modifier = Modifier.fillMaxSize(),
        //     contentScale = ContentScale.Crop
        // )

        // Fondo gris por ahora para representar imagen
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray) // cambiar por imagen más adelante
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomStart)
                .background(Color.Black.copy(alpha = 0.5f))
                .padding(8.dp) // padding exterior
        ) {
            Column {
                Text(
                    text = name,
                    color = Color.White,
                    style = Typography.bodyMedium,
                    modifier = Modifier
                        .background(
                            Color.Black.copy(alpha = 0.5f),
                            RoundedCornerShape(8.dp)
                        )
                        .padding(8.dp)
                )

                // Definir bien colores y también después el fontSize para poder tener
                // la aplicación pareja.
                Text(
                    text = "little description",
                    color = Color.White,
                    style = Typography.bodySmall
                )

                // Necesito hacer que en este botón tenga un texto y que navegue
                // por a la página de ese país.
                //
                // Button(onClick = { navigation }) {
                // }
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
    // Aquí llamas a tu Composable con datos de ejemplo para el preview
    Box(
        modifier = Modifier
            .width(300.dp) // Define un ancho fijo para el preview
            .height(400.dp) // Define un alto fijo para el preview
            .background(Color.White) // Opcional: un fondo para el contenedor del preview
            .padding(16.dp) // Opcional: un poco de padding alrededor de la tarjeta
    ) {
        CountryCard(name = "Argentina")
    }
}
