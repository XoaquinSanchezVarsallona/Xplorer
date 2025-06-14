 package com.example.xplorer.navigator

 import android.media.MediaParser
 import android.os.Build
 import android.util.Log
 import androidx.annotation.RequiresApi
 import androidx.compose.foundation.layout.PaddingValues
 import androidx.compose.foundation.layout.fillMaxSize
 import androidx.compose.foundation.layout.padding
 import androidx.compose.runtime.Composable
 import androidx.compose.ui.Modifier
 import androidx.navigation.NavHostController
 import androidx.navigation.NavType
 import androidx.navigation.compose.NavHost
 import androidx.navigation.compose.composable
 import androidx.navigation.navArgument
 import com.example.xplorer.ui.pages.Country
 import com.example.xplorer.ui.pages.HomePage
 import com.example.xplorer.ui.pages.LoginScreen
 import com.example.xplorer.ui.theme.LargePadding

 @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
 @Composable
 fun NavHostComposable(
     innerPadding: PaddingValues,
     navController: NavHostController,
 ) {
     NavHost(
         navController = navController,
         startDestination = XplorerScreens.Home.route,
         modifier = Modifier
             .fillMaxSize()
             .padding(innerPadding)
             .padding(LargePadding)
     ) {
         composable(route = XplorerScreens.Login.route) {
             LoginScreen(navController)
         }
         composable(route = XplorerScreens.Home.route) {
             HomePage(navController)
         }
         composable(route = XplorerScreens.Favorite.route) {
             // Favorite()
         }
         composable(route = XplorerScreens.City.route) {
             // City()
         }
         composable(
             route = "${XplorerScreens.Country.route}/{countryId}",
             arguments = listOf(navArgument("countryId") { type = NavType.StringType })
         ) { backStackEntry ->
             val countryId = backStackEntry.arguments?.getString("countryId")
             Log.d("NAV", "Country ID: $countryId")
             Country(navController = navController ,id = countryId!!)
         }
         composable(route = XplorerScreens.Profile.route) {
             // Profile()
         }
     }
 }