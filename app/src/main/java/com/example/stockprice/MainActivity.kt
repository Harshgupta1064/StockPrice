package com.example.stockprice

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

    // Declare variables for API key, ViewModel, and stock symbol
    private lateinit var apiKey: String
    private lateinit var stockViewModel: StockViewModel
    private var stSymbol: String = "" // Stores the current stock symbol entered by the user

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize API key and UI components
        apiKey = getString(R.string.API_KEY)
        val searchView = findViewById<SearchView>(R.id.searchBar)
        val btnSearch: Button = findViewById(R.id.searchButton)
        val tvStockName: TextView = findViewById(R.id.stockName)
        val tvpriceChange: TextView = findViewById(R.id.priceChange)
        val tvPrice: TextView = findViewById(R.id.currentPrice)
        val tvPercentChange: TextView = findViewById(R.id.percentChange)
        val tvSymbol: TextView = findViewById(R.id.stockSymbol)

        // Initialize ViewModel to manage stock data
        stockViewModel = ViewModelProvider(this).get(StockViewModel::class.java)

        // Observe stock data from the ViewModel
        stockViewModel.stockData.observe(this, Observer { stockData ->
            // Update UI with the stock data if available
            stockData?.let {
                displayStockData(it, tvpriceChange, tvPrice, tvPercentChange, tvSymbol)
            } ?: run {
                Toast.makeText(this, "No stock data available", Toast.LENGTH_SHORT).show()
            }
        })
        // Observe stock name from ViewModel
        stockViewModel.stockName.observe(this, Observer { stockName ->
            tvStockName.text = stockName ?: "Unknown Company"
        })

        // Observe error messages from the ViewModel
        stockViewModel.errorMessage.observe(this, Observer { error ->
            // Display error messages if any
            error?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        })

        // Set up the search functionality for the SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    stSymbol = it.trim() // Update stSymbol with the search query
                    Toast.makeText(this@MainActivity, "Searching for: $stSymbol", Toast.LENGTH_SHORT).show()
                    searchForStock() // Trigger stock search
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Update stock symbol as the user types
                stSymbol = newText?.trim() ?: ""
                return true
            }
        })

        // Handle button click for manual search
        btnSearch.setOnClickListener {
            searchForStock() // Trigger stock search
        }
    }

    // Function to initiate stock search based on the entered symbol
    private fun searchForStock() {
        if (stSymbol.isNotEmpty()) {
            // Log the search action for debugging
            Log.d("MainActivity", "Searching for: $stSymbol with API Key: $apiKey")
            stockViewModel.fetchStockData(stSymbol.uppercase(), apiKey) // Fetch stock data from ViewModel
        } else {
            Toast.makeText(this, "Please enter a stock symbol", Toast.LENGTH_SHORT).show() // Show warning if no symbol is entered
        }
    }

    // UI update method to display stock data
    private fun displayStockData(stockData: StockData, tvPriceChange: TextView, tvPrice: TextView, tvPercentChange: TextView, tvSymbol: TextView) {
        tvPriceChange.text = "Stock Price: ${stockData.currentPrice}"
        tvPriceChange.visibility = TextView.VISIBLE // Make stock price visible
        tvPrice.text = "Price Change: ${stockData.change}"
        tvPrice.visibility = TextView.VISIBLE // Make price change visible
        tvPercentChange.text = "Percent Change: ${stockData.percentChange}%"
        tvPercentChange.visibility = TextView.VISIBLE // Make percent change visible
        tvSymbol.text = "Symbol: ${stSymbol.uppercase()}"
        tvSymbol.visibility = TextView.VISIBLE // Make stock symbol visible
    }
}
