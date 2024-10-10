package com.example.stockprice

data class Stock(
    private val stockName: String,
    private val stockPrice: Int,
    private val stockSymbol: String,
    private val priceChange: String,
    private val changeDirection: Int
)
