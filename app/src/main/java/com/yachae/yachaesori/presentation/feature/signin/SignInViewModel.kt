package com.yachae.yachaesori.presentation.feature.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthResult
import com.yachae.yachaesori.data.repository.SignInRepository
import com.yachae.yachaesori.domain.usecase.SignInUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignInViewModel(
    private val signInUserUseCase: SignInUserUseCase
) : ViewModel() {

    private val _signInResult = MutableLiveData<AuthResult>()
    val signInResult: LiveData<AuthResult> get() = _signInResult

    fun signInWithYachae(accessToken: String) {
        viewModelScope.launch {
            _signInResult.value = signInUserUseCase.signInWithYachae(accessToken)
        }
    }
}

class SignInViewModelFactory(private val useCase: SignInUserUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignInViewModel::class.java)) {
            return SignInViewModel(useCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}