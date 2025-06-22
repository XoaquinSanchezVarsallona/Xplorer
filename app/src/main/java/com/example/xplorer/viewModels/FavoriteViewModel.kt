package com.example.xplorer.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.xplorer.room.entities.Country
import com.example.xplorer.storage.XplorerDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    @ApplicationContext val context: Context
) : ViewModel() {
    private val database = XplorerDatabase.getDatabase(context)
    private val countryDao = database.countryDao()

    private val _favoriteList = countryDao.getFavoriteCountries()
    val favorites = _favoriteList

    suspend fun updateCountry(country: Country) {
        countryDao.updateCountry(country)
    }

}