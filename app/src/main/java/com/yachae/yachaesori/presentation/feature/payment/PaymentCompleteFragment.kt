package com.yachae.yachaesori.presentation.feature.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.yachae.yachaesori.R
import com.yachae.yachaesori.databinding.FragmentPaymentCompleteBinding
import com.yachae.yachaesori.presentation.feature.order.OrderViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

@AndroidEntryPoint
class PaymentCompleteFragment : Fragment() {
    private val orderViewModel: OrderViewModel by activityViewModels()
    private val paymentViewModel: PaymentViewModel by activityViewModels()
    private var _binding: FragmentPaymentCompleteBinding? = null
    private val binding get() = _binding!!

    private val adapter = SelectedItemListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPaymentCompleteBinding.inflate(layoutInflater)
        binding.orderItemList.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        paymentViewModel.orderKey.observe(viewLifecycleOwner) {
            orderViewModel.fetchOrder(it)
        }

        setPayDue()
        setShipDetail()
        setTotalPrice()
        setItemList()
        setNaviIcon()
        setPayDetailButton()
        setContinueButton()
        setItemExpandButton()
    }

    private fun setItemList() {
        paymentViewModel.selectedItemList.observe(viewLifecycleOwner) {
            binding.tvPayCompItems.text = "${it[0].product.name}외 ${it.size - 1}개"
            adapter.submitList(it)
        }
    }

    private fun setTotalPrice() {
        paymentViewModel.totalPrice.observe(viewLifecycleOwner) {
            binding.tvPayCompTotalPrice.text = DecimalFormat("#,###").format(it + 3000L) + "원"
        }
    }

    private fun setShipDetail() {
        paymentViewModel.combinedInfo.observe(viewLifecycleOwner) {
            binding.tvPayCompAddress.text = it
        }
    }

    private fun setPayDue() {
        val cal = Calendar.getInstance()
        cal.time = Date()
        val df = SimpleDateFormat("MM.dd(E)")

        cal.add(Calendar.DATE, 1)
        binding.tvPayDue.text = df.format(cal.time) + "까지 입금을 완료해주세요."
    }

    private fun setItemExpandButton() {
        binding.run {
            ivItemExpand.setOnClickListener {
                toggleListVisibility()
            }
        }
    }

    private fun toggleListVisibility() {
        if (binding.orderItemList.visibility == View.VISIBLE) {
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
            findNavController().navigate(R.id.action_paymentCompleteFragment_to_orderDetailFragment)
        }
    }

    private fun setPayDetailButton() {
        binding.btnContinueShopping.setOnClickListener {
            //쇼핑 홈으로 이동
            findNavController().popBackStack(R.id.shopFragment, false)
        }
    }

    private fun setNaviIcon() {
        binding.toolbarPayComplete.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }
}