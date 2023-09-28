package com.akshay.retrofit_challenge.repository

import androidx.lifecycle.LiveData
import com.akshay.retrofit_challenge.database.MultimediaDao
import com.akshay.retrofit_challenge.model.MultiMediaModel

class MainRepository(private val mediaDao : MultimediaDao) {
    val allMedia : LiveData<List<MultiMediaModel>> = mediaDao.getAllData()

    suspend fun getMediaById(id: String): MultiMediaModel {
        return mediaDao.getMediaById(id)

    }


    fun addMediaFile(media : MultiMediaModel){
        mediaDao.addMediaData(media)
    }

}