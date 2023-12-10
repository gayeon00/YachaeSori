package com.yachae.yachaesori

import android.app.Application
import android.content.Context
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class YachaeApplication : Application() {
    private var appContext: Context? = null
    override fun onCreate() {
        super.onCreate()
        appContext = this
        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_APP_KEY)
    }
}