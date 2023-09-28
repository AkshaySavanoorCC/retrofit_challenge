package com.akshay.retrofit_challenge.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "media_data")
data class MultiMediaModel(
    @PrimaryKey
    val id : String,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "thumbnail_url")
    val thumbnailUrl: String,
    @ColumnInfo(name = "video_duration")
    val duration: String,
    @ColumnInfo(name = "upload_time")
    val uploadTime :String,
    @ColumnInfo(name = "views")
    val views: String,
    @ColumnInfo(name = "author")
    val author: String,
    @ColumnInfo("video_url")
    val videoUrl: String,
    @ColumnInfo("description")
    val description: String,
    @ColumnInfo("is_live")
    val isLive: Boolean
)