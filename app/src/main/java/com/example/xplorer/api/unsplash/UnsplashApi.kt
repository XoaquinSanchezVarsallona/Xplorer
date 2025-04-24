package com.example.xplorer.api.unsplash

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

        val call: Call<UnsplashImage> = service.getImage(
            query = query,
            orientation = "landscape",
            apiKey = context.getString(R.string.unsplash_access_key)
        )

        call.enqueue(object : Callback<UnsplashImage> {
            override fun onResponse(response: Response<UnsplashImage>?, retrofit: Retrofit?) {
                loadingFinished()
                if (response != null && response.isSuccess) {
                    val image: UnsplashImage? = response.body()
                    if (image != null) {
                        onSuccess(image)
                    } else {
                        notifier.notify("Something went wrong loading image", context)
                        onFail()
                    }
                } else {
                    notifier.notify("Bad request", context)
                    onFail()
                }
            }

            override fun onFailure(t: Throwable?) {
                notifier.notify("Can't get image", context)
                onFail()
                loadingFinished()            }
        })
    }
}
