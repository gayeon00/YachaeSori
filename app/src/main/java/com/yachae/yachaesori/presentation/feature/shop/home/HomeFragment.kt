package com.yachae.yachaesori.presentation.feature.shop.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.yachae.yachaesori.R
import com.yachae.yachaesori.databinding.FragmentHomeBinding
import com.yachae.yachaesori.presentation.feature.shop.home.company.CompanyFragment
import com.yachae.yachaesori.presentation.feature.shop.home.guide.GuideFragment
import com.yachae.yachaesori.presentation.feature.shop.home.product.ProductListFragment
import com.zhpan.bannerview.BannerViewPager

class HomeFragment : Fragment() {
    private var _fragmentHomeBinding: FragmentHomeBinding? = null
    private val fragmentHomeBinding get() = _fragmentHomeBinding!!

    private lateinit var bannerAdapter: HomeBannerAdapter

    private lateinit var homeTabsAdapter: HomeTabsAdapter
    private lateinit var tabViewPager: ViewPager2


    //TODO: viewmodel에서 banner 데이터 observe해서 viewpager refresh해주기
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

        tabViewPager = fragmentHomeBinding.viewPagerHome

        //탭 뷰페이저 설정
        homeTabsAdapter = HomeTabsAdapter(this)
        tabViewPager.adapter = homeTabsAdapter

        //배너 설정
        val bannerViewPager = fragmentHomeBinding.bannerView as BannerViewPager<BannerBean>
        bannerViewPager.apply {
            adapter = bannerAdapter
            registerLifecycleObserver(lifecycle)
        }.create()

        bannerViewPager.refreshData(bannerList)

        //탭 설정
        fragmentHomeBinding.tabLayoutHome.run {
            //viewpager와 연결
            TabLayoutMediator(this, tabViewPager) { tab, position ->
                tab.text = HomeTabs.entries[position].title
            }.attach()

            addOnTabSelectedListener(object :
                TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    tabViewPager.currentItem = tab?.position ?: 0
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {

                }

                override fun onTabReselected(tab: TabLayout.Tab?) {

                }
            })
        }


    }

}

class HomeTabsAdapter(private val fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = HomeTabs.entries.size

    override fun createFragment(position: Int): Fragment {
        val tag = "f$position"
        val existingFragment = fragment.childFragmentManager.findFragmentByTag(tag)
        // Return a NEW fragment instance in createFragment(int).
        return existingFragment ?: createNewFragment(position)

    }

    private fun createNewFragment(position: Int): Fragment {
        val newFragment: Fragment = when (HomeTabs.entries[position]) {
            HomeTabs.PRODUCT -> ProductListFragment()
            HomeTabs.GUIDE -> GuideFragment()
            HomeTabs.COMPANY -> CompanyFragment()
        }

        return newFragment
    }
}

enum class HomeTabs(val title: String) {
    PRODUCT("상품"),
    GUIDE("손질법"),
    COMPANY("회사소개")
}




