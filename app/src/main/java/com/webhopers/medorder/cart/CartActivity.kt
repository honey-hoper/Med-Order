package com.webhopers.medorder.cart

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.webhopers.medorder.R
import com.webhopers.medorder.adapters.FirebaseCartAdapter
import com.webhopers.medorder.myOrders.MyOrdersActivity
import com.webhopers.medorder.profile.ProfileActivity
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
        showProgressBar(true)
    }

    private fun setupToolbar() {
        setSupportActionBar(ac_toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.overflow_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.om_action_my_orders -> startMyOrdersActivity()
            R.id.om_action_profile -> startProfileActivity()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun startMyOrdersActivity() {
        startActivity(Intent(this, MyOrdersActivity::class.java))
    }

    private fun startProfileActivity() {
        startActivity(Intent(this, ProfileActivity::class.java))
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

    override fun makeToast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    override fun enablePlaceOrderButton(bool: Boolean) {
        if (bool) ac_place_order_btn.isEnabled = true
        else ac_place_order_btn.isEnabled = false
    }

}
