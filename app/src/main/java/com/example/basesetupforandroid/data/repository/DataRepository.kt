package com.example.basesetupforandroid.data.repository

import com.example.basesetupforandroid.data.remote.Resource
import com.example.basesetupforandroid.data.model.PictureResponseItem
import com.example.basesetupforandroid.data.remote.ApiService
import com.example.basesetupforandroid.data.remote.safeApiCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DataRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun fetchData(): Flow<Resource<ArrayList<PictureResponseItem>>> {
        return flow {
            emit(Resource.Loading)
            emit(safeApiCall { apiService.fetchData() })
        }.flowOn(Dispatchers.IO)
    }
}
