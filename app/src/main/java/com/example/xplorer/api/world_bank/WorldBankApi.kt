package com.example.xplorer.api.world_bank

import android.content.Context
import android.widget.Toast
import com.example.xplorer.R
import com.example.xplorer.api.Notifier
import retrofit.Call
import retrofit.Callback
import retrofit.GsonConverterFactory
import retrofit.Response
import retrofit.Retrofit
import javax.inject.Inject

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

        val call: Call<List<WorldBankData>> = service.getTourismArrivals (
            format = "json",
            mrnev = 1,
            perPage = 50,
            page = page
        )

        call.enqueue(object : Callback<List<WorldBankData>>  {
            override fun onResponse(response: Response<List<WorldBankData>>?, retrofit: Retrofit?) {
                loadingFinished()
                if (response != null && response.isSuccess) {
                    val countries: List<WorldBankData>? = response.body()
                    if (countries != null) {
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
                notifier.notify("Can't get World Bank data", context)
                onFail()
                loadingFinished()
            }

        })
    }
}