package com.example.stockprice

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {
    private lateinit var apiKey: String
    private lateinit var stockViewModel: StockViewModel
    private var stSymbol: String = "" // Declare stSymbol here

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        apiKey = getString(R.string.API_KEY)
        val searchView = findViewById<SearchView>(R.id.searchBar)
        val btnSearch: Button = findViewById(R.id.searchButton)
        val tvStockName: TextView = findViewById(R.id.stockName)
        val tvPrice: TextView = findViewById(R.id.currentPrice)
        val tvPercentChange: TextView = findViewById(R.id.percentChange)
        val tvSymbol: TextView = findViewById(R.id.stockSymbol)

        stockViewModel = ViewModelProvider(this).get(StockViewModel::class.java)

        // Observe stock data from the ViewModel
        stockViewModel.stockData.observe(this, Observer { stock ->
            stock?.let {
                tvStockName.text = it.currentPrice.toString()
                tvPrice.text = it.currentPrice.toString()
                tvPercentChange.text = it.percentChange.toString()
                tvSymbol.text = stSymbol
            }
        })

        // SearchView listener
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Log for debugging
                Log.d("MainActivity", "Search submitted: $query")
                if (!query.isNullOrBlank()) {
                    stSymbol = query.trim() // Update stSymbol
                    Toast.makeText(this@MainActivity, "Symbol entered: $stSymbol", Toast.LENGTH_SHORT).show()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d("MainActivity", "Search submitted: $newText")
                if (!newText.isNullOrBlank()) {
                    stSymbol = newText.trim()
                }
                return true
            }
        })

        // Button click listener
        btnSearch.setOnClickListener {
            if (stSymbol.isNotEmpty()) {
                Toast.makeText(this, "$apiKey", Toast.LENGTH_SHORT).show()
                Toast.makeText(this, "Searching for: $stSymbol", Toast.LENGTH_SHORT).show()
                stockViewModel.fetchStockData(stSymbol.uppercase(),apiKey)
            } else {
                Toast.makeText(this, "Please enter a stock symbol", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
