package com.yachae.yachaesori.presentation.feature.shop.home.product

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yachae.yachaesori.MainActivity
import com.yachae.yachaesori.R
import com.yachae.yachaesori.data.Product
import com.yachae.yachaesori.databinding.ListItemProductBinding
import com.yachae.yachaesori.presentation.feature.shop.detail.ProductDetailFragment

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

        private fun navigateToProductDetail(product: Product, view: View) {
            Log.d("ProductListAdapter", "View: $view, Context: ${view.context}")
            (view.context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host, ProductDetailFragment()).commit()


        }


        fun bind(item: Product) {
            listItemProductBinding.run {
                tvProductName.text = item.name
                tvProductPrice.text = item.price
                //TODO: 이미지넣어주기
                ivProduct.setImageResource(R.drawable.ic_launcher_background)

                root.setOnClickListener {
                    navigateToProductDetail(item, it)
                }

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