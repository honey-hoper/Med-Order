package com.webhopers.medorder.myOrders

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.webhopers.medorder.R
import com.webhopers.medorder.adapters.MyOrdersAdapter
import com.webhopers.medorder.models.OrderResponse
import com.webhopers.medorder.services.retrofit.WooCommerceRetrofitClient
import com.webhopers.medorder.services.woocommerce.WooCommerceService
import kotlinx.android.synthetic.main.activity_my_orders.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyOrdersActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_orders)

        initUI()

        WooCommerceRetrofitClient.retrofit.create(WooCommerceService::class.java)
                .getOrderByCustomerId()
                .enqueue(object : Callback<List<OrderResponse>> {
                    override fun onResponse(call: Call<List<OrderResponse>>, response: Response<List<OrderResponse>>) {
                        if (response.isSuccessful) {
                            showProgressBar(false)
                            val body = response.body()!!
                            amo_recycler_view.layoutManager = LinearLayoutManager(this@MyOrdersActivity)
                            amo_recycler_view.adapter = MyOrdersAdapter(body)
                        } else println(response.code())
                    }

                    override fun onFailure(call: Call<List<OrderResponse>>, t: Throwable) {
                        showProgressBar(false)
                        println(t.message)
                    }
                })
    }

    private fun initUI() {
        setSupportActionBar(amo_toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
        showProgressBar(true)
    }

    fun showProgressBar(bool: Boolean) {
        if (bool) amo_progress_bar.visibility = View.VISIBLE
        else amo_progress_bar.visibility = View.GONE
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
