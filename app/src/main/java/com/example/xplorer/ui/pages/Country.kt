package com.example.xplorer.ui.pages

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.xplorer.components.FlagLabel
import com.example.xplorer.navigator.XplorerScreens
import com.example.xplorer.storage.XplorerDatabase
import com.example.xplorer.ui.theme.Greyscale500
import com.example.xplorer.ui.theme.MediumPadding

@Composable
fun Country(navController: NavController, id: String) {
    val database = XplorerDatabase.getDatabase(LocalContext.current)
    val countryDao = database.countryDao()

    val country = countryDao.getCountryById(id).observeAsState().value
    if (country == null) {
        Text(
            text = "No hay datos disponibles",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.fillMaxSize(),
            color = Greyscale500
        )
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(MediumPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            FlagLabel(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MediumPadding),
                countryFlag = country.id,
                countryName = country.name,
            )
            Text(
                text = country.name,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(MediumPadding))

            Text(
                text = "Amount of Tourists : ${country.tourism}",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(MediumPadding))

            Text(
                text = "ID: ${country.id}",
                style = MaterialTheme.typography.bodyLarge,
                color = Greyscale500
            )

            Spacer(modifier = Modifier.height(MediumPadding))

            Text(
                text = "Description / Details of that country: ${country.details}",
                style = MaterialTheme.typography.bodyLarge,
                color = Greyscale500
            )
        }
    }
}