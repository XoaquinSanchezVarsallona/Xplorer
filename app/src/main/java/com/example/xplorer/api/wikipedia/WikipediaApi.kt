package com.example.xplorer.api.wikipedia

import android.content.Context
import android.util.Log
import com.example.xplorer.R
import com.example.xplorer.api.Notifier
import com.example.xplorer.api.world_bank.WorldBankData
import kotlinx.coroutines.CompletableDeferred
import retrofit.Callback
import retrofit.GsonConverterFactory
import retrofit.Retrofit
import javax.inject.Inject

class WikipediaServiceImpl @Inject constructor() {
    suspend fun getData(
        countries : List <WorldBankData>,
        context : Context,
        notifier: Notifier

    ) : MutableMap<WorldBankData, CountryData> {
        val map = mutableMapOf<WorldBankData, CountryData>()

        countries.forEach { wbData ->
            val countryName = wbData.country.value
            val details = getExtraitSuspend(
                query = "$countryName",
                context = context,
                notifier = notifier
            )

            map[wbData] = CountryData(
                details = details
            )
        }
        return map
    }

    private suspend fun getExtraitSuspend(
        query: String,
        context: Context,
        notifier: Notifier
    ): String {
        val deferred = CompletableDeferred<String>()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(context.getString(R.string.wikipedia_url))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(WikipediaService::class.java)
        try {
            val extraitData = service.getInfo(query)
            extraitData.enqueue(object : Callback<ExtraitData> {
                override fun onResponse(response: retrofit.Response<ExtraitData>?, retrofit: retrofit.Retrofit?) {
                    if (response != null && response.isSuccess && response.body() != null) {
                        val details =  response.body()?.extract ?: "there is nothing in wikipedia"
                        deferred.complete(details)
                    } else {
                        val details = "there is nothing in wikipedia"
                        deferred.complete(details)
                    }
                }

                override fun onFailure(t: Throwable?) {
                    val errorMessage = "Failed to fetch data for query: $query"
                    notifier.notify(errorMessage, context)
                    deferred.completeExceptionally(t ?: Exception(errorMessage))
                }
            })
        } catch (e: Exception) {
            val errorMessage = "Error while making API call: ${e.message}"
            Log.e("WikipediaServiceImpl", errorMessage, e)
            deferred.completeExceptionally(e)
        }


        return deferred.await()
    }
}

