package com.yachae.yachaesori.presentation.feature.shop.home

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yachae.yachaesori.data.model.Banner
import com.yachae.yachaesori.domain.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository
) : ViewModel() {
    private val _introImageDownloadUri = MutableLiveData<Uri>()
    val introImageDownloadUri: LiveData<Uri> = _introImageDownloadUri

    private val _guideImageDownloadUri = MutableLiveData<Uri>()
    val guideImageDownloadUri: LiveData<Uri> = _guideImageDownloadUri

    private val _banners = MutableLiveData<List<Banner>>()
    val banners: LiveData<List<Banner>> = _banners

    init {
        loadIntroImageDownloadUri()
        loadGuideImageDonwloadUri()
        initBanners()
    }

    private fun initBanners() {
        viewModelScope.launch {
            _banners.value = repository.loadBanners()
        }
    }

    private fun loadIntroImageDownloadUri() {
        viewModelScope.launch {
            _introImageDownloadUri.value = repository.loadIntroImageDownloadUri()
        }
    }

    private fun loadGuideImageDonwloadUri() {
        viewModelScope.launch {
            _guideImageDownloadUri.value = repository.loadGuideImageDownloadUri()
        }
    }

}