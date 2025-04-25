package com.example.xplorer.components

import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.xplorer.api.world_bank.Country
import com.example.xplorer.api.world_bank.WorldBankData
import com.example.xplorer.ui.theme.Typography

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

        if (query.isNotEmpty() && filteredItems.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 200.dp)
                    .background(Color.White)
            ) {
                items(filteredItems.subList(0, minOf(5, filteredItems.size))) { item ->
                    FlagLabel(
                        countryFlag  = item.countryCodeToFlagEmoji(),
                        countryName = item.country.value,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onItemSelected(item.country.value)
                                query = ""
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
        Text(text = countryFlag, style = Typography.bodyMedium, modifier = Modifier.padding(start = 8.dp))
        Text(text = countryName, style = Typography.bodyMedium, modifier = Modifier.padding(start = 8.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewExpandableSearchBar() {
    val dummyItems = listOf(
        WorldBankData(country = Country(id = "AR", value = "Argentina"), value = 123456.0),
        WorldBankData(country = Country(id = "AU", value = "Australia"), value = 98765.0),
        WorldBankData(country = Country(id = "AT", value = "Austria"), value = 54321.0),
        WorldBankData(country = Country(id = "AO", value = "Angola"), value = 6789.0),
        WorldBankData(country = Country(id = "AL", value = "Albania"), value = 4321.0)
    )

    ExpandableSearchBar(
        modifier = Modifier.padding(16.dp),
        items = dummyItems,
        onItemSelected = { selected ->
            println("Seleccionado: $selected")
        }
    )
}