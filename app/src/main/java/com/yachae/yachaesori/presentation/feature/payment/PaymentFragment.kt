package com.yachae.yachaesori.presentation.feature.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yachae.yachaesori.MainActivity
import com.yachae.yachaesori.R
import com.yachae.yachaesori.databinding.FragmentPaymentBinding

class PaymentFragment : Fragment() {
    private var _binding : FragmentPaymentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPaymentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.run {
            setNaviIcon()

            setPayConfirmButton()

        }
    }

    private fun setPayConfirmButton() {
        binding.paymentConfirmButton.setOnClickListener {
            //주문 완료 화면으로 넘어가기
            (requireActivity() as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host, PaymentCompleteFragment()).commit()
        }
    }

    private fun setNaviIcon() {
        binding.toolbarPayment.setNavigationOnClickListener {
            //돌아가기
        }
    }

}