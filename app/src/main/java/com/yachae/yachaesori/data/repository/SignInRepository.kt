package com.yachae.yachaesori.data.repository

import android.util.Log
import com.google.android.gms.tasks.RuntimeExecutionException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class SignInRepository {

    private val auth = FirebaseAuth.getInstance()
    suspend fun signInWithYachae(accessToken: String): AuthResult? {
        Log.d("카카오", "커스텀 토큰 받아오기")
        val functions: FirebaseFunctions = Firebase.functions("asia-northeast3")

        val data = hashMapOf(
            "token" to accessToken
        )

        var authResult: AuthResult? = null

        functions
            .getHttpsCallable("kakaoCustomAuth")
            .call(data)
            .await().also { taskResult ->
                try {
                    // 호출 성공
                    val result = taskResult.data as HashMap<*, *>
                    Log.d("카카오", "호출 성공")
                    var mKey: String? = null
                    for (key in result.keys) {
                        mKey = key.toString()
                    }
                    val customToken = result[mKey!!].toString()

                    // 호출 성공해서 반환받은 커스텀 토큰으로 Firebase Authentication 인증받기
                    authResult = firebaseAuthWithKakao(customToken)
                } catch (e: RuntimeExecutionException) {
                    // 호출 실패
                    Log.d("카카오", "호출 실패")
                    Log.d("카카오", e.message!!)
                }
            }

        return authResult
    }

    private suspend fun firebaseAuthWithKakao(customToken: String): AuthResult? {
        Log.d("카카오", "Firebase authentication 받아오기")
        return auth.signInWithCustomToken(customToken).await()
    }
}