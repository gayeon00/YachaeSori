package com.yachae.yachaesori.presentation.feature.shop.home

import android.view.View
import android.view.ViewGroup
import com.yachae.yachaesori.R
import com.yachae.yachaesori.databinding.ItemHomeBannerBinding
import com.zhpan.bannerview.BaseBannerAdapter
import com.zhpan.bannerview.BaseViewHolder

class HomeBannerAdapter : BaseBannerAdapter<BannerBean>() {

    override fun createViewHolder(
        parent: ViewGroup,
        itemView: View,
        viewType: Int
    ): BaseViewHolder<BannerBean> {
        return HomeBannerViewHolder(ItemHomeBannerBinding.bind(itemView))
    }

    override fun bindData(
        holder: BaseViewHolder<BannerBean>,
        data: BannerBean?,
        position: Int,
        pageSize: Int
    ) {
        holder.setImageResource(R.id.image_home_banner, data!!.imgRes)
    }

    override fun getLayoutId(viewType: Int): Int {
        return R.layout.item_home_banner
    }

    internal class HomeBannerViewHolder(var viewBinding: ItemHomeBannerBinding) :
        BaseViewHolder<BannerBean>(viewBinding.root)
}