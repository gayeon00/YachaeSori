package com.yachae.yachaesori.domain.repository

interface HomeRepository {
    suspend fun loadIntroImageUrl(): String?
    suspend fun loadGuideImageUrl(): String?

}
