package com.example.xplorer.viewModels

import android.content.Context
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.xplorer.api.Notifier
import com.example.xplorer.api.ToastNotifier
import com.example.xplorer.api.unsplash.UnsplashImage
import com.example.xplorer.api.unsplash.UnsplashServiceImpl
import com.example.xplorer.api.world_bank.WorldBankData
import com.example.xplorer.api.world_bank.WorldBankServiceImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class XplorerViewModel @Inject constructor(
    private val WBapi : WorldBankServiceImpl,
    private val UNSapi : UnsplashServiceImpl,
    private val notifier : Notifier
) : ViewModel() {

    private val _countryList = MutableStateFlow<List<WorldBankData>> (emptyList())
    val countryList : StateFlow<List<WorldBankData>> = _countryList

    private val _WBisLoading = MutableStateFlow(true)
    private var i = 2

    private val _imageMap = MutableStateFlow<Map<String, UnsplashImage>>(emptyMap())
    val imageMap: StateFlow<Map<String, UnsplashImage>> = _imageMap

    private val _UNSisLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> =  combine(
        _WBisLoading,
        _UNSisLoading
    ) { wbLoading, imgLoading ->
        wbLoading || imgLoading
    }.stateIn(viewModelScope, SharingStarted.Eagerly, true)

    fun initializeData (context: Context) {
        val actualContext = context.applicationContext
        fetchCountryInfo(actualContext)
        viewModelScope.launch {
            fetchImageFor(actualContext, _countryList
                .filter { it.isNotEmpty() } // Esperamos a que haya datos
                .first()[0].country.value )// Solo la primera vez
//                .let { countryData ->
//                    // Pedimos una imagen por país (por ejemplo usando el nombre)
//                    countryData.forEach { data ->
//                        fetchImageFor(actualContext, data.country.value)
//                    }
//                }

        }
    }

    private fun fetchCountryInfo (context : Context) {
        val actualContext = context.applicationContext

        viewModelScope.launch {
            WBapi.getTourismMostVisitedCountries(
                notifier = notifier,
                context = actualContext,
                onSuccess = { data ->
                    _countryList.value = data.sortedByDescending { it.value }
                },
                onFail = {
                    notifier.notify("Something went wrong fetching info in World Bank!", context)
                },
                loadingFinished = {
                    _WBisLoading.value = false
                },
                page = i++
            )
        }
    }

    private fun fetchImageFor(context : Context, keyword: String) {
        val actualContext = context.applicationContext

        viewModelScope.launch {
            UNSapi.getImage(
                context = actualContext,
                query = keyword,
                onSuccess = { image ->
                    _imageMap.update { currentMap ->
                        currentMap + (keyword to image)
                    }
                },
                onFail = {
                    notifier.notify(
                        "No se pudo obtener la imagen para '$keyword'",
                        context = actualContext
                    )
                },
                loadingFinished = {
                    _UNSisLoading.value = false
                },
                notifier = notifier
            )
        }
    }
}