package com.yachae.yachaesori.data.repository

import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import com.yachae.yachaesori.data.model.Order
import com.yachae.yachaesori.domain.repository.OrderRepository
import kotlinx.coroutines.tasks.await

class OrderRepositoryImpl : OrderRepository {

    private val database: FirebaseDatabase by lazy {
        FirebaseDatabase.getInstance()
    }

    override suspend fun placeOrder(order: Order) {
        try {
            // 'orders' 레퍼런스에 주문 정보 저장
            val ordersReference = database.getReference("orders")
            val newOrderReference = ordersReference.push()
            newOrderReference.setValue(order).await()
        } catch (e: Exception) {
            // 예외 처리
            Log.e("OrderRepositoryImpl", "Error saving order: $e")
        }
    }
}