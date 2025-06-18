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
    fun getTourismMostVisitedCountries(
        page: Int,
        notifier: Notifier,
        context: Context,
        onSuccess: (List<WorldBankData>) -> Unit,
        onFail: () -> Unit,
        loadingFinished: () -> Unit
    ) {
        val retrofit = Retrofit.Builder()
            .baseUrl(context.getString(R.string.world_bank_url))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(WorldBankService::class.java)

        service.getTourismArrivals(page = page).enqueue(object : Callback<JsonArray> {
            override fun onResponse(response: Response<JsonArray>?, retrofit: Retrofit?) {
                loadingFinished()
                if (response != null && response.isSuccess) {
                    val body = response.body()
                    val dataArray = if (body != null && body.size() > 1) body[1].asJsonArray else null
                    if (dataArray != null && dataArray.size() > 0) {
                        val type = object : TypeToken<List<WorldBankData>>() {}.type
                        val countries: List<WorldBankData> = Gson().fromJson(dataArray, type)
                        onSuccess(countries)
                    } else {
                        notifier.notify("No data on page $page", context)
                        onFail()
                    }
                } else {
                    notifier.notify("Bad request for page $page", context)
                    onFail()
                }
            }

            override fun onFailure(t: Throwable?) {
                loadingFinished()
                notifier.notify("Error fetching page $page: ${t?.message}", context)
                Log.e("WorldBankServiceImpl", "onFailure", t)
                onFail()
            }
        })
    }

    /**
     * Suspend: obtiene datos de una página usando la función callback anterior
     */
    private suspend fun fetchTourismPage(
        page: Int,
        context: Context
    ): List<WorldBankData> = suspendCoroutine { cont ->
        getTourismMostVisitedCountries(
            page = page,
            notifier = object : Notifier {
                override fun notify(message: String, context: Context) {
                    Log.w("Notifier", message)
                }
            },
            context = context,
            onSuccess = { countries -> cont.resume(countries) },
            onFail = { cont.resume(emptyList()) },
            loadingFinished = {}
        )
    }

    /**
     * Suspend: obtiene todos los países iterando páginas hasta que no haya más datos
     */
    suspend fun fetchAllTourismCountries(context: Context): List<WorldBankData> {
        val all = mutableListOf<WorldBankData>()
        val perPage = 50
        var page = 2

        while (true) {
            try {
                val list = fetchTourismPage(page, context)
                if (list.isEmpty()) break
                all += list
                if (list.size < perPage) break
                page++
            } catch (e: Exception) {
                Log.e("WorldBankServiceImpl", "Error fetching page $page", e)
                break
            }
        }
        return all
    }
}