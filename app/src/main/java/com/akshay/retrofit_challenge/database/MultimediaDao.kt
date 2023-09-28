package com.akshay.retrofit_challenge.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.akshay.retrofit_challenge.model.MultiMediaModel

@Dao
interface MultimediaDao {

    @Upsert
    fun addMediaData(media:MultiMediaModel)

    @Query("SELECT * FROM media_data")
    fun getAllData() : LiveData<List<MultiMediaModel>>

    @Query("SELECT * FROM media_data WHERE id=:itemId")
    suspend fun getMediaById(itemId:String) : MultiMediaModel
}