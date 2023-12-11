package com.yachae.yachaesori.data

data class Product(
    val productId: String,
    val imageUrl: String = "",
    val name: String,
    val price: String,
)