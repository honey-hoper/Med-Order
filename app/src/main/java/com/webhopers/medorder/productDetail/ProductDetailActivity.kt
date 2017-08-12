package com.webhopers.medorder.productDetail

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.webhopers.medorder.R
import com.webhopers.medorder.models.Product
import kotlinx.android.synthetic.main.activity_product_detail.*

class ProductDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        val product = intent.getSerializableExtra("PRODUCT") as Product

        setupUI(product)

    }

    fun setupUI(product: Product?) {
        if (product != null) {
            apd_product_name.text = product.name
            apd_product_price.text = "\u20B9" + product.price
            apd_product_quantity.text = product.quantity
            apd_product_desc.text = product.desc
        }
    }
}
