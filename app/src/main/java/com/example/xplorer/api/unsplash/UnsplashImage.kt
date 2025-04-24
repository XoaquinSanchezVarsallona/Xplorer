package com.example.xplorer.api.unsplash

data class UnsplashImage(
    val id : String,
    val description : String?,
    val urls: UnsplashUrls
)

data class UnsplashUrls(
    val raw: String,
    val regular: String,
    val small: String
)

data class UnsplashResponse(
    val results: List<UnsplashImage>
)