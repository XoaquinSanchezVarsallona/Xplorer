package com.example.xplorer

import BottomBar
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.xplorer.navigator.NavHostComposable
import com.example.xplorer.ui.theme.XplorerTheme
import com.example.xplorer.viewModels.XplorerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // Initialize Hilt
            val viewModel : XplorerViewModel = hiltViewModel<XplorerViewModel>()
            viewModel.initializeData(this)

            val navController = rememberNavController()

            XplorerTheme {
                Scaffold(
                    bottomBar = {BottomBar { navController.navigate(it) }},
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    NavHostComposable(innerPadding = innerPadding, navController = navController)
                }
            }
        }
    }
}

/*
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = stringResource(R.string.app_name),
        modifier = modifier
    )

}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    XplorerTheme {
        Greeting("Android")
    }
}
 */