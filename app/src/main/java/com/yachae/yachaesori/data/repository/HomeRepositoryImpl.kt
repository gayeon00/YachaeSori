package com.yachae.yachaesori.data.repository

import com.google.firebase.database.FirebaseDatabase
import com.yachae.yachaesori.domain.repository.HomeRepository
import kotlinx.coroutines.tasks.await

class HomeRepositoryImpl : HomeRepository {
    private val database = FirebaseDatabase.getInstance()

    private val databasePath = "yachae"
    override suspend fun loadIntroImageUrl(): String {
        val databaseRef = database.getReference(databasePath).child("introImageUrl")
        val snapshot = databaseRef.get().await()

        return snapshot.getValue(String::class.java)!!
    }

    override suspend fun loadGuideImageUrl(): String? {
        val databaseRef = database.getReference(databasePath).child("guideImageUrl")
        val snapshot = databaseRef.get().await()

        return snapshot.getValue(String::class.java)!!
    }

}
