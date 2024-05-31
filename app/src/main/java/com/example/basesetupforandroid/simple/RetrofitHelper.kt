package com.ai.live.hd.practical

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitHelper {

    private val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    private val okHttpClient = OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build()


    val apiiNterface: APIINterface by lazy {
        val retrofit =
            Retrofit.Builder().baseUrl("https://jsonplaceholder.typicode.com/").client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create()) // Add this line
                .build()
        retrofit.create(APIINterface::class.java)
    }
}