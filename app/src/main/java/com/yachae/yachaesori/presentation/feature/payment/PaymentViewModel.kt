package com.yachae.yachaesori.presentation.feature.payment

import android.telephony.PhoneNumberUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.yachae.yachaesori.data.model.Order
import com.yachae.yachaesori.data.model.OrderItem
import com.yachae.yachaesori.data.model.SelectedItem
import com.yachae.yachaesori.domain.repository.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val orderRepository: OrderRepository
) : ViewModel() {
    private val _selectedItemList = MutableLiveData<List<SelectedItem>>()
    val selectedItemList: LiveData<List<SelectedItem>> = _selectedItemList

    private val _totalCount = MutableLiveData<Int>()
    val totalCount: LiveData<Int> = _totalCount

    private val _totalPrice = MutableLiveData<Long>()
    val totalPrice: LiveData<Long> = _totalPrice

    private val _address = MutableLiveData<String>()
    val address: LiveData<String> = _address

    private val _postcode = MutableLiveData<String>()
    val postcode: LiveData<String> = _postcode

    private val _place = MutableLiveData<String>()
    val place: LiveData<String> = _place

    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    private val _phone = MutableLiveData<String>()
    val phone: LiveData<String> = _phone

    private val _msg = MutableLiveData<String>()
    val msg: LiveData<String> = _msg

    private val _orderKey = MutableLiveData<String>()
    val orderKey: LiveData<String> get() = _orderKey

    val combinedInfo = MediatorLiveData<String>()

    init {
        combinedInfo.addSource(name) { updateCombinedInfo() }
        combinedInfo.addSource(phone) { updateCombinedInfo() }
        combinedInfo.addSource(postcode) { updateCombinedInfo() }
        combinedInfo.addSource(address) { updateCombinedInfo() }
    }

    private fun updateCombinedInfo() {
        val formattedPhone =
            PhoneNumberUtils.formatNumber(phone.value, Locale.getDefault().getCountry())
        combinedInfo.value = "${name.value} / $formattedPhone\n[${postcode.value}] ${address.value}"
    }


    fun setSelectedItemList(selectedItem: List<SelectedItem>) {
        _selectedItemList.value = selectedItem
    }

    fun setTotalCount(totalCount: Int) {
        _totalCount.value = totalCount
    }

    fun setTotalPrice(totalPrice: Long) {
        _totalPrice.value = totalPrice
    }

    fun setAddress(address: String) {
        viewModelScope.launch {
            _address.value = address
        }

    }

    fun setPostcode(postcode: String) {
        viewModelScope.launch {
            _postcode.value = postcode
        }

    }

    fun setName(name: String) {
        _name.value = name
    }

    fun setPhone(phone: String) {
        _phone.value = phone
    }

    fun setPlace(place: String) {
        _place.value = place
    }

    fun setMsg(msg: String) {
        _msg.value = msg
    }

    fun setOrderKey(key: String) {
        _orderKey.value = key
    }

    fun saveOrder(
        place: String,
        postcode: String,
        address: String,
        name: String,
        phone: String,
        msg: String
    ) {
        val userId = Firebase.auth.currentUser!!.uid
        val sdfDate = SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.getDefault())
        val orderDate = sdfDate.format(Date(System.currentTimeMillis()))
        val orderList = selectedItemList.value!!.map {
            OrderItem(it.product, it.selectedOption, it.quantity, 0)
        }

        viewModelScope.launch {
            val orderKey = orderRepository.placeOrder(
                Order(
                    null,
                    0,
                    userId,
                    orderDate,
                    place,
                    postcode,
                    address,
                    name,
                    phone,
                    msg,
                    orderList,
                    totalPrice.value!!
                )
            )
            setOrderKey(orderKey)
        }


    }
}
