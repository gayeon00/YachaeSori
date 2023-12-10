package com.yachae.yachaesori.presentation.feature.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yachae.yachaesori.domain.repository.UserAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val userAuthRepository: UserAuthRepository
) : ViewModel() {

    fun signInWithYachae(accessToken: String) {
        viewModelScope.launch {
            userAuthRepository.signInWithYachae(accessToken)
        }
    }
}