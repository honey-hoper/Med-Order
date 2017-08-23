package com.webhopers.medorder.myOrders

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
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

        WooCommerceRetrofitClient.retrofit.create(WooCommerceService::class.java)
                .getOrderByCustomerId()
                .enqueue(object : Callback<List<OrderResponse>> {
                    override fun onResponse(call: Call<List<OrderResponse>>, response: Response<List<OrderResponse>>) {
                        if (response.isSuccessful) {
                            val body = response.body()!!
                            amo_recycler_view.layoutManager = LinearLayoutManager(this@MyOrdersActivity)
                            amo_recycler_view.adapter = MyOrdersAdapter(body)
                        } else println(response.code())
                    }

                    override fun onFailure(call: Call<List<OrderResponse>>, t: Throwable) {
                        println(t.message)
                    }
                })
    }
}
