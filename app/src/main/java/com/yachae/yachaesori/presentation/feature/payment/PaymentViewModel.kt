package com.yachae.yachaesori.presentation.feature.payment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yachae.yachaesori.data.model.SelectedItem

class PaymentViewModel : ViewModel() {
    private val _selectedItemList = MutableLiveData<List<SelectedItem>>()
    val selectedItemList: LiveData<List<SelectedItem>> = _selectedItemList

    private val _totalCount = MutableLiveData<Int>()
    val totalCount: LiveData<Int> = _totalCount

    private val _totalPrice = MutableLiveData<Long>()
    val totalPrice: LiveData<Long> = _totalPrice
    fun setSelectedItemList(selectedItem: List<SelectedItem>) {
        _selectedItemList.value = selectedItem
    }

    fun setTotalCount(totalCount: Int) {
        _totalCount.value = totalCount
    }

    fun setTotalPrice(totalPrice: Long) {
        _totalPrice.value = totalPrice
    }
}
