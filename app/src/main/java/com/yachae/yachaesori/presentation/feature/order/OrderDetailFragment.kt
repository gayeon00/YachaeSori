package com.yachae.yachaesori.presentation.feature.order

import android.os.Bundle
import android.telephony.PhoneNumberUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.yachae.yachaesori.data.model.Order
import com.yachae.yachaesori.data.model.OrderItem
import com.yachae.yachaesori.databinding.FragmentOrderDetailBinding
import com.yachae.yachaesori.presentation.feature.payment.ItemListAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class OrderDetailFragment : Fragment() {
    private val orderDetailViewModel: OrderDetailViewModel by activityViewModels()
    private val adapter = ItemListAdapter(false)
    private var _binding: FragmentOrderDetailBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        orderDetailViewModel.order.observe(viewLifecycleOwner) {
            setOrderItems(it.itemList)
            setUi(it)

        }

        setNaviIcon()
    }

    private fun setUi(order: Order) {
        binding.run {
            textViewOrderDetailNum.text = "Id.${order.orderId}"
            textViewOrderDetailDate.text = order.orderDate
            if (order.status == 0L) {
                tvDetailPayDue.text = nextDay(order.orderDate)
                layoutPayWait.visibility = View.VISIBLE
                tvPayPrice.text = formatMoney(order.totalPrice)
            }
            tvDetailReceiver.text = order.name
            tvDetailContact.text = PhoneNumberUtils.formatNumber(order.phone, Locale.getDefault().getCountry())
            tvAddress.text = order.address
            tvDetailMsg.text = order.msg

            tvDetailTotalPrice.text = formatMoney(order.totalPrice - 3000L)
            if(order.status == 0L) {
                tvDetailTotalPaid.text = formatMoney(0L)
            } else {
                tvDetailTotalPaid.text = formatMoney(order.totalPrice)
            }
        }

    }

    private fun formatMoney(money: Long): String {
        return DecimalFormat("#,###").format(money)+"원"
    }

    private fun nextDay(str: String):String{
        // SimpleDateFormat을 사용하여 날짜를 파싱
        val dateFormat = SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.getDefault())
        val date = dateFormat.parse(str)

        // 하루를 더한 날짜를 구함
        val nextDay = addDays(date!!, 1)

        // 다시 SimpleDateFormat을 사용하여 원하는 형식으로 포맷팅
        val resultFormat = SimpleDateFormat("MM.dd(EEE)", Locale.getDefault())
        return resultFormat.format(nextDay)+"까지 입금을 완료해주세요."
    }

    private fun addDays(date: Date, days: Int): Date {
        val calendar = java.util.Calendar.getInstance()
        calendar.time = date
        calendar.add(java.util.Calendar.DAY_OF_YEAR, days)
        return calendar.time
    }

    private fun setOrderItems(itemList: List<OrderItem>) {
        adapter.submitList(itemList)
        binding.detailOrderItemList.adapter = adapter
    }


    private fun setNaviIcon() {
        binding.toolbarOrderDetail.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

}