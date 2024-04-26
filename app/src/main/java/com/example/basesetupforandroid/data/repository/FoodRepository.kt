package com.example.basesetupforandroid.data.repository

import com.example.basesetupforandroid.util.storage.db.dao.FoodDao
import com.example.basesetupforandroid.util.storage.db.entity.Food
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FoodRepository(private val foodDao: FoodDao) {
    fun getAllData() = foodDao.getAllFood()

    suspend fun insertFood(food: Food) =
        withContext(Dispatchers.IO) {
            foodDao.insert(food)
        }
}