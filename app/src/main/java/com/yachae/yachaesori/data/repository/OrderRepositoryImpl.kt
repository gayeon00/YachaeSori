package com.yachae.yachaesori.data.repository

import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import com.yachae.yachaesori.data.model.Order
import com.yachae.yachaesori.domain.repository.OrderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class OrderRepositoryImpl : OrderRepository {

    private val database: FirebaseDatabase by lazy {
        FirebaseDatabase.getInstance()
    }

    override suspend fun placeOrder(order: Order):String {
        val ordersReference = database.getReference("orders")
        val newOrderReference = ordersReference.push()
        val orderKey = newOrderReference.key

        // 주문 데이터를 저장
        newOrderReference.setValue(order).await()

        return orderKey ?: throw OrderPlacementException("Failed to get order key")
    }

    override suspend fun fetchOrder(id: String): Order {
        return withContext(Dispatchers.IO) {
            try {
                val ordersReference = database.getReference("orders")
                val dataSnapshot = ordersReference.child(id).get().await()

                if (dataSnapshot.exists()) {
                    // 주문 정보가 존재할 경우, Order 객체로 매핑
                    return@withContext dataSnapshot.getValue(Order::class.java)
                        ?: throw OrderNotFoundException("Order data is null for id: $id")
                } else {
                    // 주문 정보가 존재하지 않을 경우, 예외 처리
                    throw OrderNotFoundException("Order not found for id: $id")
                }
            } catch (e: Exception) {
                // 예외 처리
                Log.e("OrderRepositoryImpl", "Error fetching order: $e")
                throw OrderFetchException("Error fetching order for id: $id", e)
            }
        }
    }
}

class OrderNotFoundException(message: String) : Exception(message)
class OrderFetchException(message: String, cause: Throwable? = null) : Exception(message, cause)
class OrderPlacementException(message: String, cause: Throwable? = null) : Exception(message, cause)