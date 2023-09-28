package com.akshay.retrofit_challenge.ui.viewmodels


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akshay.retrofit_challenge.model.MultiMediaModel
import com.akshay.retrofit_challenge.network.RetrofitClient
import com.akshay.retrofit_challenge.network.services.ApiServices
import com.akshay.retrofit_challenge.repository.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MultimediaViewModel(private val repository: MainRepository) : ViewModel() {

    val allMediaFiles: LiveData<List<MultiMediaModel>> = repository.allMedia

    private val multimediaService = RetrofitClient.create(ApiServices::class.java)

    private val _multiMediaData = MutableLiveData<List<MultiMediaModel>>()
    val multiMediaData: LiveData<List<MultiMediaModel>> = _multiMediaData

    private val _currentVideo = MutableLiveData<MultiMediaModel>()
    val currentVideo  = _currentVideo
    init {
        fetchDataFromApi()

    }


    fun addDataToDb(){
        viewModelScope.launch {
            multiMediaData.observeForever {
                it.forEach { media ->
                    repository.addMediaFile(media)
                }
            }
        }
    }

    suspend fun getCurrentMedia(id : String){
        viewModelScope.launch(Dispatchers.IO) {
            val currentMedia = repository.getMediaById(id)
            withContext(Dispatchers.Main) {
                _currentVideo.value = currentMedia

            }
        }

    }

    fun updateCurrentVideo(mediaModel: MultiMediaModel){
        _currentVideo.value = mediaModel
    }


    private fun fetchDataFromApi() {
        multimediaService.getMultimediaData().enqueue(object : Callback<List<MultiMediaModel>> {
            override fun onResponse(
                call: Call<List<MultiMediaModel>>,
                response: Response<List<MultiMediaModel>>
            ) {
                if (response.isSuccessful) {
                    _multiMediaData.value = response.body()
                } else {
                    val errorMessage = "API request failed with response code: ${response.code()}"
                    println(errorMessage)
                }
            }

            override fun onFailure(call: Call<List<MultiMediaModel>>, t: Throwable) {
                val errorMessage = "Network error: ${t.message}"
                println(errorMessage)
            }
        })
    }
}

