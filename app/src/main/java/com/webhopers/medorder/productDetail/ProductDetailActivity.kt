package com.webhopers.medorder.productDetail

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.webhopers.medorder.R
import com.webhopers.medorder.cart.CartActivity
import com.webhopers.medorder.dialogs.QuantityPickerDialog
import com.webhopers.medorder.models.Product
import kotlinx.android.synthetic.main.activity_product_detail.*

class ProductDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        val product = intent.getSerializableExtra("PRODUCT") as Product

        initUI(product)

        apd_add_to_cart_btn.setOnClickListener { QuantityPickerDialog(this, product.quantity!!.toInt())}

    }

    private fun initUI(product: Product?) {
        if (product != null) {
            apd_product_name.text = product.name
            apd_product_price.text = "\u20B9" + product.price
            apd_product_quantity.text = product.quantity
            apd_product_desc.text = product.desc
        }
        setupToolbar()
    }

    private fun setupToolbar() {
        setSupportActionBar(apd_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.action_open_cart -> startCartActivity()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun startCartActivity() {
        startActivity(Intent(this, CartActivity::class.java))
    }
}
