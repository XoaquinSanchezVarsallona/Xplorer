package com.example.xplorer.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.xplorer.room.entities.Country

@Dao
interface CountryDao {
    @Insert
    suspend fun insertCountry(country: Country)

    @Update
    suspend fun updateCountry(country: Country)

    @Query("SELECT * FROM countries ORDER BY RANDOM()")
    fun getAllCountries(): LiveData<List<Country>>

    @Query("SELECT * FROM countries WHERE id = :id")
    fun getCountryById(id: String): LiveData<Country>
}