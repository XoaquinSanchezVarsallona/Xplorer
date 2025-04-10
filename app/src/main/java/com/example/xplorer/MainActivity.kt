package com.example.xplorer

import XplorerBottomBar
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.xplorer.navigator.NavHostComposable
import com.example.xplorer.ui.theme.XplorerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            XplorerTheme {
                Scaffold(
                    bottomBar = {XplorerBottomBar (navController)},
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