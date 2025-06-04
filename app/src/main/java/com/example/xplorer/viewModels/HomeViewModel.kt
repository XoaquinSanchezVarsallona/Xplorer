package com.example.xplorer.viewModels

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import com.example.xplorer.api.Notifier
import com.example.xplorer.api.unsplash.UnsplashImage
import com.example.xplorer.api.unsplash.UnsplashServiceImpl
import com.example.xplorer.room.CountryInitializer
import com.example.xplorer.storage.XplorerDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor (
    private val UNSapi : UnsplashServiceImpl,
    private val notifier : Notifier,
    private val countryInitializer: CountryInitializer,
    @ApplicationContext val context : Context
) : ViewModel() {
    private val database = XplorerDatabase.getDatabase(context)
    val countries = database.countryDao().getAllCountries().asFlow()

    init {
        runBlocking {
            countries.collect { countries ->
                if (countries.isEmpty()) {
                    countryInitializer.initialize()
                }
            }
        }
    }

    private val _imageMap = MutableStateFlow<Map<String, UnsplashImage>>(emptyMap())
    val imageMap: StateFlow<Map<String, UnsplashImage>> = _imageMap
}

