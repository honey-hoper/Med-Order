package com.webhopers.medorder.adapters

import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.webhopers.medorder.R
import com.webhopers.medorder.dialogs.QuantityPickerDialog
import com.webhopers.medorder.models.Product
import com.webhopers.medorder.productDetail.ProductDetailActivity
import kotlinx.android.synthetic.main.product_list_item.view.*

class ListAdapter(val dataset: List<Product>) : RecyclerView.Adapter<ProductListViewHolder>() {


    override fun onBindViewHolder(holder: ProductListViewHolder, position: Int) {
        holder.bind(dataset[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.product_list_item, parent, false)
        return ProductListViewHolder(v)
    }

    override fun getItemCount() = dataset.size

}

class ProductListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(product: Product) {
        itemView.pli_product_name.text = product.name
        itemView.pli_product_price.text =  if (!product.price.isNullOrBlank()) "\u20B9" + product.price else "N/A"
        itemView.pli_product_quantity.text = product.quantity ?: "N/A"
        itemView.setOnClickListener {
            val intent = Intent(itemView.context, ProductDetailActivity::class.java)
            intent.putExtra("PRODUCT", product)
            startActivity(itemView.context, intent, null)
        }
        itemView.pli_image_btn.setOnClickListener { QuantityPickerDialog(it.context, product) }
    }
}
