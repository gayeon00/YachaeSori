package com.yachae.yachaesori.presentation.feature.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.yachae.yachaesori.data.model.Order
import com.yachae.yachaesori.data.model.OrderItem
import com.yachae.yachaesori.data.model.Product
import com.yachae.yachaesori.databinding.FragmentOrderListBinding

class OrderListFragment : Fragment() {
    private val adapter = OrderListAdapter()
    private var _binding: FragmentOrderListBinding? = null
    private val binding get() = _binding!!

    private val orderViewModel: OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderListBinding.inflate(layoutInflater)

        handleBackPress()

        binding.recyclerViewOrderList.adapter = adapter
        orderViewModel.orderList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setNaviIcon()

    }

    private fun handleBackPress() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun setNaviIcon() {
        binding.toolbarOrderList.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }
}