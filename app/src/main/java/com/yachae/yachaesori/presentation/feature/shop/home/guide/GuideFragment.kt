package com.yachae.yachaesori.presentation.feature.shop.home.guide

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.yachae.yachaesori.R
import com.yachae.yachaesori.databinding.FragmentGuideBinding
import com.yachae.yachaesori.presentation.feature.shop.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GuideFragment : Fragment() {
    private lateinit var binding: FragmentGuideBinding

    private val viewModel: HomeViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGuideBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.guideImageDownloadUri.observe(viewLifecycleOwner) {
            setGuideImageView(it)
        }
    }

    private fun setGuideImageView(uri: Uri) {
        Glide.with(this)
            .load(uri)
            .placeholder(R.drawable.yachae_logo)
            .into(binding.imageView)
    }


}