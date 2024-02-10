package com.yachae.yachaesori.presentation.feature.shop.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yachae.yachaesori.domain.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository
) : ViewModel() {
    private val _introImageUrl = MutableLiveData<String>()
    val introImageUrl: LiveData<String> = _introImageUrl

    private val _guideImageUrl = MutableLiveData<String>()
    val guideImageUrl: LiveData<String> = _guideImageUrl

    init {
        loadIntroImageUrl()
        loadGuideImageUrl()
    }

    private fun loadIntroImageUrl() {
        viewModelScope.launch {
            _introImageUrl.value = repository.loadIntroImageUrl()
        }
    }

    private fun loadGuideImageUrl() {
        viewModelScope.launch {
            _guideImageUrl.value = repository.loadGuideImageUrl()
        }
    }

}