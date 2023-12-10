package com.yachae.yachaesori.presentation.feature.shop

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yachae.yachaesori.R
import com.yachae.yachaesori.databinding.FragmentShopBinding
import com.yachae.yachaesori.presentation.feature.shop.home.HomeFragment
import com.yachae.yachaesori.presentation.feature.shop.mypage.MyPageFragment
import com.yachae.yachaesori.presentation.feature.signin.SignInFragment

class ShopFragment : Fragment() {
    private lateinit var fragmentShopBinding: FragmentShopBinding

    private var homeFragment: HomeFragment? = null
    private var myPageFragment: MyPageFragment? = null

    private var homeState = true
    private var myPageState = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentShopBinding = FragmentShopBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        initBottomNavigation()
        return fragmentShopBinding.root
    }

    private fun initBottomNavigation() {
        homeFragment = HomeFragment()
        childFragmentManager.beginTransaction().replace(R.id.fragment_container_view_shop,homeFragment!!).commit()

        fragmentShopBinding.bottomNavigationShop.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.item_home -> {
                    if(homeFragment == null) {
                        homeFragment = HomeFragment()
                        childFragmentManager.beginTransaction().add(R.id.fragment_container_view_shop, homeFragment!!).commit()
                    }

                    if(homeFragment!=null) childFragmentManager.beginTransaction().show(homeFragment!!).commit()
                    if(myPageFragment!=null) childFragmentManager.beginTransaction().hide(myPageFragment!!).commit()
                    homeState = true
                    myPageState = false

                    return@setOnItemSelectedListener true
                }

                R.id.item_mypage -> {
                    if(myPageFragment == null) {
                        myPageFragment = MyPageFragment()
                        childFragmentManager.beginTransaction().add(R.id.fragment_container_view_shop, myPageFragment!!).commit()
                    }

                    if(homeFragment!=null) childFragmentManager.beginTransaction().hide(homeFragment!!).commit()
                    if(myPageFragment!=null) childFragmentManager.beginTransaction().show(myPageFragment!!).commit()
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