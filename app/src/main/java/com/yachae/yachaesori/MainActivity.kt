package com.yachae.yachaesori

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.yachae.yachaesori.data.repository.SignInRepository
import com.yachae.yachaesori.databinding.ActivityMainBinding
import com.yachae.yachaesori.domain.usecase.SignInUserUseCase
import com.yachae.yachaesori.presentation.feature.shop.ShopFragment
import com.yachae.yachaesori.presentation.feature.signin.SignInFragment


class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding


    private val authViewModel: AuthViewModel by viewModels {
        AuthViewModelFactory(
            SignInUserUseCase(SignInRepository())
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)


        // Auth 상태를 관찰하고 그에 따라 화면 전환
        authViewModel.currentUser.observe(this) { user ->
            if (user == null) {
                // 사용자가 로그인되어 있지 않으면 SignInFragment를 표시
                showSignInFragment()
            } else {
                // 사용자가 로그인되어 있으면 ShopFragment를 표시
                showShopFragment()
            }
        }

        // AuthStateListener 추가
        authViewModel.addAuthStateListener()

        // 초기 Auth 상태 확인
        authViewModel.checkCurrentUser()

    }

    override fun onDestroy() {
        super.onDestroy()
        // AuthStateListener 제거 (메모리 누수 방지)
        authViewModel.removeAuthStateListener()
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