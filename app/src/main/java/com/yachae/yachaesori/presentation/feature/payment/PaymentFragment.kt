package com.yachae.yachaesori.presentation.feature.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.yachae.yachaesori.R
import com.yachae.yachaesori.databinding.FragmentPaymentBinding
import com.yachae.yachaesori.util.NetworkStatus
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat

@AndroidEntryPoint
class PaymentFragment : Fragment() {
    private var _binding: FragmentPaymentBinding? = null
    private val binding get() = _binding!!
    private val paymentViewModel: PaymentViewModel by activityViewModels()
    private val adapter = SelectedItemListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPaymentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setNaviIcon()
        setPayConfirmButton()

        binding.listSelectedItem.adapter = adapter

        paymentViewModel.selectedItemList.observe(viewLifecycleOwner) {
            adapter.submitList(it)

        }

        paymentViewModel.totalPrice.observe(viewLifecycleOwner) {
            binding.paymentCheck.text = DecimalFormat("#,###").format(it) + "원"

            binding.tvTotalPrice.text = DecimalFormat("#,###").format(it + 3000) + "원"
        }
        binding.tvDeliveryPrice.text = "3,000원"

        setShipMsg()
        setFindAddressBtn()

    }

    private fun setFindAddressBtn() {
        binding.btnFindPostcode.setOnClickListener {
            val status = NetworkStatus.getConnectivityStatus(requireContext())
            if (status == NetworkStatus.TYPE_MOBILE || status == NetworkStatus.TYPE_WIFI) {

                findNavController().navigate(R.id.action_paymentFragment2_to_addressDialogFragment)
            } else {
                Snackbar.make(
                    binding.root,
                    "인터넷 연결을 확인해주세요.",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }


    private fun setShipMsg() {
        (binding.autoCompleteTextView as MaterialAutoCompleteTextView).setOnItemClickListener { adapterView, view, position, l ->
            //기타를 선택한 경우
            if (position == 7) {
                binding.editLayoutMsg.visibility = View.VISIBLE
            } else {
                binding.editLayoutMsg.visibility = View.GONE
            }
        }
    }

    private fun setPayConfirmButton() {
        binding.paymentConfirmButton.setOnClickListener {
            //주문 완료 화면으로 넘어가기
            findNavController().navigate(R.id.action_paymentFragment2_to_paymentCompleteFragment2)
        }
    }

    private fun setNaviIcon() {
        binding.toolbarPayment.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

}