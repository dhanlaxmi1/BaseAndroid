package com.example.basesetupforandroid.util.storage.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.basesetupforandroid.util.storage.db.dao.FoodDao
import com.example.basesetupforandroid.util.storage.db.dao.PhotoDao
import com.example.basesetupforandroid.util.storage.db.entity.Food
import com.example.basesetupforandroid.util.storage.db.entity.Photo

@Database(entities = [Food::class, Photo::class], version = AppDatabase.VERSION)
abstract class AppDatabase : RoomDatabase() {

    abstract fun foodDao(): FoodDao
    abstract fun photoDao(): PhotoDao

    companion object {
        const val DB_NAME = "BaseApp.db"
        const val VERSION = 2
    }
}