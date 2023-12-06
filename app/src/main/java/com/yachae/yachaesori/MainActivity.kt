package com.yachae.yachaesori

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.kakao.sdk.common.util.Utility
import com.yachae.yachaesori.databinding.ActivityMainBinding
import com.yachae.yachaesori.presentation.feature.shop.ShopFragment
import com.yachae.yachaesori.presentation.feature.signin.SignInFragment
import dagger.hilt.android.AndroidEntryPoint


class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        if (isLoggedIn()) {
            showShopFragment()
        } else {
            showSignInFragment()
        }

    }

    private fun isLoggedIn(): Boolean {
        // 여기에서 실제로 로그인 상태를 확인하는 로직을 구현합니다.
        return false // 예시로 항상 false를 반환하도록 설정
    }

    private fun showSignInFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, SignInFragment())
            .commit()
    }

    private fun showShopFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, ShopFragment())
            .commit()
    }

    fun showSnackBar(str: CharSequence) {
        Snackbar.make(activityMainBinding.root, str, Snackbar.LENGTH_SHORT).show()
    }


}