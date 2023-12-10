package com.yachae.yachaesori.di

import com.yachae.yachaesori.data.remote.FirebaseFunctionsDataSource
import com.yachae.yachaesori.data.remote.FirebaseUserDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseFunctionsDataSource(): FirebaseFunctionsDataSource {
        return FirebaseFunctionsDataSource()
    }

    @Provides
    @Singleton
    fun provideFirebaseUserDataSource(): FirebaseUserDataSource {
        return FirebaseUserDataSource()
    }

}