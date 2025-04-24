package com.example.xplorer.api.unsplash

import androidx.compose.ui.unit.Constraints
import com.example.xplorer.Constants
import com.example.xplorer.R
import retrofit.Call
import retrofit.http.GET
import retrofit.http.Query

interface UnsplashService {
    @GET("search/photos")
    fun getImage (
        @Query("query") query : String,
        @Query("orientation") orientation : String,
        @Query("client_id") apiKey: String = Constants.unsplashAccessKey
    ) : Call<UnsplashResponse>


}