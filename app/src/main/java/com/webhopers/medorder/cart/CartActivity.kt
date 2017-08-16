package com.webhopers.medorder.cart

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.google.firebase.database.FirebaseDatabase
import com.webhopers.medorder.R
import com.webhopers.medorder.adapters.FirebaseCartAdapter
import com.webhopers.medorder.services.firebase.FirebaseDatabaseService
import kotlinx.android.synthetic.main.activity_cart.*

class CartActivity :
        CartView,
        AppCompatActivity() {

    lateinit var presenter: CartPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        presenter = CartPresenter(this)

        initUI()

        ac_place_order_btn.setOnClickListener { presenter.onPlaceOrderClick() }
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
            val query = FirebaseDatabase.getInstance()
                    .getReference(FirebaseDatabaseService.PATH_CART)
                    .child("user_id")

            adapter = FirebaseCartAdapter(query)
            layoutManager = LinearLayoutManager(this@CartActivity)
            setHasFixedSize(true)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.stop()
    }


    // View Functions
    override fun showProgressBar(bool: Boolean) {
        if (bool) ac_progress_bar.visibility = View.VISIBLE
        else ac_progress_bar.visibility = View.INVISIBLE
    }


}
