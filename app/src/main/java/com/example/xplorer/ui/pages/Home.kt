package com.example.xplorer.ui.pages

import android.Manifest
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.xplorer.R
import com.example.xplorer.api.world_bank.Country
import com.example.xplorer.api.world_bank.WorldBankData
import com.example.xplorer.components.CountryCarousel
import com.example.xplorer.components.ExpandableSearchBar
import com.example.xplorer.navigator.XplorerScreens
import com.example.xplorer.ui.theme.MediumPadding
import com.example.xplorer.viewModels.HomeViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomePage(navController: NavController) {
    val homeViewModel = hiltViewModel<HomeViewModel>()
    val imageMap by homeViewModel.imageMap.collectAsState(initial = emptyMap())
    val countries by homeViewModel.countries.collectAsState(initial = emptyList())

    val postNotificationPermission = rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)

    LaunchedEffect(Unit) {
        if (!postNotificationPermission.status.isGranted) {
            postNotificationPermission.launchPermissionRequest()
        }
    }

    val items = countries.map {
        WorldBankData(Country(it.id, it.name), it.tourism)
    }

    if (items.isEmpty()) {
        Text(
            text = stringResource(R.string.no_data),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.onBackground
        )
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            ExpandableSearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(MediumPadding)),
                items = items,
                onItemSelected = { name ->
                    navController.navigate(XplorerScreens.Country.withArgs(name)) {
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )

            Spacer(modifier = Modifier.height(MediumPadding))

            Text(
                text = stringResource(R.string.explore_text),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(horizontal = MediumPadding),
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(MediumPadding))

            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CountryCarousel(imageMap = imageMap, navController = navController)
            }

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}