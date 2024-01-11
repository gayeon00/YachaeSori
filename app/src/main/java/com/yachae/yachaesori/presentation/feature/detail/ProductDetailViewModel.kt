package com.yachae.yachaesori.presentation.feature.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yachae.yachaesori.data.model.Product

class ProductDetailViewModel : ViewModel() {
    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product> = _product

//    private val _selectedOptions = MutableLiveData<List<Pair<Int, Int>>>()
//    val selectedOptions: LiveData<List<Pair<Int, Int>>> = _selectedOptions
//
//    fun setOptions(options: List<Pair<Int,Int>>) {
//        _selectedOptions.value = options
//    }

    fun setProduct(product: Product) {
        _product.value = product
    }
}