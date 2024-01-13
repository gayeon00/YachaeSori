package com.yachae.yachaesori.domain.repository

import com.yachae.yachaesori.data.model.Order

interface OrderRepository {
    suspend fun placeOrder(order: Order)
}