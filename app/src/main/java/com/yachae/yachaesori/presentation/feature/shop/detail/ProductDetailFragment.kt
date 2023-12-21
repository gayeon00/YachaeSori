package com.yachae.yachaesori.presentation.feature.shop.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.yachae.yachaesori.R
import com.yachae.yachaesori.databinding.FragmentProductDetailBinding

class ProductDetailFragment : Fragment() {
    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTabLayout()
        setPurchaseButton()

    }

    private fun setPurchaseButton() {
        binding.btnPayment.setOnClickListener {
            val modalBottomSheet = ModalBottomSheet()
            modalBottomSheet.show(childFragmentManager, ModalBottomSheet.TAG)
        }
    }

    private fun setTabLayout() {
        childFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view_detail, DetailImagesFragment.newInstance("", ""))
            .commit()

        binding.tabLayoutProductDetail.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> {
                        childFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container_view_detail, DetailImagesFragment.newInstance("", ""))
                            .commit()
                    }

                    1 -> {
                        childFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container_view_detail, QnAFragment.newInstance("", ""))
                            .commit()
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
    }

}