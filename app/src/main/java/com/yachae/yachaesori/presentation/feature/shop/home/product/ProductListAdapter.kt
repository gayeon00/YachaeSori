package com.yachae.yachaesori.presentation.feature.shop.home.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yachae.yachaesori.R
import com.yachae.yachaesori.data.Product
import com.yachae.yachaesori.databinding.ListItemProductBinding

class ProductListAdapter() :
    ListAdapter<Product, ProductListAdapter.ProductItemViewHolder>(ProductDiffCallBack()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductItemViewHolder {
        return ProductItemViewHolder(
            ListItemProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProductItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ProductItemViewHolder(
        private val listItemProductBinding: ListItemProductBinding
    ) : RecyclerView.ViewHolder(listItemProductBinding.root) {

        init {
            listItemProductBinding.root.setOnClickListener {
                //TODO: 클릭하면 상세 페이지로 넘어가기
            }
        }

        fun bind(product: Product) {
            listItemProductBinding.run {
                tvProductName.text = product.name
                tvProductPrice.text = product.price
                //TODO: 이미지넣어주기
                ivProduct.setImageResource(R.drawable.ic_launcher_background)


            }
        }

    }
}

private class ProductDiffCallBack : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }
}