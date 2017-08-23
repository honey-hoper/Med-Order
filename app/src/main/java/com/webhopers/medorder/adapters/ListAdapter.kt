package com.webhopers.medorder.adapters

import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.webhopers.medorder.R
import com.webhopers.medorder.dialogs.QuantityPickerDialog
import com.webhopers.medorder.models.Product
import com.webhopers.medorder.productDetail.ProductDetailActivity
import com.webhopers.medorder.services.retrofit.WooCommerceRetrofitClient
import com.webhopers.medorder.services.woocommerce.WooCommerceService
import kotlinx.android.synthetic.main.product_list_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListAdapter(val dataset: MutableList<Product>,
                  TOTAL_PRODUCTS: Int) : RecyclerView.Adapter<ProductListViewHolder>() {

    var loading = false
    val PRODUCT_TO_GET = 20
    var remaining = TOTAL_PRODUCTS - 50

    override fun onBindViewHolder(holder: ProductListViewHolder, position: Int) {
        holder.bind(dataset[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.product_list_item, parent, false)
        return ProductListViewHolder(v)
    }

    override fun getItemCount() = dataset.size

    fun getMoreProducts(offset: String) {
        val perPage: Int
        if (remaining >= PRODUCT_TO_GET) {
            perPage = PRODUCT_TO_GET
            remaining -= PRODUCT_TO_GET
        } else {
            perPage = remaining
            remaining = 0
        }

        WooCommerceRetrofitClient.retrofit.create(WooCommerceService::class.java)
                .getProducts(offset = offset, perPage = perPage.toString())
                .clone()
                .enqueue(object : Callback<List<Product>> {
                    override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                        loading = false
                    }

                    override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                        loading = false
                        val newList = response.body()
                        if (newList == null || newList.isEmpty())
                            return
                        dataset.addAll(newList)
                        notifyItemRangeInserted(itemCount - 1, newList.size)
                    }

                })

    }

}

class ProductListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(product: Product) {
        itemView.pli_product_name.text = product.name
        itemView.pli_product_price.text =  if (!product.price.isNullOrBlank()) "\u20B9" + product.price else "N/A"
        itemView.pli_product_quantity.text = product.quantity ?: "N/A"
        itemView.pli_product_packing.text = getPackingSize(product)
        itemView.setOnClickListener {
            val intent = Intent(itemView.context, ProductDetailActivity::class.java)
            intent.putExtra("PRODUCT", product)
            startActivity(itemView.context, intent, null)
        }
        itemView.pli_image_btn.setOnClickListener { QuantityPickerDialog(it.context, product) }
    }

    private fun getPackingSize(product: Product?): String {
        if (product != null) {
            val attrs = product.attributes
            if (attrs != null && !attrs.isEmpty()) {
                val attr = attrs[0]
                if (attr != null) {
                    val opts = attr.options
                    if (opts != null && !opts.isEmpty()) {
                        return opts[0]
                    }
                }
            }
        }
        return "N/A"
    }
}

class ScrollListener(
        val layoutManager: LinearLayoutManager,
        val adapter: ListAdapter): RecyclerView.OnScrollListener() {

    var offset = 50
    val visibleThreshold = 10
    var lastVisibleItem = 0
    var visibleItemCount = 0
    var totalItemCount = 0

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        visibleItemCount = recyclerView.childCount
        totalItemCount = layoutManager.itemCount
        lastVisibleItem = layoutManager.findLastVisibleItemPosition()

        if (!adapter.loading && totalItemCount <= (lastVisibleItem + visibleThreshold) && adapter.remaining != 0) {
            adapter.getMoreProducts(offset.toString())
            adapter.loading = true
            offset += adapter.PRODUCT_TO_GET

        }
    }
}


