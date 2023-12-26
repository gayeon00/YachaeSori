package com.yachae.yachaesori.presentation.feature.shop.home.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yachae.yachaesori.data.Product
import com.yachae.yachaesori.databinding.FragmentProductListBinding

class ProductListFragment : Fragment() {
    private val adapter = ProductListAdapter()
    private var _fragmentProductBinding: FragmentProductListBinding? = null
    private val fragmentProductBinding get() = _fragmentProductBinding!!

    private val productList = mutableListOf(
        Product("test1", "", "test1", "1,000원", listOf("1", "2", "3", "4")),
        Product("test1", "", "test1", "1,000원", listOf("1", "2", "3", "4")),
        Product("test1", "", "test1", "1,000원", listOf("1", "2", "3", "4")),
        Product("test1", "", "test1", "1,000원", listOf("1", "2", "3", "4")),
        Product("test1", "", "test1", "1,000원", listOf("1", "2", "3", "4")),
        Product("test1", "", "test1", "1,000원", listOf("1", "2", "3", "4")),
        Product("test1", "", "test1", "1,000원", listOf("1", "2", "3", "4"))
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentProductBinding = FragmentProductListBinding.inflate(layoutInflater)

        fragmentProductBinding.productsList.adapter = adapter
        adapter.submitList(productList)

        return fragmentProductBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}