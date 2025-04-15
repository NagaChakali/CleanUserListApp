package com.example.naga.cleanuserlistapp.data.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.naga.cleanuserlistapp.data.model.Item

/**
 * Sealed class to represent different item types for the RecyclerView.
 */
sealed class DisplayItem {
    data class Header(val listId: Int) : DisplayItem()
    data class Entry(val name: String) : DisplayItem()
}

/**
 * RecyclerView Adapter to display items grouped by listId.
 *
 * @param groupedItems A map of items grouped by their listId.
 */
class ItemAdapter(groupedItems: Map<Int, List<Item>>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val displayList: List<DisplayItem> = groupedItems
        .flatMap { (listId, items) ->
            // Flatten the grouped items with headers and entries
            listOf(DisplayItem.Header(listId)) + items.mapNotNull { it.name?.let(DisplayItem::Entry) }
        }

    companion object {
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_ITEM = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (displayList[position]) {
            is DisplayItem.Header -> VIEW_TYPE_HEADER
            is DisplayItem.Entry -> VIEW_TYPE_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_HEADER -> HeaderViewHolder(inflater.inflate(android.R.layout.simple_list_item_1, parent, false))
            VIEW_TYPE_ITEM -> ItemViewHolder(inflater.inflate(android.R.layout.simple_list_item_1, parent, false))
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemCount(): Int = displayList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = displayList[position]) {
            is DisplayItem.Header -> (holder as HeaderViewHolder).bind(item)
            is DisplayItem.Entry -> (holder as ItemViewHolder).bind(item)
        }
    }

    /**
     * ViewHolder for displaying a header (listId).
     */
    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val textView: TextView = view.findViewById(android.R.id.text1)

        fun bind(header: DisplayItem.Header) {
            textView.text = "List ID: ${header.listId}"
            textView.setTypeface(null, android.graphics.Typeface.BOLD)
        }
    }

    /**
     * ViewHolder for displaying an item entry (name).
     */
    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val textView: TextView = view.findViewById(android.R.id.text1)

        fun bind(entry: DisplayItem.Entry) {
            textView.text = entry.name
        }
    }
}
