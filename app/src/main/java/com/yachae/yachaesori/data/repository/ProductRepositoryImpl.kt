package com.yachae.yachaesori.data.repository

import android.util.Log
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.yachae.yachaesori.data.model.Product
import com.yachae.yachaesori.domain.repository.ProductRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor() : ProductRepository {
    override suspend fun getProducts(): List<Product> {
        val productList = mutableListOf<Product>()

        try {
            val snapshot = Firebase.database.reference.child("products").get().await()
            for (productSnapshot in snapshot.children) {
                val product = productSnapshot.getValue(Product::class.java)
                if (product != null) {
                    productList.add(product)
                }
            }
        } catch (e: Exception) {
            // 예외 처리
            Log.e("ProductRepositoryImpl", "Error fetching products: $e")
        }

        return productList
    }

    override suspend fun getImageUrl(storagePath: String): String? {
        return try {
            val storageRef = Firebase.storage.reference.child(storagePath)
            val downloadUrl = storageRef.downloadUrl.await()
            downloadUrl.toString()
        } catch (e: Exception) {
            // 예외 처리
            Log.e("FirebaseProductRepository", "Error getting image URL: $e")
            null
        }
    }
}