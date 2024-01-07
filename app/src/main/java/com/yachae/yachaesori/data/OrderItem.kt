package com.yachae.yachaesori.data

// OrderItem 클래스 (주문 항목 정보)
data class OrderItem(
    val product: Product,
    val selectedOption: String,
    val quantity: Int,
    val status: Long,
)
