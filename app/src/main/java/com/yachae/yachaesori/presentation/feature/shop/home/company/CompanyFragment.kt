package com.yachae.yachaesori.presentation.feature.shop.home.company

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.yachae.yachaesori.R
import com.yachae.yachaesori.databinding.FragmentCompanyBinding
import com.yachae.yachaesori.presentation.feature.shop.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CompanyFragment : Fragment() {
    private lateinit var binding: FragmentCompanyBinding

    private val viewModel: HomeViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCompanyBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.introImageDownloadUri.observe(viewLifecycleOwner) { uri ->
            Log.d("imageurl CompanyFragment", "가져온 setImageUrl: $uri")
            setIntroImageView(uri)
        }


    }

    private fun setIntroImageView(uri: Uri) {
        Glide.with(this)
            .load(uri)
            .placeholder(R.drawable.yachae_logo)
            .into(binding.imageView)
    }

}