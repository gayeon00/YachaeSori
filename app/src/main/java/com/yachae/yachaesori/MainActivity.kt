package com.yachae.yachaesori

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.yachae.yachaesori.databinding.ActivityMainBinding
import com.yachae.yachaesori.feature.home.HomeFragment
import com.yachae.yachaesori.feature.mypage.MyPageFragment

class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding

    private var homeFragment: HomeFragment? = null
    private var myPageFragment: MyPageFragment? = null

    private var homeState = true
    private var myPageState = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        setSupportActionBar(activityMainBinding.materialToolbar)

        initBottomNavigation()

    }

    private fun initBottomNavigation() {
        homeFragment = HomeFragment()
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView,homeFragment!!).commit()

        activityMainBinding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.item_home -> {
                    if(homeFragment == null) {
                        homeFragment = HomeFragment()
                        supportFragmentManager.beginTransaction().add(R.id.fragmentContainerView, homeFragment!!).commit()
                    }

                    if(homeFragment!=null) supportFragmentManager.beginTransaction().show(homeFragment!!).commit()
                    if(myPageFragment!=null) supportFragmentManager.beginTransaction().hide(myPageFragment!!).commit()
                    homeState = true
                    myPageState = false

                    return@setOnItemSelectedListener true
                }

                R.id.item_mypage -> {
                    if(myPageFragment == null) {
                        myPageFragment = MyPageFragment()
                        supportFragmentManager.beginTransaction().add(R.id.fragmentContainerView, myPageFragment!!).commit()
                    }

                    if(homeFragment!=null) supportFragmentManager.beginTransaction().hide(homeFragment!!).commit()
                    if(myPageFragment!=null) supportFragmentManager.beginTransaction().show(myPageFragment!!).commit()
                    homeState = false
                    myPageState = true

                    return@setOnItemSelectedListener true
                }

                else -> {
                    return@setOnItemSelectedListener true
                }


            }
        }
    }
}