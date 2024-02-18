package com.yachae.yachaesori.domain.repository

import android.net.Uri
import com.yachae.yachaesori.data.model.Banner

interface HomeRepository {
    suspend fun loadIntroImageDownloadUri(): Uri?
    suspend fun loadGuideImageDownloadUri(): Uri?
    suspend fun loadBanners(): List<Banner>?

}
