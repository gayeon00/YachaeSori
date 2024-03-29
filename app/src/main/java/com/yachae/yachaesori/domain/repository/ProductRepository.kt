package com.yachae.yachaesori.domain.repository

import com.yachae.yachaesori.data.model.Product

interface ProductRepository {
    suspend fun getProducts(): List<Product>
    suspend fun getImageUrl(storagePath: String): String?
}
