package com.example.xplorer.room

import android.content.Context
import com.example.xplorer.api.Notifier
import com.example.xplorer.api.ToastNotifier
import com.example.xplorer.api.wikipedia.WikipediaServiceImpl
import com.example.xplorer.api.world_bank.WorldBankServiceImpl
import com.example.xplorer.room.dao.CountryDao
import com.example.xplorer.room.entities.Country
import com.example.xplorer.storage.XplorerDatabase
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class CountryInitializer @Inject constructor(
    private val worldBankService: WorldBankServiceImpl,
    private val wikipediaServiceImpl: WikipediaServiceImpl,
    @ApplicationContext private val context: Context,
    private val notifier: Notifier = ToastNotifier()
)  {
    private val database = XplorerDatabase.getDatabase(context)
    private val countryDao: CountryDao = database.countryDao()

    fun initialize () {
        CoroutineScope(Dispatchers.IO).launch {
            val basicCountryInfo = worldBankService.fetchTourismCountriesSuspend(context)
            val countriesInfo =wikipediaServiceImpl.getData(basicCountryInfo, context, notifier)
            val countries = countriesInfo.map { (worldBankData, countryData) ->
                Country(
                    id = worldBankData.country.id,
                    name = worldBankData.country.value,
                    tourism = worldBankData.value,
                    attractions = countryData.attractions,
                    culture = countryData.culture,
                    history = countryData.history
                )
            }
            countries.forEach {countryDao.insertCountry(it)}
        }
    }
}