package com.example.xplorer.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.TextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.xplorer.api.world_bank.WorldBankData

@Composable
fun ExpandableSearchBar (modifier: Modifier, items : List<WorldBankData>, onItemSelected: (String) -> Unit){
    var query by remember { mutableStateOf("") }

    val filteredItems = remember(query, items) {
        if (query.isBlank()) {
            emptyList()
        } else {
            items.filter { it.country.value.startsWith(query, ignoreCase = true) }
        }
    }

    Column(modifier = modifier) {
        TextField(
            value = query,
            onValueChange = {
                query = it
            },
            placeholder = { Text("Escribí tu búsqueda...") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
        )

        if (query.length > 3 && filteredItems.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 200.dp)
            ) {
                items(filteredItems.subList(0, minOf(5, filteredItems.size))) { item ->
                    FlagLabel(
                        countryFlag  = item.countryCodeToFlagEmoji(),
                        countryName = item.country.value,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onItemSelected(item.country.value)
                                query = "Escribi tu búsqueda..."
                                // Logica de cambiar de pantalla a CountryPage
                            }
                            .padding(vertical = 8.dp, horizontal = 16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun FlagLabel (modifier: Modifier, countryFlag: String, countryName: String) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier) {
        Text(text = countryFlag, fontSize = TODO())
        Text(text = countryName, fontSize = TODO())
    }
}