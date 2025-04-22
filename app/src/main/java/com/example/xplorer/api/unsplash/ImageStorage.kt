package com.example.xplorer.api.unsplash

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.xplorer.api.Notifier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ImageStorage (
    private val api : UnsplashServiceImpl,
    private val notifier : Notifier,
)  : ViewModel() {

    // Mapa que asocia un nombre de búsqueda con una imagen de Unsplash
    private val _imageMap = mutableMapOf<String, StateFlow<UnsplashImage>>()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun getImage (context : Context, keyword : String) : StateFlow<UnsplashImage> {
        val key = keyword.lowercase()
        if (!_imageMap.containsKey(key)) {
            fetchImageFor(context, keyword)
        }

        // Retornamos el flujo correspondiente
        return _imageMap[keyword]!!
    }
    private fun fetchImageFor(context : Context, keyword: String) {
        val actualContext = context.applicationContext

        viewModelScope.launch {
            api.getImage(
                context = actualContext,
                query = keyword,
                onSuccess = { image ->
                    _imageMap[keyword] = MutableStateFlow(image)
                },
                onFail = {
                    notifier.notify(
                        "No se pudo obtener la imagen para '$keyword'",
                        context = actualContext
                    )
                },
                loadingFinished = {
                    _isLoading.value = false
                },
                notifier = notifier
            )
        }
    }
}
