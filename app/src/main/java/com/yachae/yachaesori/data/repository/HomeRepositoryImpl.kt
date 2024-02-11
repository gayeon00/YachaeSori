package com.yachae.yachaesori.data.repository

import android.net.Uri
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.yachae.yachaesori.domain.repository.HomeRepository
import kotlinx.coroutines.tasks.await

class HomeRepositoryImpl : HomeRepository {
    override suspend fun loadIntroImageDownloadUri(): Uri {
        val storageReference = Firebase.storage.reference.child("image/yachae_intro.jpg")

        return storageReference.downloadUrl.await()
    }

    override suspend fun loadGuideImageDownloadUri(): Uri? {
        val storageReference = Firebase.storage.reference.child("image/yachae_guide.jpg")

        return storageReference.downloadUrl.await()
    }

}
