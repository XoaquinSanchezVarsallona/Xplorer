package com.example.xplorer

import BottomBar
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.xplorer.navigator.NavHostComposable
import com.example.xplorer.ui.theme.BackgroundColor
import com.example.xplorer.ui.theme.XplorerTheme
import com.example.xplorer.viewModels.XplorerViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.google.firebase.FirebaseApp



@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        enableEdgeToEdge()
        setContent {
            val viewModel : XplorerViewModel = hiltViewModel<XplorerViewModel>()
            viewModel.initializeData(this)

            val navController = rememberNavController()

            XplorerTheme {
                Scaffold(
                    bottomBar = {BottomBar { navController.navigate(it) }},
                    modifier = Modifier.fillMaxSize(),
                    containerColor = BackgroundColor
                ) { innerPadding ->
                    NavHostComposable(
                        innerPadding = innerPadding,
                        navController = navController,
                        sharedViewModel = viewModel
                    )
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