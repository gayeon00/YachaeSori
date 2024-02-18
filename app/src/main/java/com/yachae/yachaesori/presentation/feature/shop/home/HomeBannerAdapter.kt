package com.yachae.yachaesori.presentation.feature.shop.home

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.yachae.yachaesori.R
import com.yachae.yachaesori.data.model.Banner
import com.yachae.yachaesori.databinding.ItemHomeBannerBinding
import com.zhpan.bannerview.BaseBannerAdapter
import com.zhpan.bannerview.BaseViewHolder

class HomeBannerAdapter : BaseBannerAdapter<Banner>() {

    inner class HomeBannerViewHolder(
        viewBinding: ItemHomeBannerBinding
    ) : BaseViewHolder<Banner>(viewBinding.root)

    override fun createViewHolder(
        parent: ViewGroup,
        itemView: View,
        viewType: Int
    ): BaseViewHolder<Banner> {
        return HomeBannerViewHolder(ItemHomeBannerBinding.bind(itemView))
    }

    override fun bindData(
        holder: BaseViewHolder<Banner>,
        data: Banner?,
        position: Int,
        pageSize: Int
    ) {
        Log.d("HomeBannerAdapter", data.toString())
        val storageReference = Firebase.storage.reference.child(data!!.imageUrl)

        val view = holder.findViewById<ImageView>(R.id.image_home_banner)

        Glide.with(view.context)
            .load(storageReference)
            .placeholder(R.drawable.ic_launcher_background)
            .centerCrop()
            .into(view)
    }

    override fun getLayoutId(viewType: Int): Int {
        return R.layout.item_home_banner
    }


}