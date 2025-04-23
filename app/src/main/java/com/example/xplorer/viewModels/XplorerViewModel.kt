package com.example.xplorer.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.xplorer.api.Notifier
import com.example.xplorer.api.ToastNotifier
import com.example.xplorer.api.unsplash.UnsplashImage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class XplorerViewModel @Inject constructor (
    private val wbDataStorage: WBDataStorage,
    private val imageStorage: ImageStorage,
)  : ViewModel() {

    val countryList = wbDataStorage.countryList
    val imageMap = imageStorage.imageMap
    val isLoading : StateFlow<Boolean> = combine(
    wbDataStorage.isLoading,
    imageStorage.isLoading
    ) { wbLoading, imgLoading ->
        wbLoading || imgLoading
    }.stateIn(viewModelScope, SharingStarted.Eagerly, true)


    fun initializeData (context: Context) {
        val actualContext = context.applicationContext
        fetchCountryInfo(actualContext)
        viewModelScope.launch {
            wbDataStorage.countryList
                .filter { it.isNotEmpty() } // Esperamos a que haya datos
                .first() // Solo la primera vez
                .let { countryData ->
                    // Pedimos una imagen por país (por ejemplo usando el nombre)
                    countryData.forEach { data ->
                        fetchImages(actualContext, data.country.value)
                    }
                }
        }
    }

    private fun fetchCountryInfo (context : Context) {
        wbDataStorage.fetchCountryInfo(context)
    }
    private fun fetchImages (context : Context, keyword : String) {
        return imageStorage.fetchImageFor(context, keyword)
    }


}