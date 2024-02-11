package com.yachae.yachaesori.domain.repository

import android.net.Uri

interface HomeRepository {
    suspend fun loadIntroImageDownloadUri(): Uri?
    suspend fun loadGuideImageDownloadUri(): Uri?

}
