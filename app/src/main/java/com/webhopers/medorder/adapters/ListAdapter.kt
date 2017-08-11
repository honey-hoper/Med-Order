package com.webhopers.medorder.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.webhopers.medorder.R
import com.webhopers.medorder.models.Product
import kotlinx.android.synthetic.main.product_list_item.view.*

class ListAdapter(val dataset: List<Product>) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataset[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.product_list_item, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount() = dataset.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(product: Product) {
            itemView.pli_product_name.text = product.name
            itemView.pli_product_price.text =  "\u20B9" + product.price
            itemView.pli_product_quantity.text = product.quantity
        }
    }
}