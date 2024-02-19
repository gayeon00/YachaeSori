package com.yachae.yachaesori.presentation.feature.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.yachae.yachaesori.R
import com.yachae.yachaesori.data.model.Product
import com.yachae.yachaesori.data.model.SelectedItem
import com.yachae.yachaesori.databinding.ItemSelectedOptionBinding
import com.yachae.yachaesori.databinding.ModalBottomSheetContentBinding
import com.yachae.yachaesori.presentation.feature.payment.PaymentViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat

@AndroidEntryPoint
class OptionBottomSheet(
) : BottomSheetDialogFragment() {

    private var _binding: ModalBottomSheetContentBinding? = null
    private val binding get() = _binding!!
    private val productDetailViewModel: ProductDetailViewModel by activityViewModels()
    private val paymentViewModel: PaymentViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ModalBottomSheetContentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSelectedOptions()
        setTotalCount()
        setTotalPrice()
        setPurchaseButton()
        setOptionDropdown(productDetailViewModel.product.value!!)
    }

    private fun setTotalPrice() {
        paymentViewModel.totalPrice.observe(viewLifecycleOwner) {
            binding.tvProductTotalPrice.text =
                "${DecimalFormat("#,###").format(it)}원"
        }
    }

    private fun setTotalCount() {
        paymentViewModel.totalCount.observe(viewLifecycleOwner) {
            binding.tvProductTotalCount.text =
                "상품 ${it}개"
        }

    }

    private fun setSelectedOptions() {
        paymentViewModel.selectedItemList.observe(viewLifecycleOwner) { list ->
            binding.layoutSelectedOptions.removeAllViews()

            for (item in list) {
                val selectedOption = ItemSelectedOptionBinding.inflate(layoutInflater)

                selectedOption.run {
                    tvSelectedOption.text = item.selectedOption

                    tvProductCount.text = item.quantity.toString()
                    tvProductPrice.text = "${
                        DecimalFormat("#,###").format(
                            item.product.price * item.quantity
                        )
                    }원"


                    //수량추가
                    btnPlus.setOnClickListener {
                        item.quantity++
                        //list중 item의 quantity를 증가시켜서 다시 할당해주기
                        paymentViewModel.setSelectedItemList(list.toList())
                        paymentViewModel.setTotalCount(paymentViewModel.totalCount.value!! + 1)
                        paymentViewModel.setTotalPrice(paymentViewModel.totalPrice.value!! + item.product.price)
                    }
                    //수량빼기
                    btnMinus.setOnClickListener {
                        if (item.quantity > 1) {
                            item.quantity--
                            paymentViewModel.setSelectedItemList(list.toList())
                            paymentViewModel.setTotalCount(paymentViewModel.totalCount.value!! - 1)
                            paymentViewModel.setTotalPrice(paymentViewModel.totalPrice.value!! - item.product.price)
                        }
                    }
                    //선택취소
                    btnRemove.setOnClickListener {
                        val tmp = list.toMutableList()
                        tmp.remove(item)

                        paymentViewModel.setSelectedItemList(tmp)
                        paymentViewModel.setTotalCount(paymentViewModel.totalCount.value!! - item.quantity)
                        paymentViewModel.setTotalPrice(paymentViewModel.totalPrice.value!! - item.product.price * item.quantity)
                    }
                }

                binding.layoutSelectedOptions.addView(selectedOption.root)
            }
        }
    }


    private fun setPurchaseButton() {
        //TODO: 여기 다시 보기 -> viewmodel분리하기
        paymentViewModel.selectedItemList.observe(viewLifecycleOwner) {
            binding.btnOptionPayment.isEnabled = it.isNotEmpty()
        }

        binding.btnOptionPayment.setOnClickListener {
            val navController = findNavController()
            navController.navigate(R.id.action_productDetailFragment_to_paymentFragment)

        }
    }

    private fun setOptionDropdown(product: Product) {

        Log.d(TAG, product.options.toString())
        (binding.autoCompleteTextView as MaterialAutoCompleteTextView).setSimpleItems(product.options.toTypedArray())
        (binding.autoCompleteTextView as MaterialAutoCompleteTextView).setOnItemClickListener { _, _, position, _ ->

            val fnd =
                paymentViewModel.selectedItemList.value!!.find { it.selectedOption == product.options[position] }
            //이미 선택된 옵션이라면 -> 기존의 옵션에 수량 추가하기
            if (fnd != null) {
                fnd.quantity++;
            } else {
                val tmp = paymentViewModel.selectedItemList.value!!.toMutableList()
                tmp.add(SelectedItem(product, product.options[position], 1))

                paymentViewModel.setSelectedItemList(tmp)
                paymentViewModel.setTotalCount(paymentViewModel.totalCount.value!! + 1)
                paymentViewModel.setTotalPrice(paymentViewModel.totalPrice.value!! + product.price)
            }
        }
    }

    companion object {
        const val TAG = "OptionBottomSheet"
    }
}
