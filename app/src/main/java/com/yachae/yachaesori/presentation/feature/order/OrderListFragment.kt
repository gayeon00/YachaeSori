package com.yachae.yachaesori.presentation.feature.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
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

    private val product = Product("test1", "", "", "test1", 1000, listOf("1", "2", "3", "4"))

    private val itemList = mutableListOf(
        OrderItem(product, "1", 10, 0),
        OrderItem(product, "1", 10, 0),
        OrderItem(product, "1", 10, 0),
        OrderItem(product, "1", 10, 0),
    )

    private val orderList = mutableListOf<Order>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderListBinding.inflate(layoutInflater)

        handleBackPress()

        binding.recyclerViewOrderList.adapter = adapter
        adapter.submitList(orderList)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTabs()
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

    private fun setTabs() {
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab) {

                when (tab.position) {
//                    0 -> {
//                        orderViewModel.getOrderByUserUid(
//                            Firebase.auth.currentUser!!.uid
////            Firebase.auth.currentUser!!.uid
//                        )
//                    }
//
//                    1 -> {
//                        orderViewModel.getOrderByState(Firebase.auth.currentUser!!.uid, 1)
//
//                    }
//
//                    2 -> {
//                        orderViewModel.getOrderByState(Firebase.auth.currentUser!!.uid, 3)
//                    }
//
//                    3 -> {
//                        orderViewModel.getOrderByState(Firebase.auth.currentUser!!.uid, 4)
//                    }
//
//                    4 -> {
//                        orderViewModel.getOrderByState(Firebase.auth.currentUser!!.uid, 5, 6, 7)
//                    }
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
                // Handle tab reselect
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                // Handle tab unselect
            }
        })
    }
}