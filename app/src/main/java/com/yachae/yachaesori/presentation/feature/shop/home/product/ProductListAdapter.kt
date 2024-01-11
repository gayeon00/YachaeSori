package com.yachae.yachaesori.presentation.feature.shop.home.product

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.yachae.yachaesori.R
import com.yachae.yachaesori.data.model.Product
import com.yachae.yachaesori.databinding.ListItemProductBinding
import com.yachae.yachaesori.presentation.feature.shop.ShopFragmentDirections
import java.text.DecimalFormat

class ProductListAdapter() :
    ListAdapter<Product, ProductListAdapter.ProductViewHolder>(ProductDiffCallBack()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            ListItemProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    class ProductViewHolder(
        private val listItemProductBinding: ListItemProductBinding
    ) : RecyclerView.ViewHolder(listItemProductBinding.root) {

        private fun navigateToProductDetail(position: Int, view: View) {
            Log.d("ProductListAdapter", "View: $view, Context: ${view.context}")
            val action = ShopFragmentDirections.actionShopFragmentToProductDetailFragment(position)
            view.findNavController().navigate(action)
        }


        fun bind(item: Product, position: Int) {
            listItemProductBinding.run {

                tvProductName.text = item.name
                val dec = DecimalFormat("#,###")
                tvListProductPrice.text = dec.format(item.price)
                ivProduct.setImageFromUrl(item.mainImageUrl)

                root.setOnClickListener {
                    navigateToProductDetail(position, it)
                }

            }
        }

    }
}

private fun ImageView.setImageFromUrl(imageUrl: String) {

    Log.d("ProductListAdapter", imageUrl)
    val storageReference = Firebase.storage.reference.child(imageUrl)

    Glide.with(context)
        .load(storageReference)
        .placeholder(R.drawable.ic_launcher_background)
        .centerCrop()
        .into(this)

}

private class ProductDiffCallBack : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }
}