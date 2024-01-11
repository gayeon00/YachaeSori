package com.yachae.yachaesori.data.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Product(
    val productId: String = "",
    val mainImageUrl: String = "",
    val detailImageUrl: String = "",
    val name: String = "",
    val price: Long = 0L,
    val options: List<String> = listOf(),
)