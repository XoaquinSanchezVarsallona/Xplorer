package com.example.xplorer.api.wikipedia

import retrofit.http.GET
import retrofit.Call
import retrofit.http.Path

interface WikipediaService {

    @GET("summary/{q}")
    fun getInfo(
        @Path("q") query: String,
    ) : Call<ExtraitData>
}