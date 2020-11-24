package com.example.getpostdeleteapp.module

import com.example.getpostdeleteapp.api.SimpleApi
import com.example.getpostdeleteapp.utils.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SimpleApiModule {

    val httpClient by lazy {
        OkHttpClient.Builder()
    }

    private val retrofit by lazy {

        Retrofit.Builder()
            .client(httpClient.build())
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            /*.addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                )
            )*/
            .build()

    }

    val api: SimpleApi by lazy {
        retrofit.create(SimpleApi::class.java)
    }
}