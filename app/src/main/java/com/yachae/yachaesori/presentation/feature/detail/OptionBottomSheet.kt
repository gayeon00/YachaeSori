package com.yachae.yachaesori.presentation.feature.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.yachae.yachaesori.R
import com.yachae.yachaesori.data.Product
import com.yachae.yachaesori.databinding.ItemSelectedOptionBinding
import com.yachae.yachaesori.databinding.ModalBottomSheetContentBinding
import java.text.DecimalFormat

class OptionBottomSheet(
    private val fragment: Fragment,
    private val product: Product
) : BottomSheetDialogFragment() {
    private var _binding: ModalBottomSheetContentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ModalBottomSheetContentBinding.inflate(layoutInflater)

        setOptionDropdown()
        setTotalPrice()
        setPurchaseButton()

        return binding.root
    }

    private fun setTotalPrice() {
        binding.layoutSelectedOptions.get
    }

    private fun setPurchaseButton() {
        binding.btnOptionPayment.setOnClickListener {
            //결제 창으로 넘어가기
            val navController = fragment.findNavController()
            navController.navigate(R.id.action_productDetailFragment_to_paymentFragment2)
        }
    }

    private fun setOptionDropdown() {
        (binding.autoCompleteTextView as MaterialAutoCompleteTextView).setSimpleItems(product.options.toTypedArray())
        (binding.autoCompleteTextView as MaterialAutoCompleteTextView).setOnItemClickListener { _, _, position, _ ->

            val item =
                binding.layoutSelectedOptions.findViewWithTag<LinearLayout>(product.options[position])

            //새롭게 선택된 옵션이라면
            if (item == null) {
                val selectedOption = ItemSelectedOptionBinding.inflate(layoutInflater)
                selectedOption.run {
                    root.tag = product.options[position]
                    tvSelectedOption.text = product.options[position]

                    setPrice(product)


                    btnPlus.setOnClickListener {
                        tvProductCount.text =
                            (tvProductCount.text.toString().toInt() + 1).toString()
                        setPrice(product)
                    }
                    btnMinus.setOnClickListener {
                        if (tvProductCount.text.toString().toInt() > 1) {
                            tvProductCount.text =
                                (tvProductCount.text.toString().toInt() - 1).toString()
                        }
                        setPrice(product)
                    }
                    btnRemove.setOnClickListener {
                        binding.layoutSelectedOptions.removeView(selectedOption.root)
                    }
                }
                binding.layoutSelectedOptions.addView(selectedOption.root)
            }


        }
    }

    private fun setPrice() {

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
