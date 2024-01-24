package com.yachae.yachaesori

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
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

        // Auth 상태를 관찰하고 그에 따라 화면 전환
        mainViewModel.currentUser.observe(this) { user ->
            if (user == null) {
                Log.d("MainActivity User", "로그인 안돼있음")
                navController.navigate(R.id.action_shopFragment_to_signInFragment)
            } else {
                Log.d("MainActivity User", user.toString())
                showSnackBar("로그인에 성공하였습니다.")
                if(navController.currentDestination!!.id == R.id.signInFragment) {
                    navController.navigate(R.id.action_signInFragment_to_shopFragment)
                }
            }
        }

        // AuthStateListener 추가
        mainViewModel.addAuthStateListener()

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