package com.yachae.yachaesori.presentation.feature.shop

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yachae.yachaesori.R
import com.yachae.yachaesori.databinding.FragmentShopBinding
import com.yachae.yachaesori.presentation.feature.shop.home.HomeFragment
import com.yachae.yachaesori.presentation.feature.shop.mypage.MyPageFragment

class ShopFragment : Fragment() {
    private lateinit var fragmentShopBinding: FragmentShopBinding

    private var homeFragment: HomeFragment? = null
    private var myPageFragment: MyPageFragment? = null

    private var currentFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "shop oncreate")
        fragmentShopBinding = FragmentShopBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        Log.d(TAG, "shop onCreateView")
        initBottomNavigation()
        return fragmentShopBinding.root
    }

    private fun initBottomNavigation() {
        //상태에 따라 보여줄 화면 설정
        when(fragmentShopBinding.bottomNavigationShop.selectedItemId) {
            R.id.item_home -> {
                showFragment(getOrCreateHomeFragment())
            }
            R.id.item_mypage-> {
                showFragment(getOrCreateMyPageFragment())
            }
        }

        //클릭할 때에 따라 보여줄 화면 설정
        fragmentShopBinding.bottomNavigationShop.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.item_home -> {
                    showFragment(getOrCreateHomeFragment())
                }
                R.id.item_mypage -> {
                    showFragment(getOrCreateMyPageFragment())
                }
            }
            true
        }

        // 초기화면 설정
//        fragmentShopBinding.bottomNavigationShop.selectedItemId = R.id.item_home
    }

    private fun showFragment(fragmentToShow: Fragment) {
        if (currentFragment != null) {
            childFragmentManager.beginTransaction().hide(currentFragment!!).commit()
        }

        childFragmentManager.beginTransaction().show(fragmentToShow).commit()
        currentFragment = fragmentToShow
    }

    private fun getOrCreateHomeFragment(): HomeFragment {
        if (homeFragment == null) {
            homeFragment = HomeFragment()
            childFragmentManager.beginTransaction()
                .add(R.id.fragment_container_view_shop, homeFragment!!).commit()
        }
        return homeFragment!!
    }

    private fun getOrCreateMyPageFragment(): MyPageFragment {
        if (myPageFragment == null) {
            myPageFragment = MyPageFragment()
            childFragmentManager.beginTransaction()
                .add(R.id.fragment_container_view_shop, myPageFragment!!).commit()
        }
        return myPageFragment!!
    }

    companion object {
        const val TAG = "ShopFragment"
    }

}