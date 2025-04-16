package com.example.xplorer.api.unsplash

data class UnsplashImage(
    val id : String,
    val description : String,
    val urls: UnsplashUrls
)

data class UnsplashUrls(
    val raw: String
)