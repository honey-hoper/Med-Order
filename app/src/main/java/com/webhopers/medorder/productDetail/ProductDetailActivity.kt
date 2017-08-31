package com.webhopers.medorder.productDetail

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.squareup.picasso.Picasso
import com.webhopers.medorder.R
import com.webhopers.medorder.cart.CartActivity
import com.webhopers.medorder.dialogs.QuantityPickerDialog
import com.webhopers.medorder.models.Product
import com.webhopers.medorder.myOrders.MyOrdersActivity
import com.webhopers.medorder.profile.ProfileActivity
import com.webhopers.medorder.utils.convertDpToPixels
import kotlinx.android.synthetic.main.activity_product_detail.*
import org.jsoup.Jsoup

class ProductDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        val product = intent.getSerializableExtra("PRODUCT") as Product

        initUI(product)

        apd_add_to_cart_btn.setOnClickListener { QuantityPickerDialog(this, product) }

    }

    private fun initUI(product: Product?) {
        if (product != null) {
            apd_product_name.text = product.name
            apd_product_price.text = if (!product.price.isNullOrBlank()) "\u20B9" + product.price else "N/A"
            apd_product_quantity.text = if (!product.quantity.isNullOrBlank()) product.quantity else "N/A"
            apd_product_packing.text = getPackingSize(product)
            apd_product_desc.text = if (!product.desc.isNullOrBlank()) {
                Jsoup.parse(product.desc).text().replace(Regex("\\s*\\+\\s*"), "\n")
            } else "N/A"
        }
        setupToolbar()
        val url = product?.images?.get(0)?.src
        if (!url.isNullOrBlank()) getImage(url)
        else {}
    }

    private fun setupToolbar() {
        setSupportActionBar(apd_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun getImage(url: String?) {
        Picasso.with(this)
                .load(url)
                .resize(convertDpToPixels(400f, resources).toInt(), convertDpToPixels(400f, resources).toInt())
                .centerCrop()
                .into(apd_image_view)

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
            R.id.action_my_orders -> startMyOrdersActivity()
            R.id.action_profile -> startProfileActivity()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun startCartActivity() {
        startActivity(Intent(this, CartActivity::class.java))
    }

    private fun startMyOrdersActivity() {
        startActivity(Intent(this, MyOrdersActivity::class.java))
    }

    private fun startProfileActivity() {
        startActivity(Intent(this, ProfileActivity::class.java))
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
