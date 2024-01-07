package com.yachae.yachaesori.data

data class Product(
    val productId: String,
    val mainImageUrl: String = "",
    val detailImageUrl: String = "",
    val name: String,
    val price: String,
    val options: List<String>,
)