package com.example.xplorer.api.world_bank

import retrofit.Call
import retrofit.http.GET
import retrofit.http.Query

interface WorldBankService {
    @GET("country/all/indicator/ST.INT.ARVL")
    fun getTourismArrivals(
        @Query("format") format: String = "json",
        @Query("mrnev") mrnev: Int = 1,
        @Query("per_page") perPage: Int = 50,
        @Query("page") page : Int = 1
    ): Call<List<WorldBankData>>

}