package com.example.basesetupforandroid.util.storage.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.basesetupforandroid.util.storage.db.entity.Food
import com.example.basesetupforandroid.util.storage.db.entity.Photo
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {

    @Query("SELECT * FROM food")
    fun getAllFood(): Flow<MutableList<Food>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(food: Food)



 }