package com.webhopers.medorder.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.webhopers.medorder.R
import com.webhopers.medorder.models.OrderResponse
import kotlinx.android.synthetic.main.order_list_item.view.*

class MyOrdersAdapter(val dataset: List<OrderResponse>) : RecyclerView.Adapter<MyOrderListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyOrderListViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.order_list_item, parent, false)
        return MyOrderListViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyOrderListViewHolder, position: Int) {
        holder.bind(dataset[position])
    }

    override fun getItemCount() = dataset.size


}

class MyOrderListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(orderResponse: OrderResponse) {
        itemView.apply {
            oli_order_number.text = orderResponse.orderNumber
            oli_order_date.text = orderResponse.dateCreated?.substringBefore("T")
            oli_order_status.text = orderResponse.status
        }
    }
}
