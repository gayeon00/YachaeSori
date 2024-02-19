package com.yachae.yachaesori.presentation.feature.payment

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.yachae.yachaesori.R
import com.yachae.yachaesori.data.model.OrderItem
import com.yachae.yachaesori.data.model.getOrderState
import com.yachae.yachaesori.databinding.ListItemSelectedItemBinding
import com.yachae.yachaesori.util.setImageFromUrl
import java.text.DecimalFormat

class ItemListAdapter(private val isDetail: Boolean) :
    ListAdapter<OrderItem, ItemListAdapter.OrderItemViewHolder>(OrderItemDiffCallBack()) {
    class OrderItemViewHolder(
        private val binding: ListItemSelectedItemBinding,
        private val isDetail: Boolean
    ) : ViewHolder(binding.root) {
        fun bind(item: OrderItem) {
            val dec = DecimalFormat("#,###")
            binding.run {
                if(isDetail) {
                    layoutDetailButtons.visibility = View.VISIBLE
                }
                tvOrderProductName.text = item.product.name
                tvOrderProductPrice.text = dec.format(item.product.price)
                ivOrderProduct.setImageFromUrl(item.product.mainImageUrl)
                tvOrderStatus.text = getOrderState(item.status).str
                tvOrderProductOption.text = "${item.selectedOption} / ${item.quantity}개"
                tvOrderProductPrice.text =
                    "${dec.format(item.product.price * item.quantity)}원"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderItemViewHolder {
        return OrderItemViewHolder(
            ListItemSelectedItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            isDetail
        )
    }

    override fun onBindViewHolder(holder: OrderItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}


private class OrderItemDiffCallBack : DiffUtil.ItemCallback<OrderItem>() {
    override fun areItemsTheSame(oldItem: OrderItem, newItem: OrderItem): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: OrderItem, newItem: OrderItem): Boolean {
        return oldItem == newItem
    }
}