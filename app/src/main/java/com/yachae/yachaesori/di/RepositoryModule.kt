package com.yachae.yachaesori.di

import com.yachae.yachaesori.data.repository.UserAuthRepositoryImpl
import com.yachae.yachaesori.domain.repository.UserAuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindUserAuthRepository(
        userAuthRepositoryImpl: UserAuthRepositoryImpl
    ): UserAuthRepository
}