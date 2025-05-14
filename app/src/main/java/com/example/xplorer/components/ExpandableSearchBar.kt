package com.example.xplorer.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.xplorer.R
import com.example.xplorer.api.world_bank.Country
import com.example.xplorer.api.world_bank.WorldBankData
import com.example.xplorer.ui.theme.BackgroundColor
import com.example.xplorer.ui.theme.UnselectedColor
import com.example.xplorer.ui.theme.CountryCardHeight
import com.example.xplorer.ui.theme.Greyscale400
import com.example.xplorer.ui.theme.MediumPadding
import com.example.xplorer.ui.theme.SmallPadding
import com.example.xplorer.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
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
            placeholder = { Text(stringResource(R.string.search_bar_placeholder)) },
            singleLine = true,
            textStyle = Typography.titleMedium,
            modifier = Modifier
                .fillMaxWidth()
                .background(UnselectedColor),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = UnselectedColor, // Fondo cuando está enfocado
                focusedPlaceholderColor = Greyscale400,
                unfocusedPlaceholderColor = Greyscale400,
                unfocusedContainerColor = UnselectedColor, // Fondo cuando no está enfocado
                focusedTextColor = Greyscale400, // Color del texto cuando está enfocado
                unfocusedTextColor = Greyscale400, // Color del texto cuando no está enfocado
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        if (query.isNotEmpty() && filteredItems.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = CountryCardHeight)
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
                            }
                            .padding(vertical = SmallPadding, horizontal = MediumPadding)

                    )
                }
            }
        }
    }
}

@Composable
fun FlagLabel (modifier: Modifier, countryFlag: String, countryName: String) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier) {
        Text(text = countryFlag, style = Typography.bodyMedium, modifier = Modifier.padding(start = SmallPadding))
        Text(text = countryName, style = Typography.bodyMedium, modifier = Modifier.padding(start = SmallPadding))
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
    Box(modifier =  Modifier.fillMaxSize().background(BackgroundColor))

    ExpandableSearchBar(
        modifier = Modifier.padding(MediumPadding).clip(RoundedCornerShape(MediumPadding)),
        items = dummyItems,
        onItemSelected = { selected ->
            println("Seleccionado: $selected")
        }
    )
}