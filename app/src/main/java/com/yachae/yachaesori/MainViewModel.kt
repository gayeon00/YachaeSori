package com.yachae.yachaesori

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainViewModel : ViewModel() {

    private val firebaseAuth = FirebaseAuth.getInstance()

    private val _currentUser = MutableLiveData<FirebaseUser?>()
    val currentUser: LiveData<FirebaseUser?> = _currentUser

    private val authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
        val user = firebaseAuth.currentUser
        _currentUser.value = user
    }

    fun addAuthStateListener() {
        firebaseAuth.addAuthStateListener(authStateListener)
    }

    fun removeAuthStateListener() {
        firebaseAuth.removeAuthStateListener(authStateListener)
    }

    fun checkCurrentUser() {
        val user = firebaseAuth.currentUser
        _currentUser.value = user
    }

}