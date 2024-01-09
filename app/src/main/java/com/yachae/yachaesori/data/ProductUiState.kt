package com.yachae.yachaesori.data

data class ProductUiState (
    val productId: String = "",
    val mainImageUrl: String = "",
    val detailImageUrl: String = "",
    val name: String = "",
    val price: Long = 0L,
    val options: List<String> = listOf(),
)