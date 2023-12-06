package com.yachae.yachaesori.domain.usecase

import com.google.firebase.auth.AuthResult
import com.yachae.yachaesori.data.repository.SignInRepository
import javax.inject.Inject

class SignInUserUseCase (
    private val signInRepository: SignInRepository
) {
    suspend fun signInWithYachae(token: String): AuthResult? {
        // 비즈니스 로직 처리
        return signInRepository.signInWithYachae(token)
    }
}