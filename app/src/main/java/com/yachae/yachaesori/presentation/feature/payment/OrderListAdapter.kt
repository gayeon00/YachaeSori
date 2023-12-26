package com.yachae.yachaesori.presentation.feature.payment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.yachae.yachaesori.R
import com.yachae.yachaesori.data.OrderItem
import com.yachae.yachaesori.databinding.ListItemOrderProductBinding
import java.text.DecimalFormat

class OrderListAdapter(private val isDetail: Boolean) :
    ListAdapter<OrderItem, OrderListAdapter.OrderItemViewHolder>(OrderItemDiffCallBack()) {
    class OrderItemViewHolder(
        private val binding: ListItemOrderProductBinding,
        private val isDetail: Boolean
    ) : ViewHolder(binding.root) {
        fun bind(item: OrderItem) {
            val dec = DecimalFormat("#,###")
            binding.run {
                if(isDetail) {
                    layoutDetailButtons.visibility = View.VISIBLE
                }
                tvOrderProductName.text = item.product.name
                tvOrderProductPrice.text = item.product.price
                //TODO: 이미지넣어주기
                ivOrderProduct.setImageResource(R.drawable.ic_launcher_background)
                tvOrderStatus.text = item.status
                tvOrderProductOption.text = "${item.selectedOption} / ${item.quantity}개"
                tvOrderProductPrice.text =
                    "${dec.format(item.product.price.toInt() * item.quantity)}원"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderItemViewHolder {
        return OrderItemViewHolder(
            ListItemOrderProductBinding.inflate(
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