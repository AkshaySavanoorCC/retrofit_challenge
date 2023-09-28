package com.akshay.retrofit_challenge.network.services

import com.akshay.retrofit_challenge.model.MultiMediaModel
import com.akshay.retrofit_challenge.network.api.Api
import retrofit2.Call
import retrofit2.http.GET

interface ApiServices {
    @GET(Api.END_POINT)
    fun getMultimediaData(): Call<List<MultiMediaModel>>
}