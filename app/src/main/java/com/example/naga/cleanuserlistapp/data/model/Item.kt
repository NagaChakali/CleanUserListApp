package com.example.naga.cleanuserlistapp.data.model

import com.google.gson.annotations.SerializedName

/**
 * Data class representing an item from the remote JSON API.
 *
 * @property id The unique identifier for the item.
 * @property listId The identifier representing the group this item belongs to.
 * @property name The name of the item, which can be null or blank and should be filtered out in the UI.
 */
data class Item(
    @SerializedName("id")
    val id: Int,

    @SerializedName("listId")
    val listId: Int,

    @SerializedName("name")
    val name: String?
)
