package com.webhopers.medorder.productList


import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.webhopers.medorder.R
import com.webhopers.medorder.adapters.ListAdapter
import com.webhopers.medorder.adapters.ScrollListener
import com.webhopers.medorder.cart.CartActivity
import com.webhopers.medorder.models.Product
import kotlinx.android.synthetic.main.activity_product_list.*

class ProductListActivity :
        ProductListView,
        AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)

        ProductListPresenter(this)

        initUI()
    }

    private fun initUI() {
        setUpRecyclerView()
        setSupportActionBar(apl_toolbar)
    }

    private fun setUpRecyclerView() {
        apl_recyler_view.apply {
            layoutManager = LinearLayoutManager(this@ProductListActivity)
            setHasFixedSize(true)
            val itemDecoration = DividerItemDecoration(this@ProductListActivity, LinearLayoutManager.VERTICAL)
            itemDecoration.setDrawable(ContextCompat.getDrawable(this@ProductListActivity, R.drawable.divider))
            addItemDecoration(itemDecoration)
        }
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

    /*
    *
    * View Functions
    * */
    override fun showProgressBar(bool: Boolean) {
        if (bool) apl_progress_bar.visibility = View.VISIBLE
        else apl_progress_bar.visibility = View.INVISIBLE
    }

    override fun setAdater(dataset: MutableList<Product>, totalProducts: Int) {
        apl_recyler_view.apply {
            val adapter = ListAdapter(dataset, totalProducts)
            this.adapter = adapter
            addOnScrollListener(ScrollListener(LinearLayoutManager(context), adapter))
        }

    }

}
