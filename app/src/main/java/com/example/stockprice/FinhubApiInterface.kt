package com.example.stockprice

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FinhubApiInterface {
    @GET("quote")
    suspend fun getStockData(
        @Query("symbol") symbol: String,
        @Query("token") apiKey: String
    ): Response<StockData>
    @GET("stock/profile2")
    suspend fun getStockName(
        @Query("symbol") symbol: String,
        @Query("token") apiKey: String
    ): Response<CompanyProfile>
}