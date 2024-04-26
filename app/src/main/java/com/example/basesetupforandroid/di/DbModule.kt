package com.example.basesetupforandroid.di

import android.content.Context
import androidx.room.Room
import com.example.basesetupforandroid.data.repository.FoodRepository
import com.example.basesetupforandroid.data.repository.PhotoRepository
import com.example.basesetupforandroid.util.storage.db.AppDatabase
import com.example.basesetupforandroid.util.storage.db.dao.FoodDao
import com.example.basesetupforandroid.util.storage.db.dao.PhotoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.DB_NAME)
            .fallbackToDestructiveMigration()
            . allowMainThreadQueries()
            .build()
    }

    @Provides
    @Singleton
    fun provideFoodDao(database: AppDatabase): FoodDao {
        return database.foodDao()
    }

    @Provides
    fun provideFoodRepository(foodDao: FoodDao): FoodRepository {
        return FoodRepository(foodDao)
    }

    @Provides
    @Singleton
    fun providePhotoDao(database: AppDatabase): PhotoDao {
        return database.photoDao()
    }


    @Provides
    fun providePhotoRepository(photoDao: PhotoDao): PhotoRepository {
        return PhotoRepository(photoDao)
    }
}