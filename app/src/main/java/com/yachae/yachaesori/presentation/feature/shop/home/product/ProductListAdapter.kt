package com.yachae.yachaesori.presentation.feature.shop.home.product

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yachae.yachaesori.MainActivity
import com.yachae.yachaesori.R
import com.yachae.yachaesori.data.Product
import com.yachae.yachaesori.databinding.ListItemProductBinding
import com.yachae.yachaesori.presentation.feature.detail.ProductDetailFragment

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

            val navController = (view.context as MainActivity).findNavController(R.id.nav_host)
            navController.navigate(R.id.action_shopFragment_to_productDetailFragment)
            //homefragment에서 navcontroller를 찾고 있음 -> shop -> mainactivity의 navcontroller를 찾아야하는디
//            view.findNavController()
//                .navigate(R.id.action_shopFragment_to_productDetailFragment)
//            (view.context as MainActivity).supportFragmentManager.beginTransaction()
//                .replace(R.id.nav_host, ProductDetailFragment()).commit()


        }


        fun bind(item: Product) {
            listItemProductBinding.run {
                tvProductName.text = item.name
                tvListProductPrice.text = item.price
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