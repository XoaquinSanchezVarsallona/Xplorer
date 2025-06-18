package com.example.xplorer.api.wikipedia

import com.google.gson.annotations.SerializedName

data class CountryData (
    val details : String
)

data class ExtraitData (
    @SerializedName("extract")
    val extract : String
)