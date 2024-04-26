package com.example.basesetupforandroid.util.storage.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.basesetupforandroid.util.storage.db.entity.Photo
import kotlinx.coroutines.flow.Flow

@Dao
interface PhotoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(pictures: List<Photo>)

    @Query("SELECT * FROM photos")
    fun getAllPhoto() : Flow<MutableList<Photo>>
}