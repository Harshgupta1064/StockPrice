package com.example.stockprice

import com.google.gson.annotations.SerializedName

data class StockData(
    @SerializedName("c")
    val currentPrice: Float,

    @SerializedName("d")
    val change: Float,

    @SerializedName("dp")
    val percentChange: Float,

    @SerializedName("h")
    val highPriceOfDay: Float,

    @SerializedName("l")
    val lowPriceOfDay: Float,

    @SerializedName("o")
    val openPriceOfDay: Float,

    @SerializedName("pc")
    val previousClosePrice: Float
)



