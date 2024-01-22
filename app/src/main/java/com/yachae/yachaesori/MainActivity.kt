package com.yachae.yachaesori

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.snackbar.Snackbar
import com.yachae.yachaesori.databinding.ActivityMainBinding
import com.yachae.yachaesori.presentation.feature.shop.ShopFragment
import com.yachae.yachaesori.presentation.feature.signin.SignInFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding


    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        val navController = navHostFragment.navController
        val navGraph = navController.navInflater.inflate(R.navigation.nav_graph)


        // Auth 상태를 관찰하고 그에 따라 화면 전환
        mainViewModel.currentUser.observe(this) { user ->
            if (user == null) {
                Log.d("MainActivity User", "로그인 안돼있음")
                // 사용자가 로그인되어 있지 않으면 SignInFragment를 표시
                showSnackBar("로그아웃 되었습니다.")
                showSignInFragment()
            } else {
                Log.d("MainActivity User", user.toString())
                showSnackBar("로그인에 성공하였습니다.")
                showShopFragment()
            }
        }


        // AuthStateListener 추가
        mainViewModel.addAuthStateListener()

        // 초기 Auth 상태 확인
        mainViewModel.checkCurrentUser()

    }

    private fun showSignInFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host, SignInFragment())
            .commit()
    }

    private fun showShopFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host, ShopFragment())
            .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        // AuthStateListener 제거 (메모리 누수 방지)
        mainViewModel.removeAuthStateListener()
    }

    fun showSnackBar(text: String) {
        Snackbar.make(activityMainBinding.root, text, Snackbar.LENGTH_SHORT).show()
    }

}