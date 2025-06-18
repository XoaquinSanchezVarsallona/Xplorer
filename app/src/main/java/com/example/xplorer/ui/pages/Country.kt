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
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.xplorer.api.world_bank.Country
import com.example.xplorer.api.world_bank.WorldBankData
import com.example.xplorer.components.FlagLabel
import com.example.xplorer.R
import com.example.xplorer.storage.XplorerDatabase
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
            color = MaterialTheme.colorScheme.onBackground
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
                countryFlag = WorldBankData(Country(country.id, country.name), country.tourism).countryCodeToFlagEmoji(),
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
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(MediumPadding))

            Text(
                text = stringResource(R.string.country_details_description, country.details),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.surface
            )
        }
    }
}