package com.example.musicshop.data

data class Order(
    val customerName: String,
    val goodsName: String,
    val quantity: Int,
    val orderPrice: Int
)
