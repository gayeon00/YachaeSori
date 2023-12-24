package com.yachae.yachaesori.presentation.feature.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.yachae.yachaesori.MainActivity
import com.yachae.yachaesori.R
import com.yachae.yachaesori.databinding.ModalBottomSheetContentBinding
import com.yachae.yachaesori.presentation.feature.payment.PaymentFragment

class ModalBottomSheet : BottomSheetDialogFragment() {
    private var _binding: ModalBottomSheetContentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ModalBottomSheetContentBinding.inflate(layoutInflater)

        addSpinner()
        setPurchaseButton()

        return binding.root
    }

    private fun setPurchaseButton() {
        binding.btnOptionPayment.setOnClickListener {
            //결제 창으로 넘어가기
            (requireActivity() as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host, PaymentFragment()).commit()
        }
    }

    private fun addSpinner() {
        //spinner programmatically 추가하기
        val spinner = Spinner(requireContext())
        // Create an ArrayAdapter using the string array and a default spinner layout.
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.planets_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears.
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner.
            spinner.adapter = adapter
            spinner.layoutParams = ViewGroup.LayoutParams(
                LayoutParams.MATCH_PARENT,
                160
            )
            spinner.setBackgroundResource(R.drawable.spinner_background)
        }

        binding.layoutOptions.addView(spinner)
    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }
}