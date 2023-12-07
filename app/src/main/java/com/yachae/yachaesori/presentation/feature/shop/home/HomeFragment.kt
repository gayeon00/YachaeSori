package com.yachae.yachaesori.presentation.feature.shop.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yachae.yachaesori.R
import com.yachae.yachaesori.databinding.FragmentHomeBinding
import com.zhpan.bannerview.BannerViewPager

class HomeFragment : Fragment() {
    private var _fragmentHomeBinding: FragmentHomeBinding? = null
    private val fragmentHomeBinding get() = _fragmentHomeBinding!!

    private lateinit var bannerAdapter: HomeBannerAdapter

    //viewmodel에서 banner 데이터 observe해서 viewpager refresh해주기
    private val bannerList = mutableListOf<BannerBean>(
        BannerBean(R.drawable.banner_1),
        BannerBean(R.drawable.banner_2)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _fragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater)
        bannerAdapter = HomeBannerAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return fragmentHomeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager = fragmentHomeBinding.bannerView as BannerViewPager<BannerBean>
        viewPager.apply {
            adapter = bannerAdapter
            registerLifecycleObserver(lifecycle)
        }.create()

        viewPager.refreshData(bannerList)
    }

}




