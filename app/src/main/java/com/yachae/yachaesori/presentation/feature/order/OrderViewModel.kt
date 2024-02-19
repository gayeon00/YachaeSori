package com.yachae.yachaesori.presentation.feature.order

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yachae.yachaesori.data.model.Order
import com.yachae.yachaesori.domain.repository.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val orderRepository: OrderRepository
): ViewModel() {
    private var _order = MutableLiveData<Order>()
    val order: LiveData<Order> = _order

    private var _orderList = MutableLiveData<List<Order>>()
    val orderList: LiveData<List<Order>> = _orderList

    init {
        fetchOrderList()
    }
    private fun setOrder(order: Order) {
        _order.value = order
    }

    fun fetchOrder(id: String){
        viewModelScope.launch {
            val order = orderRepository.fetchOrder(id)
            order.orderId = id

            Log.d("OrderViewModel", order.toString())

            withContext(Dispatchers.Main) {
                setOrder(order)
            }

        }
    }

    fun fetchOrderList() {
        viewModelScope.launch {
            _orderList.value = orderRepository.fetchOrderList()
        }
    }
}