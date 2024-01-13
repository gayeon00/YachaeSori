package com.yachae.yachaesori.di

import com.yachae.yachaesori.data.remote.FirebaseFunctionsDataSource
import com.yachae.yachaesori.data.remote.FirebaseUserDataSource
import com.yachae.yachaesori.data.repository.OrderRepositoryImpl
import com.yachae.yachaesori.data.repository.ProductRepositoryImpl
import com.yachae.yachaesori.domain.repository.OrderRepository
import com.yachae.yachaesori.domain.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.FragmentScoped
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

    @Provides
    @Singleton
    fun provideProductRepository(): ProductRepository {
        return ProductRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideOrderRepository(): OrderRepository {
        return OrderRepositoryImpl()
    }

}