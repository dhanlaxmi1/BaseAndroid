package com.example.basesetupforandroid.data.model

import com.google.gson.annotations.SerializedName

data class PictureResponse(

	@field:SerializedName("PictureResponse")
	val pictureResponse: ArrayList<PictureResponseItem>? = null
)

data class PictureResponseItem(

	@field:SerializedName("albumId")
	val albumId: Int? = null,

 	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("thumbnailUrl")
	val thumbnailUrl: String? = null,

	var isSelected: Boolean = false
)

