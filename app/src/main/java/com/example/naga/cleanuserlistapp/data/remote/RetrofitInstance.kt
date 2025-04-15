package com.example.naga.cleanuserlistapp.data.remote

import com.example.naga.cleanuserlistapp.data.network.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Singleton object for creating and providing the Retrofit instance.
 */
object RetrofitInstance {

    private const val BASE_URL = "https://fetch-hiring.s3.amazonaws.com/"

    // Retrofit instance, lazy-initialized
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // API service, lazy-initialized
    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
