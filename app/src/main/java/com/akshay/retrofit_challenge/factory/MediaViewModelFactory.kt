package com.akshay.retrofit_challenge.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.akshay.retrofit_challenge.repository.MainRepository
import com.akshay.retrofit_challenge.ui.viewmodels.MultimediaViewModel

class MediaViewModelFactory(private val repository: MainRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MultimediaViewModel::class.java)){
            return MultimediaViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown View model class")
    }
}