package com.yachae.yachaesori.presentation.feature.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yachae.yachaesori.MainActivity
import com.yachae.yachaesori.R
import com.yachae.yachaesori.data.OrderItem
import com.yachae.yachaesori.data.Product
import com.yachae.yachaesori.databinding.FragmentPaymentCompleteBinding
import com.yachae.yachaesori.presentation.feature.order.OrderDetailFragment
import com.yachae.yachaesori.presentation.feature.shop.ShopFragment

class PaymentCompleteFragment : Fragment() {
    private var _binding: FragmentPaymentCompleteBinding? = null
    private val binding get() = _binding!!

    private val adapter = OrderListAdapter(false)
    private val product = Product("test1", "", "test1", "1000", listOf("1", "2", "3", "4"))
    private val orderItemList = mutableListOf(
        OrderItem(product,"1",2,"입금대기"),
        OrderItem(product,"1",2,"입금대기"),
        OrderItem(product,"1",2,"입금대기"),
        OrderItem(product,"1",2,"입금대기"),
        OrderItem(product,"1",2,"입금대기"),
        OrderItem(product,"1",2,"입금대기"),
        OrderItem(product,"1",2,"입금대기")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPaymentCompleteBinding.inflate(layoutInflater)
        binding.orderItemList.adapter = adapter
        adapter.submitList(orderItemList)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setCloseButton()
        setPayDetailButton()
        setContinueButton()
        setItemExpandButton()
        setItemList()
    }

    private fun setCloseButton() {
        binding.toolbarPayComplete.setNavigationOnClickListener {
            //닫기
        }
    }

    private fun setItemList() {

    }

    private fun setItemExpandButton() {
        binding.run {
            ivItemExpand.setOnClickListener {
                toggleListVisibility()
            }
        }
    }

    private fun toggleListVisibility() {
        if(binding.orderItemList.visibility == View.VISIBLE) {
            binding.ivItemExpand.setImageResource(R.drawable.expand_more_24px)
            binding.orderItemList.visibility = View.GONE
        } else {
            binding.orderItemList.visibility = View.VISIBLE
            binding.ivItemExpand.setImageResource(R.drawable.expand_less_24px)
        }
    }

    private fun setContinueButton() {
        binding.btnToOrderDetail.setOnClickListener {
            //주문 상세정보로 이동
            (requireActivity() as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host, OrderDetailFragment()).commit()
        }
    }

    private fun setPayDetailButton() {
        binding.btnContinueShopping.setOnClickListener {
            //쇼핑 홈으로 이동
            (requireActivity() as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host, ShopFragment()).commit()
        }
    }
}