package com.webhopers.medorder.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.Query
import com.webhopers.medorder.R
import com.webhopers.medorder.models.ProductF
import com.webhopers.medorder.services.firebase.FirebaseDatabaseService
import kotlinx.android.synthetic.main.cart_list_item.view.*

class FirebaseCartAdapter(query: Query)
    : FirebaseRecyclerAdapter<ProductF, CartViewHolder>(
        ProductF::class.java,
        R.layout.cart_list_item,
        CartViewHolder::class.java,
        query) {

    override fun populateViewHolder(viewHolder: CartViewHolder, product: ProductF, position: Int) {
        viewHolder.bind(product)
    }
}

class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(product: ProductF) {
        itemView.cli_product_name.text = product.name
        itemView.cli_product_quantity.text = product.orderedQuantity?.toString()
        itemView.cli_product_price.text = product.totalPrice?.toString()
        itemView.cli_remove_btn.setOnClickListener { FirebaseDatabaseService.removeFromCart("user_id", product.id!!.toString()) }
    }
}