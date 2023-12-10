package com.yachae.yachaesori.domain.repository

import com.google.firebase.auth.FirebaseUser
import com.yachae.yachaesori.util.Result

interface UserAuthRepository {
    suspend fun signInWithYachae(accessToken: String): Result<FirebaseUser?>

}