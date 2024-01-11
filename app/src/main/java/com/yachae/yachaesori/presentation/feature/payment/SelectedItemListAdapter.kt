package com.yachae.yachaesori.presentation.feature.payment

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.yachae.yachaesori.R
import com.yachae.yachaesori.data.model.SelectedItem
import com.yachae.yachaesori.databinding.ListItemSelectedItemBinding
import java.text.DecimalFormat

class SelectedItemListAdapter :
    ListAdapter<SelectedItem, SelectedItemListAdapter.SelectedItemViewHolder>(
        SelectedItemDiffCallback()
    ) {
    class SelectedItemViewHolder(
        private val listItemSelectedItemBinding: ListItemSelectedItemBinding
    ) : ViewHolder(listItemSelectedItemBinding.root) {
        fun bind(selectedItem: SelectedItem) {
            listItemSelectedItemBinding.run {
                tvOrderProductName.text = selectedItem.product.name
                tvOrderProductOption.text = "${selectedItem.selectedOption} / ${selectedItem.quantity}개"
                tvOrderProductPrice.text =
                    "${DecimalFormat("#,###").format(selectedItem.product.price * selectedItem.quantity)}원"
                ivOrderProduct.setImageFromUrl(selectedItem.product.mainImageUrl)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectedItemViewHolder {
        return SelectedItemViewHolder(
            ListItemSelectedItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SelectedItemViewHolder, position: Int) {
        holder.bind(getItem(position))
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

private class SelectedItemDiffCallback : DiffUtil.ItemCallback<SelectedItem>() {
    override fun areItemsTheSame(oldItem: SelectedItem, newItem: SelectedItem): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: SelectedItem, newItem: SelectedItem): Boolean {
        return oldItem == newItem
    }
}