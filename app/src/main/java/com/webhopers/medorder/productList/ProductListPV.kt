package com.webhopers.medorder.productList

import android.util.Log
import com.webhopers.medorder.fake.fakeServices.ProductService
import com.webhopers.medorder.interfaces.Presenter
import com.webhopers.medorder.interfaces.View
import com.webhopers.medorder.models.Product
import com.webhopers.medorder.services.retrofit.WooCommerceRetrofitClient
import com.webhopers.medorder.services.woocommerce.WooCommerceService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductListPresenter(val view: ProductListView): Presenter {
    lateinit var dataset: List<Product>
    init {
//        dataset = ProductService.getProducts()
//        view.setAdater(dataset)
        view.showProgressBar(true)
        WooCommerceRetrofitClient.retrofit.create(WooCommerceService::class.java)
                .getProducts()
                .enqueue(object :  Callback<List<Product>> {
                    override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                        view.showProgressBar(false)
                        if (response.isSuccessful) {
                            dataset = response.body()!!
                            view.setAdater(dataset)
                        } else Log.d("Error", "${response.code()}")
                    }

                    override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                        view.showProgressBar(false)
                        Log.d("Error", t.message)
                    }
                })

    }
}

interface ProductListView: View {
    fun setAdater(dataset: List<Product>)
}