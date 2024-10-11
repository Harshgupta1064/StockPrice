package com.example.stockprice

import androidx.core.content.ContextCompat.getString
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private val apiKey: String = R.string.API_KEY.toString()
    private var BASE_URL = "https://finnhub.io/api/v1/"

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
