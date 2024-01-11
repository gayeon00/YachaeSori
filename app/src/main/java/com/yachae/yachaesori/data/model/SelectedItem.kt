package com.yachae.yachaesori.data.model

data class SelectedItem(
    val product: Product,
    val selectedOption: String,
    var quantity: Int
)
