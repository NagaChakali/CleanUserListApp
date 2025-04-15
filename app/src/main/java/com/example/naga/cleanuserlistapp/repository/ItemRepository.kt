package com.example.naga.cleanuserlistapp.repository

import com.example.naga.cleanuserlistapp.data.model.Item
import com.example.naga.cleanuserlistapp.data.remote.RetrofitInstance

class ItemRepository {

    suspend fun getItems(): List<Item> {
        return try {
            // Use RetrofitInstance.apiService to make network requests
            RetrofitInstance.apiService.fetchItems()
        } catch (e: Exception) {
            // Log the error and return an empty list or a default value
            emptyList()  // Returning an empty list in case of failure
        }
    }
}
