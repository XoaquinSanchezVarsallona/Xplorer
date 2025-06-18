package com.example.xplorer.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.xplorer.api.Notifier
import com.example.xplorer.api.unsplash.UnsplashImage
import com.example.xplorer.api.unsplash.UnsplashServiceImpl
import com.example.xplorer.room.CountryInitializer
import com.example.xplorer.storage.XplorerDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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
        val sharedCountries = countries.shareIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            replay = 1
        )

        viewModelScope.launch(Dispatchers.IO) {
            sharedCountries
                .take(1)
                .collect { countries ->
                    if (countries.isEmpty()) {
                        countryInitializer.initialize()
                    }
                }
        }

        // 2) Fetch de imágenes
        viewModelScope.launch(Dispatchers.IO) {
            sharedCountries
                .take(15)
                .collect { countries ->
                    countries.forEach { country ->
                        if (!_imageMap.value.containsKey(country.name)) {
                            fetchImageForCountry(country.name)
                        }
                    }
                }
        }
    }

    private val _imageMap = MutableStateFlow<Map<String, UnsplashImage>>(emptyMap())
    val imageMap: StateFlow<Map<String, UnsplashImage>> = _imageMap
    private var loading = MutableStateFlow(true)

    private fun fetchImageForCountry(countryName: String) {
        val actualContext = context.applicationContext
        viewModelScope.launch {
            UNSapi.getImage(
                context = actualContext,
                query = countryName,
                onSuccess = { image ->
                    _imageMap.update { currentMap ->
                        currentMap + (countryName to image)
                    }
                },
                onFail = {
                    notifier.notify(
                        "No se pudo obtener la imagen para '$countryName'",
                        context = actualContext
                    )
                },
                notifier = notifier,
                loadingFinished = { loading.value = false }
            )
        }
    }
}

