package com.example.basesetupforandroid.data.remote

import com.example.basesetupforandroid.util.ApiErrorHandling
import retrofit2.HttpException
import retrofit2.Response

suspend fun <T : Any> safeApiCall(apiCall: suspend () -> Response<T>): Resource<T> {
    return try {
        val response = apiCall.invoke()
        if (response.isSuccessful) {
            val body = response.body()
            body?.let { return Resource.Success(body) } ?: return Resource.Error("Something went wrong")
        } else {
//            Resource.Error(response.message())
            val errorMessage = ApiErrorHandling.error(
                HttpException(response),
                isCancelled = false
            )
            Resource.Error(errorMessage)
        }
    } catch (e: Exception) {
        val errorMessage = ApiErrorHandling.error(e, isCancelled = false)
        Resource.Error(errorMessage)
    }
}
