package com.example.xplorer.ui.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.xplorer.navigator.XplorerScreens
import com.example.xplorer.ui.theme.LargePadding
import com.example.xplorer.ui.theme.MediumPadding

@Composable
fun LandingScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(LargePadding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Bienvenido", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(MediumPadding))

        Button(
                onClick = { navController.navigate(XplorerScreens.Login) }) {
            Text("Iniciar Sesión")
        }

        Spacer(modifier = Modifier.height(MediumPadding))

        OutlinedButton(onClick = { navController.navigate(XplorerScreens.Register) }) {
            Text("Registrarse")
        }
    }
}

@Preview
@Composable
fun LandingScreenPreview() {
    LandingScreen(navController = NavController(context = LocalContext.current))
}

