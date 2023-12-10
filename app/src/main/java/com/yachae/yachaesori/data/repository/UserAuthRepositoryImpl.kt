package com.yachae.yachaesori.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseUser
import com.yachae.yachaesori.data.remote.FirebaseFunctionsDataSource
import com.yachae.yachaesori.data.remote.FirebaseUserDataSource
import com.yachae.yachaesori.domain.repository.UserAuthRepository
import com.yachae.yachaesori.util.Result
import javax.inject.Inject

class UserAuthRepositoryImpl @Inject constructor(
    private val firebaseUserDataSource: FirebaseUserDataSource,
    private val firebaseFunctionsDataSource: FirebaseFunctionsDataSource
) : UserAuthRepository {

    override suspend fun signInWithYachae(accessToken: String): Result<FirebaseUser?> {
        Log.i("카카오", "UserAuthRepository")
        // Firebase Functions 호출

        return when (val functionsResult = firebaseFunctionsDataSource.callKakaoCustomAuth(accessToken)) {
            is Result.Success -> {
                // Firebase Authentication 인증
                val customToken = functionsResult.data
                Log.d("카카오", "functions 성공")
                firebaseUserDataSource.signInWithCustomToken(customToken!!)
            }

            is Result.Error -> {
                Log.d("카카오", "functions 실패")
                // Handle error from FunctionsDataSource
                Result.Error(functionsResult.exception)
            }
        }
    }
}