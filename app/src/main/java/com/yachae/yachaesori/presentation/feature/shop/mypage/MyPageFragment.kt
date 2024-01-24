package com.yachae.yachaesori.presentation.feature.shop.mypage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.yachae.yachaesori.R
import com.yachae.yachaesori.databinding.FragmentMyPageBinding

class MyPageFragment : Fragment() {
    private var _binding: FragmentMyPageBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        Log.d("Frag", "MyPageFragment onCreateView")
        _binding = FragmentMyPageBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setLogOutButton()
        setToOrderListFrag()
    }

    private fun setToOrderListFrag() {
        binding.cardViewShipping.setOnClickListener { findNavController().navigate(R.id.action_shopFragment_to_orderListFragment) }
        binding.cardViewOrderComplete.setOnClickListener { findNavController().navigate(R.id.action_shopFragment_to_orderListFragment) }
        binding.cardViewDeliveryCompletedCount.setOnClickListener { findNavController().navigate(R.id.action_shopFragment_to_orderListFragment) }
    }

    private fun setLogOutButton() {
        binding.buttonLogOut.setOnClickListener {
            auth.signOut()
        }

    }

}