package com.example.basesetupforandroid.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.basesetupforandroid.data.remote.Resource
import com.example.basesetupforandroid.data.model.PictureResponseItem
import com.example.basesetupforandroid.data.repository.DataRepository
import com.example.basesetupforandroid.data.repository.FoodRepository
import com.example.basesetupforandroid.data.repository.PhotoRepository
import com.example.basesetupforandroid.util.storage.db.entity.Food
import com.example.basesetupforandroid.util.storage.db.entity.Photo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dataRepository: DataRepository,
    private val foodRepository: FoodRepository,
    private val photoRepository: PhotoRepository,
) : ViewModel() {
    private val _data = MutableLiveData<Resource<ArrayList<PictureResponseItem>>>()
    val data: LiveData<Resource<ArrayList<PictureResponseItem>>> get() = _data

    val foodList: LiveData<List<Food>> = foodRepository.getAllData().asLiveData()
    val photoList: LiveData<List<Photo>> = photoRepository.getAllPhotos().asLiveData()


    fun fetchData() {
        viewModelScope.launch {
            dataRepository.fetchData().collect() {
                _data.value = it
            }
        }
    }

      fun addFoodExample(newFood: Food) {
        viewModelScope.launch {
            foodRepository.insertFood(newFood)
        }
     }

    fun insertAllPhoto(pictureEntityList: List<Photo>) {
        viewModelScope.launch {
            photoRepository.insertPhoto(pictureEntityList)
        }
    }

//    fun fetchDataAndInsertIntoDb() {
//        viewModelScope.launch {
//            try {
//                var offset = 0 // Initial offset
//                val batchSize = 100 // Batch size
//
//                while (true) {
//                    val pictures = dataRepository.fetchData(offset, batchSize) // Fetch data from the API
//                    if (pictures.isEmpty()) break // Exit loop if no more data
//
//                    val pictureEntities = pictures.map { picture ->
//                        PictureEntity(
//                            albumId = picture.albumId,
//                            title = picture.title,
//                            url = picture.url,
//                            thumbnailUrl = picture.thumbnailUrl
//                        )
//                    }
//                    pictureRepository.insertAllPictures(pictureEntities) // Insert data into the database
//
//                    offset += batchSize // Increment offset for the next batch
//                }
//            } catch (e: Exception) {
//                // Handle error
//            }
//        }
//    }
}