package com.example.basesetupforandroid.util.storage.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photos")
data class Photo(
    @PrimaryKey(autoGenerate = true) val id : Int  = 0,
    @ColumnInfo(name = "albumId") val albumId: Int ,
    @ColumnInfo(name = "isSelected") val isSelected: Boolean,
    @ColumnInfo(name = "thumbnailUrl") val thumbnailUrl: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "url") val url: String
)