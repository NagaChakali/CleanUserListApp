
package com.example.naga.cleanuserlistapp.data.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.naga.cleanuserlistapp.R
import com.example.naga.cleanuserlistapp.data.ui.adapter.ItemAdapter
import com.example.naga.cleanuserlistapp.data.ui.viewmodel.ItemViewModel
import com.google.android.material.progressindicator.CircularProgressIndicator

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: ItemViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: CircularProgressIndicator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enable edge-to-edge display for a more immersive experience
        enableEdgeToEdge()

        // Set the content view to the activity_main layout
        setContentView(R.layout.activity_main)

        // Initialize the RecyclerView
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize the loading indicator
        progressBar = findViewById(R.id.progress_circular)

        // Initialize the ViewModel
        viewModel = ViewModelProvider(this)[ItemViewModel::class.java]

        // Observe the grouped items LiveData from ViewModel
        viewModel.items.observe(this) { groupedItems ->
            progressBar.visibility = View.GONE // Hide loading when data is loaded
            recyclerView.adapter = ItemAdapter(groupedItems)
        }

        // Observe the loading state
        viewModel.loading.observe(this) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }



        // Observe error LiveData for any errors
        viewModel.error.observe(this) { errorMessage ->
            progressBar.visibility = View.GONE
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
        }

            // Handle window insets for edge-to-edge content
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.recyclerView)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
