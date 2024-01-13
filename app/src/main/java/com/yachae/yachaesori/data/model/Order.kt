package com.yachae.yachaesori.data.model

data class Order(
    var orderId: String? = null,
    // 주문 완료 상태로 전송
    // Long -> String 변경
    var status: Long,
    //주문자 아이디
    var userId: String,
    // 주문일시
    var orderDate: String,
    val place: String,
    val postcode: String,
    var address: String,
    var name: String,
    var phone: String,
    var msg: String,
    // productUid를 통해 받아온다면 안넘겨줘도 되는 데이터?
    var itemList: List<OrderItem> = listOf(),
    var totalPrice: Long,




)

