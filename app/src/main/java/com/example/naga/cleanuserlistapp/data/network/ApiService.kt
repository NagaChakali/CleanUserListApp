package com.example.naga.cleanuserlistapp.data.network

import com.example.naga.cleanuserlistapp.data.model.Item
import retrofit2.http.GET

/**
 * Retrofit service interface for fetching items from the remote API.
 */
interface ApiService {

    /**
     * Retrieves a list of items from the "hiring.json" endpoint.
     *
     * @return A list of [Item] objects.
     */
    @GET("hiring.json")
    suspend fun fetchItems(): List<Item>
}
