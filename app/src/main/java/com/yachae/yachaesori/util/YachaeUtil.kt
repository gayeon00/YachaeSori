package com.yachae.yachaesori.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.yachae.yachaesori.R

fun ImageView.setImageFromUrl(imageUrl: String) {

    val storageReference = Firebase.storage.reference.child(imageUrl)

    Glide.with(context)
        .load(storageReference)
        .placeholder(R.drawable.yachae_logo)
        .centerCrop()
        .into(this)
}