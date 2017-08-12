package com.webhopers.medorder.productList

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.webhopers.medorder.R
import com.webhopers.medorder.adapters.ListAdapter
import com.webhopers.medorder.models.Product
import kotlinx.android.synthetic.main.activity_product_list.*
import java.io.Serializable

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

    /*
    *
    * View Functions
    * */
    override fun showProgressBar(bool: Boolean) {
        if (bool) apl_progress_bar.visibility = View.VISIBLE
        else apl_progress_bar.visibility = View.INVISIBLE
    }

    override fun setAdater(dataset: List<Product>) {
        apl_recyler_view.adapter = ListAdapter(dataset)
    }

}
