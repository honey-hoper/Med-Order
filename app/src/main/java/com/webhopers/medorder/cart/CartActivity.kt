package com.webhopers.medorder.cart

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.webhopers.medorder.R
import com.webhopers.medorder.adapters.ListAdapter
import com.webhopers.medorder.constants.Constants
import com.webhopers.medorder.models.Product
import kotlinx.android.synthetic.main.activity_cart.*

class CartActivity :
        CartView,
        AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        CartPresenter(this)

        initUI()
    }

    private fun initUI() {
        setupToolbar()
        setupRecyclerView()
    }

    private fun setupToolbar() {
        setSupportActionBar(ac_toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    private fun setupRecyclerView() {
        ac_recycler_view.apply {
            layoutManager = LinearLayoutManager(this@CartActivity)
            setHasFixedSize(true)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    // View Functions
    override fun setAdapter(dataset: MutableList<Product>) {
        ac_recycler_view.adapter = ListAdapter(dataset, Constants.CART_LIST)
    }

}
