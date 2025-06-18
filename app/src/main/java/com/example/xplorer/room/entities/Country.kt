package com.example.xplorer.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "countries")
data class Country(
    @PrimaryKey val id : String,
    val name: String,
    val tourism : Double?,
    val details : String,
) {
    fun countryCodeToFlagEmoji(): String {
        val countryCode = id
        val first = countryCode[0].uppercaseChar() - 'A' + 0x1F1E6
        val second = countryCode[1].uppercaseChar() - 'A' + 0x1F1E6
        return String(Character.toChars(first)) + String(Character.toChars(second))
    }
}