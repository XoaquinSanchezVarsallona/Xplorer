package com.example.xplorer.api.world_bank

import android.content.Context
import android.util.Log
import com.example.xplorer.R
import com.example.xplorer.api.Notifier
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.reflect.TypeToken
import retrofit.Call
import retrofit.Callback
import retrofit.GsonConverterFactory
import retrofit.Response
import retrofit.Retrofit
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class WorldBankServiceImpl @Inject constructor() {
    fun getTourismMostVisitedCountries (
        page : Int,
        notifier : Notifier,
        context : Context,
        onSuccess : (List<WorldBankData>) -> Unit,
        onFail : () -> Unit,
        loadingFinished: () -> Unit

    ) {
        val retrofit : Retrofit = Retrofit.Builder()
            .baseUrl(context.getString(R.string.world_bank_url))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(WorldBankService::class.java)

        val call: Call<JsonArray> = service.getTourismArrivals (
            format = "json",
            mrnev = 1,
            perPage = 50,
            page = page
        )
        call.enqueue(object : Callback<JsonArray>  {
            override fun onResponse(response: Response<JsonArray>?, retrofit: Retrofit?) {
                loadingFinished()
                if (response != null && response.isSuccess) {
                    val body: JsonArray  = response.body()
                    if (body.size() != 0) {
                        val dataArray = body[1].asJsonArray
                        val gson = Gson()

                        val type = object : TypeToken<List<WorldBankData>>() {}.type
                        val countries: List<WorldBankData> = gson.fromJson(dataArray, type)

                        onSuccess(countries)
                    } else {
                        notifier.notify("World Bank Data is null", context)
                        onFail()
                    }
                } else {
                    notifier.notify("Bad request", context)
                    onFail()
                }
            }

            override fun onFailure(t: Throwable?) {
                notifier.notify("error: $t", context)
                Log.e("World Service ERROR","error: $t")
                onFail()
                loadingFinished()
            }

        })
    }

    suspend fun fetchTourismCountriesSuspend(context: Context): List<WorldBankData> =
        suspendCoroutine { continuation ->
            getTourismMostVisitedCountries(
                page = 1,
                notifier = object : Notifier {
                    override fun notify(message: String, context: Context) {
                        // puedes loguearlo si quieres
                        Log.e("Notifier", message)
                    }
                },
                context = context,
                onSuccess = { countries ->
                    continuation.resume(countries)
                },
                onFail = {
                    continuation.resumeWithException(Exception("Error fetching countries"))
                },
                loadingFinished = {
                    // no necesitas hacer nada aquí
                }
            )
        }
}