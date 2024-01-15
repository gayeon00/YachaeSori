package com.yachae.yachaesori.presentation.feature.order

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yachae.yachaesori.MainActivity
import com.yachae.yachaesori.R
import com.yachae.yachaesori.data.model.Order
import com.yachae.yachaesori.data.model.getOrderState
import com.yachae.yachaesori.databinding.ListItemOrderBinding
import java.text.DecimalFormat

class OrderListAdapter() :
    ListAdapter<Order, OrderListAdapter.OrderListViewHolder>(OrderDiffCallBack()) {
    class OrderListViewHolder(
        private val listItemOrderBinding: ListItemOrderBinding
    ) :
        RecyclerView.ViewHolder(listItemOrderBinding.root) {

        private fun navigateToOrderDetail(
            order: Order,
            view: View
        ) {
            Log.d("ProductListAdapter", "View: $view, Context: ${view.context}")

            val navController = (view.context as MainActivity).findNavController(R.id.nav_host)
            navController.navigate(R.id.action_orderListFragment_to_orderDetailFragment2)
            //homefragment에서 navcontroller를 찾고 있음 -> shop -> mainactivity의 navcontroller를 찾아야하는디
//            view.findNavController()
//                .navigate(R.id.action_shopFragment_to_productDetailFragment)
//            (view.context as MainActivity).supportFragmentManager.beginTransaction()
//                .replace(R.id.nav_host, ProductDetailFragment()).commit()


        }

        fun bind(item: Order) {
            listItemOrderBinding.run {
                textViewOrderState.text = getOrderState(item.status).str
                textViewOrderNum.text = "No.${item.orderId}"
                textViewOrderDate.text = item.orderDate
                imageViewMainImage.setImageResource(R.drawable.ic_launcher_background)
                textViewOrderProducts.text ="${item.itemList[0].product.name} 외 ${item.itemList.size - 1}건"
                textViewOrderOption.text =item.itemList[0].selectedOption
                textViewOrderPrice.text = DecimalFormat("#,###").format(item.totalPrice)+"원"

                root.setOnClickListener {
                    navigateToOrderDetail(item, it)
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderListViewHolder {
        return OrderListViewHolder(
            ListItemOrderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: OrderListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

private class OrderDiffCallBack() : DiffUtil.ItemCallback<Order>() {
    override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
        return oldItem == newItem
    }
}