package com.yachae.yachaesori.presentation.feature.shop.home.product

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.yachae.yachaesori.R
import com.yachae.yachaesori.data.Product
import com.yachae.yachaesori.databinding.ListItemProductBinding
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
        holder.bind(getItem(position))
    }

    class ProductViewHolder(
        private val listItemProductBinding: ListItemProductBinding
    ) : RecyclerView.ViewHolder(listItemProductBinding.root) {

        private fun navigateToProductDetail(product: Product, view: View) {
            Log.d("ProductListAdapter", "View: $view, Context: ${view.context}")

//            val navController = (view.context as MainActivity).findNavController(R.id.nav_host)
//
//            navController.navigate(R.id.action_shopFragment_to_productDetailFragment)

            view.findNavController().navigate(R.id.action_shopFragment_to_productDetailFragment)
            //homefragment에서 navcontroller를 찾고 있음 -> shop -> mainactivity의 navcontroller를 찾아야하는디
//            view.findNavController()
//                .navigate(R.id.action_shopFragment_to_productDetailFragment)
//            (view.context as MainActivity).supportFragmentManager.beginTransaction()
//                .replace(R.id.nav_host, ProductDetailFragment()).commit()


        }


        fun bind(item: Product) {
            listItemProductBinding.run {

                tvProductName.text = item.name
                val dec = DecimalFormat("#,###")
                tvListProductPrice.text = dec.format(item.price)
                ivProduct.setImageFromUrl(item.mainImageUrl)
                if (ivProduct.drawable == null) {
                    Log.d("ProductListAdapter", "null")
                } else {
                    Log.d("ProductListAdapter", ivProduct.drawable.toString())
                }


                root.setOnClickListener {
                    navigateToProductDetail(item, it)
                }

            }
        }

    }
}

private fun ImageView.setImageFromUrl(mainImageUrl: String) {

    Log.d("ProductListAdapter", mainImageUrl)
    val storageReference = Firebase.storage.reference.child(mainImageUrl)

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