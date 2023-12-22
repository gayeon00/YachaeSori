package com.yachae.yachaesori.presentation.feature.shop.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.yachae.yachaesori.R
import com.yachae.yachaesori.databinding.ModalBottomSheetContentBinding

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

        return binding.root
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
