package com.example.basesetupforandroid.data.repository

import com.example.basesetupforandroid.util.storage.db.dao.PhotoDao
import com.example.basesetupforandroid.util.storage.db.entity.Photo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PhotoRepository(private val photoDao: PhotoDao) {

    fun getAllPhotos() = photoDao.getAllPhoto()

    suspend fun insertPhoto(photo: List<Photo>) = withContext(Dispatchers.IO) { photoDao.insertAll(photo) }

}





