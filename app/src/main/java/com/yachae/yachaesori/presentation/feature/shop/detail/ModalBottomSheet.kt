package com.yachae.yachaesori.presentation.feature.shop.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.yachae.yachaesori.R
import com.yachae.yachaesori.databinding.ModalBottomSheetContentBinding

class ModalBottomSheet : BottomSheetDialogFragment() {
    private var _binding: ModalBottomSheetContentBinding? = null
    private val binding = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val spinner = Spinner(context).apply {
            //크기, 높이 조절하기
        }
        binding!!.layoutOptions.addView(spinner)

        _binding = ModalBottomSheetContentBinding.inflate(layoutInflater)
        return binding.root
    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }
}
