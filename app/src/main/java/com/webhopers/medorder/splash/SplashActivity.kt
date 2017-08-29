package com.webhopers.medorder.splash

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.webhopers.medorder.R
import com.webhopers.medorder.models.ProductCategory
import com.webhopers.medorder.productList.ProductListActivity
import com.webhopers.medorder.services.retrofit.WooCommerceRetrofitClient
import com.webhopers.medorder.services.woocommerce.WooCommerceService
import kotlinx.android.synthetic.main.activity_splash.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.Serializable

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        showProgressBar(true)
        WooCommerceRetrofitClient.cacheDir = cacheDir
        WooCommerceRetrofitClient.retrofit.create(WooCommerceService::class.java)
                .getProductCategories()
                .enqueue(object : Callback<List<ProductCategory>> {
                    override fun onFailure(call: Call<List<ProductCategory>>, t: Throwable) {
                        showProgressBar(false)
                        Toast.makeText(this@SplashActivity, "Network Error", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<List<ProductCategory>>, response: Response<List<ProductCategory>>) {
                        showProgressBar(false)
                        startProductListActivity(response.body()!!)
                    }
                })
    }

    fun showProgressBar(bool: Boolean) {
        if (bool) as_progress_bar.visibility = View.VISIBLE
        else as_progress_bar.visibility = View.GONE
    }

    fun startProductListActivity(list: List<ProductCategory>) {
        val intent = Intent(this, ProductListActivity::class.java)
        intent.putExtra("PRODUCT_CAT", list as Serializable)
        startActivity(intent)
        finish()
    }
}
