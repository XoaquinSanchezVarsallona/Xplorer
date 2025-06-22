package com.example.xplorer

import BottomBar
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.navigation.compose.rememberNavController
import com.example.xplorer.navigator.NavHostComposable
import com.example.xplorer.navigator.XplorerScreens
import com.example.xplorer.notifications.AppLifecycleObserver
import com.example.xplorer.notifications.notificationChannelID
import com.example.xplorer.ui.theme.BackgroundColor
import com.example.xplorer.ui.theme.XplorerTheme
import com.google.firebase.FirebaseApp
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    @Inject
    lateinit var appLifecycleObserver: AppLifecycleObserver


    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        createNotificationChannel()
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            ProcessLifecycleOwner.get().lifecycle.addObserver(appLifecycleObserver)


            XplorerTheme {
                Scaffold(
                    bottomBar = {
                        if (navController.currentBackStackEntry?.destination?.route != XplorerScreens.Login.route) {
                            BottomBar { navController.navigate(it) }
                        }},
                    modifier = Modifier.fillMaxSize(),
                    containerColor = BackgroundColor
                ) { innerPadding ->
                    NavHostComposable(
                        innerPadding = innerPadding,
                        navController = navController,
                    )
                }
            }
        }
    }
    private fun createNotificationChannel() {
        val notificationChannel = NotificationChannel(
            notificationChannelID,
            "Xplorer Notification",
            NotificationManager.IMPORTANCE_HIGH
        )
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(notificationChannel)
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