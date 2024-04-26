package com.example.basesetupforandroid.di

import com.example.basesetupforandroid.data.remote.ApiService
import com.example.basesetupforandroid.data.remote.ApiService2
import com.example.basesetupforandroid.data.remote.BaseUrlPaging
import com.example.basesetupforandroid.data.remote.BaseUrlPhoto
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

// AppModule.kt
@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @BaseUrlPaging
    @Provides
    @Singleton
    fun provideBaseUrlPaging(): String {
        return "https://dummyapi.io/"
    }

    @BaseUrlPhoto
    @Provides
    @Singleton
    fun provideBaseUrlPhoto(): String {
        return "https://jsonplaceholder.typicode.com/"
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder().addInterceptor(loggingInterceptor)
            .connectTimeout(240, TimeUnit.SECONDS)
            .readTimeout(240, TimeUnit.SECONDS)
            .writeTimeout(240, TimeUnit.SECONDS).retryOnConnectionFailure(true)
            .build()
    }

    @Provides
    @Singleton
    @BaseUrlPhoto
    fun provideRetrofit(okHttpClient: OkHttpClient, @BaseUrlPhoto url: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @BaseUrlPaging
    fun provideRetrofit2(okHttpClient: OkHttpClient, @BaseUrlPaging url: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(@BaseUrlPhoto  retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideApiServicePaging(@BaseUrlPaging  retrofit: Retrofit): ApiService2 {
        return retrofit.create(ApiService2::class.java)
    }

}
