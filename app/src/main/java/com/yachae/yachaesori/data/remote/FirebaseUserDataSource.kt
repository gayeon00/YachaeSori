package com.yachae.yachaesori.data.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import com.yachae.yachaesori.util.Result

// Firebase 관련 작업을 수행하는 클래스
class FirebaseUserDataSource {

    private val firebaseAuth = FirebaseAuth.getInstance()

    // 사용자 로그인
    suspend fun signInWithCustomToken(token: String): Result<FirebaseUser?> {
        return try {
            val result = firebaseAuth.signInWithCustomToken(token).await()
            Result.Success(result.user)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    // 사용자 로그아웃
    suspend fun signOut() {
        firebaseAuth.signOut()
    }

    // 현재 로그인한 사용자 가져오기
    fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }


}