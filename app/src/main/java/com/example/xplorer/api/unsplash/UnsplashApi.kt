package com.example.xplorer.api.unsplash

import android.content.Context
import android.widget.Toast
import com.example.xplorer.R
import retrofit.Call
import retrofit.Callback
import retrofit.GsonConverterFactory
import retrofit.Response
import retrofit.Retrofit
import javax.inject.Inject

class UnsplashServiceImpl @Inject constructor() {

    suspend fun getImage(
        context: Context,
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
            query = "nature", // Reemplazá con lo que necesites
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
                        Toast.makeText(context, "No image found", Toast.LENGTH_SHORT).show()
                        onFail()
                    }
                } else {
                    Toast.makeText(context, "Bad request", Toast.LENGTH_SHORT).show()
                    onFail()
                }
            }

            override fun onFailure(t: Throwable?) {
                Toast.makeText(context, "Can't get image", Toast.LENGTH_SHORT).show()
                onFail()
                loadingFinished()            }
        })
    }
}
