package com.webhopers.medorder.productList


import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import com.webhopers.medorder.R
import com.webhopers.medorder.adapters.ListAdapter
import com.webhopers.medorder.cart.CartActivity
import com.webhopers.medorder.models.Product
import com.webhopers.medorder.models.ProductCategory
import com.webhopers.medorder.myOrders.MyOrdersActivity
import kotlinx.android.synthetic.main.activity_product_list.*
import kotlinx.android.synthetic.main.nav_list_item.view.*

class ProductListActivity :
        ProductListView,
        AppCompatActivity() {

    lateinit var drawerToggle: ActionBarDrawerToggle

    lateinit var map: Map<String, String>
    lateinit var presenter: ProductListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)

        presenter = ProductListPresenter(this)
        val cat = intent.getSerializableExtra("PRODUCT_CAT") as? List<ProductCategory>
        if (cat != null) {
            map = cat.associateBy(keySelector = {it.name!!}, valueTransform = {it.id!! })
        }

        drawerToggle = ActionBarDrawerToggle(this, apl_drawer, R.string.open_drawer, R.string.close_drawer)

        initUI()
    }

    private fun initUI() {
        setUpNavDrawer()
        setUpRecyclerView()
        setSupportActionBar(apl_toolbar)
        setUpActionBarDrawerToggle()
    }

    private fun setUpNavDrawer() {
        apl_nav_list_view.adapter = ArrayAdapter(this, R.layout.nav_list_item, R.id.nli_cat_name, map.map { it.key })
        apl_nav_list_view.setOnItemClickListener { adapterView, view, i, l ->
            val id = map.get(view.nli_cat_name.text.toString())!!
            apl_drawer.closeDrawers()
            presenter.changeAdapter(id)
        }

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

    private fun setUpActionBarDrawerToggle() {
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        drawerToggle.syncState()
        apl_drawer.addDrawerListener(drawerToggle)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (drawerToggle.onOptionsItemSelected(item)) return true
        val id = item.itemId
        when (id) {
            R.id.action_open_cart -> startCartActivity()
            R.id.action_my_orders -> startMyOrdersActivity()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun startCartActivity() {
        startActivity(Intent(this, CartActivity::class.java))
    }

    private fun startMyOrdersActivity() {
        startActivity(Intent(this, MyOrdersActivity::class.java))
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
            //addOnScrollListener(ScrollListener(LinearLayoutManager(context), adapter))
        }

    }

}
