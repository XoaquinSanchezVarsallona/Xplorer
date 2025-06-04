package com.example.xplorer.api.wikipedia

import android.content.Context
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
            val countryName = wbData.country.id
            val attraction = getExtraitSuspend(
                query = "places to visit in $countryName",
                context = context,
                notifier = notifier
            )
            val culture = getExtraitSuspend(
                query = "culture of $countryName",
                context = context,
                notifier = notifier
            )
            val history = getExtraitSuspend(
                query = "history of $countryName",
                context = context,
                notifier = notifier
            )


            map[wbData] = CountryData(
                attractions = attraction,
                culture = culture,
                history = history
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
        val extraitData = service.getInfo(query)

        extraitData.enqueue(object : Callback<ExtraitData> {
            override fun onResponse(response: retrofit.Response<ExtraitData>?, retrofit: retrofit.Retrofit?) {
                if (response != null && response.isSuccess) {
                    deferred.complete(response.body()!!.extrait)
                } else {
                    notifier.notify("Failed to fetch data for query: $query", context)
                    deferred.completeExceptionally(Exception("Failed to fetch data"))
                }
            }

            override fun onFailure(t: Throwable?) {
                notifier.notify("Failed to fetch data for query: $query", context)
                deferred.completeExceptionally(t ?: Exception("Unknown error"))
            }
        })

        return deferred.await()
    }
}

