package com.yachae.yachaesori.presentation.feature.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yachae.yachaesori.MainActivity
import com.yachae.yachaesori.R
import com.yachae.yachaesori.databinding.FragmentPaymentCompleteBinding
import com.yachae.yachaesori.presentation.feature.shop.ShopFragment

class PaymentCompleteFragment : Fragment() {
    private var _binding: FragmentPaymentCompleteBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPaymentCompleteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setPayDetailButton()
        setContinueButton()
    }

    private fun setContinueButton() {
        binding.btnToOrderDetail.setOnClickListener {
            //주문 상세정보로 이동
            (requireActivity() as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host, ShopFragment()).commit()
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