package com.example.stockprice

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.ProgressBar
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
        val tvPriceChange: TextView = findViewById(R.id.priceChange)
        val tvPrice: TextView = findViewById(R.id.currentPrice)
        val tvPercentChange: TextView = findViewById(R.id.percentChange)
        val tvSymbol: TextView = findViewById(R.id.stockSymbol)
        val progressBar: ProgressBar = findViewById(R.id.progressBar) // Progress bar for loading state

        // Initialize ViewModel to manage stock data
        stockViewModel = ViewModelProvider(this).get(StockViewModel::class.java)

        // Observe the stock name from ViewModel and update UI
        stockViewModel.stockName.observe(this, Observer { stockName ->
            progressBar.visibility = ProgressBar.GONE // Hide progress bar when data is received
            if (stockName.isNullOrEmpty()) {
                // If stock name is empty, show a message indicating invalid symbol
                Toast.makeText(this, "Invalid stock symbol, no company found!", Toast.LENGTH_SHORT).show()
            } else {
                // Display the stock name
                tvStockName.text = "Stock Name : $stockName"
                tvStockName.visibility = TextView.VISIBLE
            }
        })

        // Observe stock data from the ViewModel and update the UI accordingly
        stockViewModel.stockData.observe(this, Observer { stockData ->
            progressBar.visibility = ProgressBar.GONE // Hide progress bar after data is loaded

            // Check if stock data or its key fields are null
            if (stockData == null || stockData.currentPrice == null || stockData.currentPrice.toDouble() == 0.0 ||
                stockData.change == null || stockData.percentChange == null) {
                Toast.makeText(this, "Invalid stock symbol, no data available!", Toast.LENGTH_SHORT).show()
            } else {
                // Update the UI with valid stock data
                displayStockData(stockData, tvPriceChange, tvPrice, tvPercentChange, tvSymbol)
            }
        })

        // Observe error messages from the ViewModel and display them as a Toast
        stockViewModel.errorMessage.observe(this, Observer { error ->
            error?.let {
                progressBar.visibility = ProgressBar.GONE // Hide progress bar if there's an error
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        })

        // Set up search functionality using the SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    stSymbol = it.trim() // Store the submitted stock symbol
                    hideKeyboard() // Hide the keyboard after submitting the query
                    progressBar.visibility = ProgressBar.VISIBLE // Show progress bar while loading
                    searchForStock() // Trigger stock search
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                stSymbol = newText?.trim() ?: "" // Update stock symbol as the user types
                return true
            }
        })

        // Handle manual search on button click
        btnSearch.setOnClickListener {
            hideKeyboard() // Hide the keyboard when the search button is clicked
            progressBar.visibility = ProgressBar.VISIBLE // Show progress bar while loading
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
            // Show a warning if no stock symbol is entered
            Toast.makeText(this, "Please enter a stock symbol", Toast.LENGTH_SHORT).show()
        }
    }

    // UI update method to display stock data
    private fun displayStockData(stockData: StockData, tvPriceChange: TextView, tvPrice: TextView, tvPercentChange: TextView, tvSymbol: TextView) {
        tvPrice.text = "Price Change: ${stockData.change ?: "N/A"}" // Update price change
        tvPrice.visibility = TextView.VISIBLE
        tvPriceChange.text = "Stock Price: ${stockData.currentPrice ?: "N/A"} USD" // Update stock price
        tvPriceChange.visibility = TextView.VISIBLE
        tvPercentChange.text = "Percent Change: ${stockData.percentChange ?: "N/A"}%" // Update percent change
        tvPercentChange.visibility = TextView.VISIBLE
        tvSymbol.text = "Symbol: ${stSymbol.uppercase()}" // Update stock symbol
        tvSymbol.visibility = TextView.VISIBLE
    }

    // Function to hide the soft keyboard
    private fun hideKeyboard() {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0) // Hide keyboard
    }
}
