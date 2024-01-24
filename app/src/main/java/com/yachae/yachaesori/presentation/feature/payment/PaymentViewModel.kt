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

    val place = MutableLiveData<String>()
    val postcode = MutableLiveData<String>()
    val address = MutableLiveData<String>()
    val detailAddress = MutableLiveData<String>()
    val name = MutableLiveData<String>()
    val phone = MutableLiveData<String>()
    val msg = MutableLiveData<String>()

    fun isInfoValid(): Boolean {
        return isDeliveryNameValid() && isPostalCodeValid() && isAddressValid() && isDetailAddressValid() && isRecipientValid() && isMobileValid()
    }

    private fun isDeliveryNameValid(): Boolean {
        return !place.value.isNullOrBlank()
    }

    private fun isPostalCodeValid(): Boolean {
        return postcode.value?.let {
            it.isNotBlank() && it.matches("^\\d{5}$".toRegex())
        } ?: false
    }

    private fun isAddressValid(): Boolean {
        return !address.value.isNullOrBlank()
    }

    private fun isDetailAddressValid(): Boolean {
        return !detailAddress.value.isNullOrBlank()
    }

    private fun isRecipientValid(): Boolean {
        return !name.value.isNullOrBlank()
    }

    private fun isMobileValid(): Boolean {
        return phone.value?.let {
            it.isNotBlank() && it.matches("^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$".toRegex())
        } ?: false
    }

    private val _orderKey = MutableLiveData<String>()
    val orderKey: LiveData<String> get() = _orderKey

    val combinedInfo = MediatorLiveData<String>()

    init {
        combinedInfo.addSource(place) { updateCombinedInfo() }
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

    fun setAddress(add: String) {
        viewModelScope.launch {
            address.value = add
        }

    }

    fun setDetailAddress(detailAddress: String) {
        this.detailAddress.value = detailAddress
    }

    fun setPostcode(pc: String) {
        viewModelScope.launch {
            postcode.value = pc
        }
    }

    fun setName(name: String) {
        this.name.value = name
    }

    fun setPhone(phone: String) {
        this.phone.value = phone
    }

    fun setPlace(place: String) {
        this.place.value = place
    }

    fun setMsg(msg: String) {
        this.msg.value = msg
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
            OrderItem(it.product, it.selectedOption, it.quantity, 1)
        }

        viewModelScope.launch {
            val orderKey = orderRepository.placeOrder(
                Order(
                    null,
                    1,
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
