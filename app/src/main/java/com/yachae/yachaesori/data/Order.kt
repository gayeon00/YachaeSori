package com.yachae.yachaesori.data

data class Order(
    // 주문 완료 상태로 전송
    // Long -> String 변경
    var status: Long,

    // seller orderList 전달 항목
    // 데이터 자동 생성처리
    // Long -> String 변경
    // code? 에 넘겨줄 값이 없어서 일단 삭제
    // date + random 알파벳 3자리
    var orderId: String,
    var userId: String,

    // 보낼 수 있는 데이터
    var orderDate: String,
    var message: String,
    var totalPrice: Long,

    // productUid를 통해 받아온다면 안넘겨줘도 되는 데이터?
    var itemList: List<OrderItem> = listOf(),

    var address: String
)

