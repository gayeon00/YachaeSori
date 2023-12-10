package com.yachae.yachaesori.data.remote

import android.util.Log
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import com.yachae.yachaesori.util.Result
import kotlinx.coroutines.tasks.await

class FirebaseFunctionsDataSource {

    private val firebaseFunctions = Firebase.functions("asia-northeast3")
    suspend fun callKakaoCustomAuth(accessToken: String): Result<String> {
        return try {
            // Firebase Functions 호출
            val data = mapOf("token" to accessToken)
            val taskResult = firebaseFunctions
                .getHttpsCallable("kakaoCustomAuth")
                .call(data)
                .await()

            // 호출 성공
            val result = taskResult.data as HashMap<*, *>
            var mKey: String? = null
            for (key in result.keys) {
                mKey = key.toString()
            }
            val customToken = result[mKey!!].toString()

            Result.Success(customToken)
        } catch (e: Exception) {
            Log.e("카카오", e.toString())
            // 호출 실패
            Result.Error(e)
        }
    }
}