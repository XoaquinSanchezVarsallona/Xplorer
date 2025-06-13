 package com.example.xplorer.navigator

 import androidx.annotation.RequiresApi
 import androidx.compose.foundation.layout.PaddingValues
 import androidx.compose.foundation.layout.fillMaxSize
 import androidx.compose.foundation.layout.padding
 import androidx.compose.runtime.Composable
 import androidx.compose.ui.Modifier
 import androidx.navigation.NavHostController
 import androidx.navigation.compose.NavHost
 import androidx.navigation.compose.composable
 import com.example.xplorer.ui.pages.HomePage
 import com.example.xplorer.ui.theme.LargePadding
 import com.example.xplorer.viewModels.XplorerViewModel
 import android.os.Build

 @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
 @Composable
 fun NavHostComposable (innerPadding: PaddingValues,
                        navController: NavHostController,
                        sharedViewModel: XplorerViewModel
 ) {
     NavHost(
         navController = navController,
         startDestination = XplorerScreens.Login.name,
         modifier = Modifier
             .fillMaxSize()
             .padding(innerPadding)
             .padding(LargePadding)
     ) {
         composable(route = XplorerScreens.Login.name) {
             HomePage(viewModel = sharedViewModel)
         }
         composable(route = XplorerScreens.Home.name) {
             //            MainMenu(
             //                onClick = { navController.navigate(it) }
             //            )
         }
         composable(route = XplorerScreens.Favorite.name) {
             //            Favorite()
         }
         composable(route = XplorerScreens.City.name) {
             //            City()
         }
         composable(route = XplorerScreens.Country.name) {
             //            City()
         }
         composable(route = XplorerScreens.Profile.name) {

         }
     }
 }
