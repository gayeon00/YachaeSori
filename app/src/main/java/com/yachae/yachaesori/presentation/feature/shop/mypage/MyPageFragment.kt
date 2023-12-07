package com.yachae.yachaesori.presentation.feature.shop.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.yachae.yachaesori.R
import com.yachae.yachaesori.databinding.FragmentMyPageBinding

class MyPageFragment : Fragment() {
    private var _fragmentMyPageBinding: FragmentMyPageBinding? = null
    private val fragmentMyPageBinding get() = _fragmentMyPageBinding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _fragmentMyPageBinding = FragmentMyPageBinding.inflate(layoutInflater)
        return fragmentMyPageBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentMyPageBinding.run {
            buttonLogOut.setOnClickListener {
                auth.signOut()
            }
        }
    }

}