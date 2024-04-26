package com.example.basesetupforandroid.data.remote

import com.example.basesetupforandroid.data.model.PictureResponseItem
import retrofit2.Response
import retrofit2.http.GET

interface ApiService2 {
    @GET("photos")
    suspend fun fetchData(): Response<ArrayList<PictureResponseItem>>
}