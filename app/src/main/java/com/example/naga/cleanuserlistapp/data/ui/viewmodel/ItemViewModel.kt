package com.example.naga.cleanuserlistapp.data.ui.viewmodel

import androidx.lifecycle.*
import com.example.naga.cleanuserlistapp.data.model.Item
import com.example.naga.cleanuserlistapp.repository.ItemRepository
import kotlinx.coroutines.launch

/**
 * ViewModel to manage item data.
 * It handles fetching, sorting, filtering, and grouping the data.
 */
class ItemViewModel : ViewModel() {

    private val repository = ItemRepository()

    // LiveData to observe the item list
    private val _items = MutableLiveData<Map<Int, List<Item>>>()
    val items: LiveData<Map<Int, List<Item>>> = _items

    // LiveData to observe loading state
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    // LiveData to observe error messages
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    init {
        fetchItems()
    }

    /**
     * Fetch items from the repository, handle loading, and update LiveData.
     */
    private fun fetchItems() {
        _loading.value = true
        viewModelScope.launch {
            try {
                val response = repository.getItems()

                // Filter, sort, and group items by listId
                val filteredSortedGrouped = response
                    .filter { !it.name.isNullOrBlank() }
                    .sortedWith(compareBy({ it.listId }, { it.name }))
                    .groupBy { it.listId }

                _items.value = filteredSortedGrouped
                _loading.value = false
            } catch (e: Exception) {
                _loading.value = false
                _error.value = "Failed to load data: ${e.localizedMessage}"
            }
        }
    }
}
