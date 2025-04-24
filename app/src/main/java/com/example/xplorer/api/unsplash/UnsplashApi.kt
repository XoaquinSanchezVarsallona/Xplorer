package com.example.xplorer.api.unsplash

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.xplorer.Constants
import com.example.xplorer.R
import com.example.xplorer.api.Notifier
import retrofit.Call
import retrofit.Callback
import retrofit.GsonConverterFactory
import retrofit.Response
import retrofit.Retrofit
import javax.inject.Inject

class UnsplashServiceImpl @Inject constructor() {

    fun getImage(
        query: String,
        context: Context,
        notifier : Notifier,
        onSuccess: (UnsplashImage) -> Unit,
        onFail: () -> Unit,
        loadingFinished: () -> Unit
    ) {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(context.getString(R.string.unsplash_url))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service: UnsplashService = retrofit.create(UnsplashService::class.java)

        val call: Call<UnsplashResponse> = service.getImage(
            query = query,
            orientation = "landscape",
            apiKey = Constants.unsplashAccessKey
        )

        call.enqueue(object : Callback<UnsplashResponse> {
            override fun onResponse(response: Response<UnsplashResponse>?, retrofit: Retrofit?) {
                loadingFinished()
                if (response != null && response.isSuccess) {
                    val image: UnsplashResponse? = response.body()
                    if (image != null) {
                        onSuccess(image.results[0])
                    } else {
                        notifier.notify("Something went wrong loading image", context)
                        onFail()
                    }
                } else if (response != null) {
                    Log.e("Unsplash Api Network Error", response.errorBody().string())
                    notifier.notify("Failed Auth on Unsplash API", context)
                    onFail()
                } else {
                    notifier.notify("Bad request at Unsplash Api", context)
                    onFail()
                }

            }

            override fun onFailure(t: Throwable?) {
                notifier.notify("Can't get image", context)
                Log.e("Unsplash Api ERROR", t.toString())
                onFail()
                loadingFinished()            }
        })
    }
}