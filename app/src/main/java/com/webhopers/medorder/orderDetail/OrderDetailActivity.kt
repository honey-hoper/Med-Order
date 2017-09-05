package com.webhopers.medorder.orderDetail

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.webhopers.medorder.R
import com.webhopers.medorder.models.OrderResponse
import kotlinx.android.synthetic.main.activity_order_detail.*

class OrderDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)

        initUI()

        val order = intent.getSerializableExtra("ORDER") as OrderResponse

        if (order.lineItems != null) {
            order.lineItems?.forEach {
                aod_items_view.append("${it.productName}\n")
                aod_quantities_view.append("${it.quantity}\n")
            }
        }

        aod_tax.text = if (!order.totalTax.isNullOrBlank()) "\u20B9${order.totalTax}" else ""
        aod_amt.text = if (!order.totalAmount.isNullOrBlank()) "\u20B9${order.totalAmount}" else ""

    }


    private fun initUI() {
        setUpToolbar()
        window.decorView.background = ContextCompat.getDrawable(this, R.drawable.order_detail_background)

    }

    private fun setUpToolbar() {
        setSupportActionBar(aod_toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
