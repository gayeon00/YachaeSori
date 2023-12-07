package com.yachae.yachaesori

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.yachae.yachaesori.domain.usecase.SignInUserUseCase
import kotlinx.coroutines.launch

class AuthViewModel(
    private val signInUserUseCase: SignInUserUseCase
) : ViewModel() {
    private val auth = FirebaseAuth.getInstance()

    private val _currentUser = MutableLiveData<FirebaseUser?>()
    val currentUser: LiveData<FirebaseUser?> = _currentUser

    private val authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
        val user = firebaseAuth.currentUser
        _currentUser.value = user
    }

    fun addAuthStateListener() {
        auth.addAuthStateListener(authStateListener)
    }

    fun removeAuthStateListener() {
        auth.removeAuthStateListener(authStateListener)
    }

    fun checkCurrentUser() {
        val user = auth.currentUser
        _currentUser.value = user
    }

    fun signInWithYachae(accessToken: String) {
        viewModelScope.launch {
            signInUserUseCase.signInWithYachae(accessToken)
        }
    }

}

class AuthViewModelFactory(private val useCase: SignInUserUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            return AuthViewModel(useCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}