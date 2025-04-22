package com.example.xplorer.api.world_bank

data class WorldBankData (
    val country: Country,
    val value: Double? = null, // Esto es la cantidad de gente que viajo a este pais.
)

data class Country (
    val id: String, // Esto es el codigo de pais.
    val value: String // Esto es el nombre del pais.
)