package com.example.basesetupforandroid.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.basesetupforandroid.vm.MainViewModel
import com.example.basesetupforandroid.R
import com.example.basesetupforandroid.util.base.BaseActivity
import com.example.basesetupforandroid.data.remote.Resource
import com.example.basesetupforandroid.data.model.PictureResponseItem
import com.example.basesetupforandroid.databinding.ActivityMainBinding
import com.example.basesetupforandroid.ui.adapter.MyAdapter
import com.example.basesetupforandroid.util.coroutineAsync
import com.example.basesetupforandroid.util.dialog.Loader
import com.example.basesetupforandroid.util.loge
import com.example.basesetupforandroid.util.storage.db.dao.FoodDao
import com.example.basesetupforandroid.util.storage.db.dao.PhotoDao
import com.example.basesetupforandroid.util.storage.db.entity.Food
import com.example.basesetupforandroid.util.storage.db.entity.Photo
import com.example.basesetupforandroid.util.storage.pref.Preference
import com.example.basesetupforandroid.util.toJson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {



    private val adapter: MyAdapter by lazy { MyAdapter() }

    @Inject
    lateinit var preference: Preference

    @Inject
    lateinit var photoDao: PhotoDao

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       addFoodFromDb()

        viewModel.foodList.observe(this) { foodList ->
            foodList.toJson().loge("===>F")
            // Update UI with the new food list
        }

        viewModel.photoList.observe(this){ photoList ->
            photoList.toJson().loge("===>F!@#")

        }

        viewModel.data.observe(this) { resource ->
            when (resource) {
                is Resource.Loading -> showLoading()
                is Resource.Success -> showData(resource.data)
                is Resource.Error -> showError(resource.message)
            }
        }
        viewModel.fetchData()
    }

    private fun addFoodFromDb() {
        val newFood = Food(name = "Apple", category = "Fruit", calories = 95)
            viewModel.addFoodExample(newFood)
    }

    override fun getLayoutResourceId() = R.layout.activity_main

    override fun clickListeners() {
    }

    private fun showLoading() {
        Loader.show(this)
    }

    private fun showError(message: String) {
        Loader.hide()
    }

    private fun showData(pictureList: ArrayList<PictureResponseItem>) {
        pictureList.toJson().loge("===>B")
        val pictureEntityList = pictureList.map {
            Photo(
                albumId = it.albumId ?: 0,
                isSelected = it.isSelected,
                thumbnailUrl = it.thumbnailUrl ?: "",
                title = it.title ?: "",
                url = it.url ?: ""
            )
        }
        viewModel.insertAllPhoto(pictureEntityList)

        Loader.hide()
        adapter.setData(pictureList)
        binding.rvPicture.adapter = adapter

        binding.ed.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                filterData(s.toString().trim(), pictureList)

            }
        })

    }

    fun filterData(query: String, pictureList: ArrayList<PictureResponseItem>) {
        val filteredList = pictureList.filter { item ->
            item.title!!.contains(query, ignoreCase = true)
        }
        adapter?.setData(filteredList)
    }
}