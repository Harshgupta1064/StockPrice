package com.example.stockprice

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class StockViewModel : ViewModel() {
    private val _stockData = MutableLiveData<StockData>()
    val stockData: LiveData<StockData> = _stockData

    fun fetchStockData(symbol: String,apiKey: String) {
        viewModelScope.launch {
            try {
                Log.d("Apikey", "$apiKey")
                val response = RetrofitClient.retrofit.create(AlphaVantageApiService::class.java)
                    .getStockData(symbol = symbol, apiKey = apiKey)

                // Log the raw response for debugging
                val rawResponse = response.raw()
                Log.d("StockViewModel", "Raw Response: ${response.body()}")

                if (response.isSuccessful) {
                    response.body()?.let {
                        _stockData.postValue(it)
                    } ?: Log.e("StockViewModel", "Response body is null")
                } else {
                    Log.e("StockViewModel", "Error: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("StockViewModel", "Exception: ${e.message}")
            }
        }

    }
}
