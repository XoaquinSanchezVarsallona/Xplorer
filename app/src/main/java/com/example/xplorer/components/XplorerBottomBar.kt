import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.xplorer.navigator.XplorerScreens
import com.example.xplorer.ui.theme.Typography
import com.example.xplorer.ui.theme.XplorerTheme
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun XplorerBottomBarPreview() {
    val navController = rememberNavController()
    XplorerBottomBar(navController = navController)
}

@Preview(showBackground = true)
@Composable
fun XplorerBottomBarPreviewLight() {
    XplorerTheme {
        XplorerBottomBarPreview()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun XplorerBottomBar(navController: NavController , modifier: Modifier = Modifier.size(width = 300.dp, height = 80.dp)) {
    // Define un ícono para cada destino (puedes personalizarlos)
    val mapper = mapOf(
        XplorerScreens.Home.name to Icons.Filled.Home,
        XplorerScreens.Country.name to Icons.Filled.LocationOn,
        XplorerScreens.Favorite.name to Icons.Filled.Favorite,
    )

    BottomAppBar (

        modifier = modifier
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxSize()
        ) {
            mapper.forEach { (name : String, icon : ImageVector ) ->
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    ){
                    IconButton(onClick = { navController.navigate(name) }) {
                        Icon(imageVector = icon, contentDescription = "$name Nav Icon")

                    }
                    Text(
                        style = Typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary,
                        text = name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
                    )
                }
            }
        }
    }
}