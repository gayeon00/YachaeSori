package com.yachae.yachaesori.presentation.feature.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
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

    private var totalCount = 0
    private var totalPrice = 3000L

    //선택한 옵션 번호, 수량
    private val selectedItemList = mutableListOf<SelectedItem>()
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

        productDetailViewModel.product.observe(viewLifecycleOwner) {
            setOptionDropdown(it)
            setPurchaseButton()
        }

//        paymentViewModel.selectedItemList.observe(viewLifecycleOwner) {
//            Log.d(TAG, it.toString())
//        }
    }


    private fun setPurchaseButton() {
        binding.btnOptionPayment.setOnClickListener {
            //결제 창으로 넘어가기
            //productlist 중 몇번째 product인지, 선택한 옵션들과 그 갯수는 뭔지 넘겨줘야
//            val action =
//                ProductDetailFragmentDirections.actionProductDetailFragmentToPaymentFragment2(
//                    position
//                )
            val navController = findNavController()
            navController.navigate(R.id.action_productDetailFragment_to_paymentFragment2)

        }
    }

    private fun setOptionDropdown(product: Product) {
        (binding.autoCompleteTextView as MaterialAutoCompleteTextView).setSimpleItems(product.options.toTypedArray())
        (binding.autoCompleteTextView as MaterialAutoCompleteTextView).setOnItemClickListener { _, _, position, _ ->

            val item =
                binding.layoutSelectedOptions.findViewWithTag<LinearLayout>(product.options[position])

            //새롭게 선택된 옵션이라면
            if (item == null) {
                var selectedItem = SelectedItem(product, product.options[position], 1)

                val selectedOption = ItemSelectedOptionBinding.inflate(layoutInflater)
                selectedOption.run {
                    root.tag = product.options[position]
                    tvSelectedOption.text = product.options[position]

                    setPrice(product)

                    totalCount += 1
                    totalPrice += product.price
                    binding.tvProductTotalCount.text =
                        "상품 ${totalCount}개"
                    binding.tvProductTotalPrice.text =
                        "${DecimalFormat("#,###").format(totalPrice)}원"
//                    optionList.add(Pair(position, 1))
//                    productDetailViewModel.setOptions(optionList)
                    selectedItemList.add(selectedItem)
                    paymentViewModel.setSelectedItemList(selectedItemList)
                    paymentViewModel.setTotalCount(totalCount)
                    paymentViewModel.setTotalPrice(totalPrice)


                    btnPlus.setOnClickListener {
//                        optionList.remove(Pair(position, tvProductCount.text.toString().toInt()))
//                        optionList.add(Pair(position, tvProductCount.text.toString().toInt() + 1))
//                        productDetailViewModel.setOptions(optionList)

                        selectedItemList.remove(selectedItem)
                        selectedItem.quantity++
                        selectedItemList.add(selectedItem)
                        paymentViewModel.setSelectedItemList(selectedItemList)

                        tvProductCount.text =
                            (tvProductCount.text.toString().toInt() + 1).toString()

                        totalCount += 1
                        totalPrice += product.price
                        binding.tvProductTotalCount.text =
                            "상품 ${totalCount}개"
                        binding.tvProductTotalPrice.text =
                            "${DecimalFormat("#,###").format(totalPrice)}원"
                        setPrice(product)
                        paymentViewModel.setTotalCount(totalCount)
                        paymentViewModel.setTotalPrice(totalPrice)

                    }
                    btnMinus.setOnClickListener {
                        if (tvProductCount.text.toString().toInt() > 1) {
//                            optionList.remove(
//                                Pair(
//                                    position,
//                                    tvProductCount.text.toString().toInt()
//                                )
//                            )
//                            optionList.add(
//                                Pair(
//                                    position,
//                                    tvProductCount.text.toString().toInt() - 1
//                                )
//                            )
//                            productDetailViewModel.setOptions(optionList)
                            selectedItemList.remove(selectedItem)
                            selectedItem.quantity--
                            selectedItemList.add(selectedItem)
                            paymentViewModel.setSelectedItemList(selectedItemList)

                            tvProductCount.text =
                                (tvProductCount.text.toString().toInt() - 1).toString()

                            totalCount -= 1
                            totalPrice -= product.price
                            binding.tvProductTotalCount.text =
                                "상품 ${totalCount}개"
                            binding.tvProductTotalPrice.text =
                                "${DecimalFormat("#,###").format(totalPrice)}원"
                            setPrice(product)
                            paymentViewModel.setTotalCount(totalCount)
                            paymentViewModel.setTotalPrice(totalPrice)
                        }

                    }
                    btnRemove.setOnClickListener {
//                        optionList.remove(Pair(position, tvProductCount.text.toString().toInt()))
//                        productDetailViewModel.setOptions(optionList)
                        selectedItemList.remove(selectedItem)
                        paymentViewModel.setSelectedItemList(selectedItemList)

                        binding.layoutSelectedOptions.removeView(selectedOption.root)

                        totalCount -= tvProductCount.text.toString().toInt()
                        totalPrice -= product.price * tvProductCount.text.toString().toInt()
                        binding.tvProductTotalCount.text =
                            "상품 ${totalCount}개"
                        binding.tvProductTotalPrice.text =
                            "${DecimalFormat("#,###").format(totalPrice)}원"
                        paymentViewModel.setTotalCount(totalCount)
                        paymentViewModel.setTotalPrice(totalPrice)


                    }
                }
                binding.layoutSelectedOptions.addView(selectedOption.root)
            }


        }
    }

    companion object {
        const val TAG = "OptionBottomSheet"
    }
}

private fun ItemSelectedOptionBinding.setPrice(product: Product) {
    tvProductPrice.text = "${
        DecimalFormat("#,###").format(
            product.price * tvProductCount.text.toString().toInt()
        )
    }원"


}
