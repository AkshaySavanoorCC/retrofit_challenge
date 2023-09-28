package com.akshay.retrofit_challenge.network

import com.akshay.retrofit_challenge.network.api.Api
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Api.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }
    fun <T> create(service : Class<T>): T{
        return retrofit.create(service)
    }
}