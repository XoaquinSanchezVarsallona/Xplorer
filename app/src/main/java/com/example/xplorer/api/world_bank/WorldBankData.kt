package com.example.xplorer.api.world_bank

data class WorldBankData (
    val country: Country,
    val value: Double? = null, // Esto es la cantidad de gente que viajo a este pais.
) {
    fun countryCodeToFlagEmoji(): String {
        val country_code = country.id
        val first = country_code[0].uppercaseChar() - 'A' + 0x1F1E6
        val second = country_code[1].uppercaseChar() - 'A' + 0x1F1E6
        return String(Character.toChars(first)) + String(Character.toChars(second))
    }
}

data class Country (
    val id: String, // Esto es el codigo de pais.
    val value: String // Esto es el nombre del pais.
)