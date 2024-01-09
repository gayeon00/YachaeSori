package com.yachae.yachaesori.presentation.feature.shop.home.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.yachae.yachaesori.databinding.FragmentProductListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductListFragment : Fragment() {
    private val adapter = ProductListAdapter()
    private var _fragmentProductBinding: FragmentProductListBinding? = null
    private val fragmentProductBinding get() = _fragmentProductBinding!!

    private val productViewModel: ProductViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentProductBinding = FragmentProductListBinding.inflate(layoutInflater)

        productViewModel.productList.observe(viewLifecycleOwner, Observer { productList ->
            // 상품 목록이 업데이트되었을 때 수행할 로직
            adapter.submitList(productList)

        })

        fragmentProductBinding.productsList.adapter = adapter


        return fragmentProductBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}