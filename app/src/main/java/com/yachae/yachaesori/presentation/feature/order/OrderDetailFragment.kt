package com.yachae.yachaesori.presentation.feature.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.yachae.yachaesori.data.OrderItem
import com.yachae.yachaesori.data.Product
import com.yachae.yachaesori.databinding.FragmentOrderDetailBinding
import com.yachae.yachaesori.presentation.feature.payment.ItemListAdapter

class OrderDetailFragment : Fragment() {
    private val adapter = ItemListAdapter(true)
    private var _binding: FragmentOrderDetailBinding? = null
    private val binding get() = _binding!!

    private val product = Product("test1", "", "", "test1", "1000", listOf("1", "2", "3", "4"))
    private val orderItemList = mutableListOf(
        OrderItem(product, "1", 2, 0),
        OrderItem(product, "1", 2, 0),
        OrderItem(product, "1", 2, 0),
        OrderItem(product, "1", 2, 0),
        OrderItem(product, "1", 2, 0),
        OrderItem(product, "1", 2, 0),
        OrderItem(product, "1", 2, 0)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderDetailBinding.inflate(layoutInflater)
        binding.detailOrderItemList.adapter = adapter
        adapter.submitList(orderItemList)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setNaviIcon()
    }

    private fun setNaviIcon() {
        binding.toolbarOrderDetail.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

}