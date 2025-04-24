package com.example.xplorer.api.unsplash

import com.example.xplorer.R
import retrofit.Call
import retrofit.http.GET
import retrofit.http.Query

interface UnsplashService {
    @GET("search/photos?")
    fun getImage (
        @Query("query") query : String,
        @Query("orientation") orientation : String,
        @Query("client_id") apiKey: String = R.string.unsplash_access_key.toString()
    ) : Call<UnsplashImage>


}