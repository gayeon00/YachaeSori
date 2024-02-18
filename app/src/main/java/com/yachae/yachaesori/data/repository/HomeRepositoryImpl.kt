package com.yachae.yachaesori.data.repository

import android.net.Uri
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.yachae.yachaesori.domain.repository.HomeRepository
import com.yachae.yachaesori.data.model.Banner
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class HomeRepositoryImpl : HomeRepository {
    override suspend fun loadIntroImageDownloadUri(): Uri {
        val storageReference = Firebase.storage.reference.child("image/yachae_intro.jpg")

        return storageReference.downloadUrl.await()
    }

    override suspend fun loadGuideImageDownloadUri(): Uri? {
        val storageReference = Firebase.storage.reference.child("image/yachae_guide.jpg")

        return storageReference.downloadUrl.await()
    }

    override suspend fun loadBanners(): List<Banner>? = suspendCoroutine { continuation ->
        val databaseRef = FirebaseDatabase.getInstance().reference.child("banners")

        // ValueEventListener를 사용하여 데이터를 가져오기
        databaseRef.orderByChild("order").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val bannerList = mutableListOf<Banner>()

                for (childSnapshot in snapshot.children) {
                    val banner = childSnapshot.getValue(Banner::class.java)
                    banner?.let { bannerList.add(it) }
                }

                // 데이터를 가져왔으면 결과를 반환
                continuation.resume(bannerList)
            }

            override fun onCancelled(error: DatabaseError) {
                // 에러가 발생했을 때 결과를 반환
                continuation.resumeWithException(error.toException())
            }
        })
    }

}
