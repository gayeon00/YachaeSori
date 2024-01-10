package com.yachae.yachaesori.presentation.feature.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.yachae.yachaesori.R
import com.yachae.yachaesori.data.Product
import com.yachae.yachaesori.databinding.FragmentProductDetailBinding
import com.yachae.yachaesori.presentation.feature.shop.home.product.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailFragment : Fragment() {
    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!

    private val args: ProductDetailFragmentArgs by navArgs()
    private val productViewModel: ProductViewModel by viewModels()
    lateinit var product: Product

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductDetailBinding.inflate(layoutInflater)



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("ProductDetailFragment", productViewModel.productList.value.toString())

        productViewModel.productList.observe(viewLifecycleOwner, Observer {
            product = it[args.position]

            setNaviIcon()
            setTabLayout()
            setPurchaseButton()
            setMainImage()

        })
    }

    private fun setMainImage() {
        val storageReference = Firebase.storage.reference.child(product.mainImageUrl)

        Glide.with(this)
            .load(storageReference)
            .placeholder(R.drawable.ic_launcher_background)
            .centerCrop()
            .into(binding.imageViewMainImage)
    }

    private fun setNaviIcon() {
        binding.toolbarDetail.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setPurchaseButton() {
        binding.btnPayment.setOnClickListener {
            val optionBottomSheet = OptionBottomSheet(this,product)
            optionBottomSheet.show(childFragmentManager, OptionBottomSheet.TAG)
        }
    }

    private fun setTabLayout() {
        childFragmentManager.beginTransaction()
            .replace(
                R.id.fragment_container_view_detail,
                DetailImagesFragment.newInstance(product.detailImageUrl)
            )
            .commit()

        binding.tabLayoutProductDetail.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> {
                        childFragmentManager.beginTransaction()
                            .replace(
                                R.id.fragment_container_view_detail,
                                DetailImagesFragment.newInstance(product.detailImageUrl)
                            )
                            .commit()
                    }

                    1 -> {
                        childFragmentManager.beginTransaction()
                            .replace(
                                R.id.fragment_container_view_detail,
                                QnAFragment.newInstance("", "")
                            )
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