package com.example.stockprice

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class StockViewModel : ViewModel() {
    // LiveData to hold stock data (nullable)
    private val _stockData = MutableLiveData<StockData?>()
    val stockData: LiveData<StockData?> = _stockData

    private val _stockName = MutableLiveData<String?>() // Stock name LiveData
    val stockName: LiveData<String?> = _stockName

    // LiveData to hold error messages (nullable)
    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    // Function to fetch stock data from the API
    fun fetchStockData(symbol: String, apiKey: String) {
        viewModelScope.launch {
            try {
                Log.d("ApiKey", "$apiKey") // Log the API key for debugging

                // Make the API call to fetch stock data
                val response = RetrofitClient.retrofit.create(FinhubApiInterface::class.java)
                    .getStockData(symbol = symbol, apiKey = apiKey)

                // Check if the response was successful
                if (response.isSuccessful) {
                    response.body()?.let {
                        _stockData.postValue(it) // Post the stock data to LiveData
                        _errorMessage.postValue(null) // Clear any previous error messages
                    } ?: run {
                        Log.e("StockViewModel", "Response body is null")
                        _errorMessage.postValue("Invalid symbol or no data found!") // Set error message for null body
                    }
                } else {
                    Log.e("StockViewModel", "Error: ${response.errorBody()?.string()}")
                    _errorMessage.postValue("Invalid symbol or no data found!") // Set error message for unsuccessful response
                }
                // Handling for the Stock Name
                val responseProfile = RetrofitClient.retrofit.create(FinhubApiInterface::class.java)
                    .getStockName(symbol = symbol, apiKey = apiKey)

                Log.d("StockViewModel", "Profile Response: ${responseProfile.body()}")

                if (responseProfile.isSuccessful) {
                    responseProfile.body()?.let {
                        _stockName.postValue(it.name) // Post stock name
                    } ?: run {
                        Log.e("StockViewModel", "Response body is null for profile")
                        _stockName.postValue("Unknown Company") // Fallback for unknown name
                    }
                } else {
                    Log.e("StockViewModel", "Profile Error: ${responseProfile.errorBody()?.string()}")
                    _stockName.postValue("Unknown Company")
                }
            } catch (e: Exception) {
                Log.e("StockViewModel", "Exception: ${e.message}")
                _errorMessage.postValue("Network error, please try again!") // Set error message for network issues
            }
        }
    }
}
