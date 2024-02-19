package com.yachae.yachaesori.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.yachae.yachaesori.data.model.Order
import com.yachae.yachaesori.domain.repository.OrderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

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

    override suspend fun fetchOrderList(): List<Order> = suspendCoroutine { continuation ->
        val currentUser = FirebaseAuth.getInstance().currentUser
        val userId = currentUser?.uid

        if (userId != null) {
            val databaseRef = FirebaseDatabase.getInstance().reference.child("orders")
            val query = databaseRef.orderByChild("userId").equalTo(userId)

            query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val orderList = mutableListOf<Order>()

                    for (orderSnapshot in snapshot.children) {
                        val order = orderSnapshot.getValue(Order::class.java)
                        //orderId로 key넣어주기
                        order?.orderId = orderSnapshot.key
                        order?.let { orderList.add(it) }
                    }

                    // 데이터를 가져왔으면 결과를 반환
                    continuation.resume(orderList)
                }

                override fun onCancelled(error: DatabaseError) {
                    // 에러가 발생했을 때 결과를 반환
                    continuation.resumeWithException(error.toException())
                }
            })
        } else {
            // 현재 사용자가 없을 경우 빈 리스트 반환
            continuation.resume(emptyList())
        }
    }

}

class OrderNotFoundException(message: String) : Exception(message)
class OrderFetchException(message: String, cause: Throwable? = null) : Exception(message, cause)
class OrderPlacementException(message: String, cause: Throwable? = null) : Exception(message, cause)