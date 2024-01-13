package com.yachae.yachaesori.data.model

// OrderItem 클래스 (주문 항목 정보)
data class OrderItem(
    val product: Product = Product(),
    val selectedOption: String = "",
    val quantity: Int = 0,
    val status: Long = OrderState.COMPLETE.code,
)
