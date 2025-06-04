package com.example.xplorer.api.wikipedia

import retrofit.http.GET
import retrofit.Call
import retrofit.http.Query

interface WikipediaService {

    @GET("search/title?/q={query}&format=json")
    fun getInfo(
        @Query("country") query : String
    ) : Call<ExtraitData>
}