package com.webhopers.medorder.services.woocommerce

import com.webhopers.medorder.models.Order
import com.webhopers.medorder.models.OrderResponse
import com.webhopers.medorder.models.Product
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface WooCommerceService {

    @GET("products")
    fun getProducts(
            @Query("per_page") perPage: String = "100",
            @Query("offset") offset: String = "0",
            @Query("order") order: String = "asc",
            @Query("orderby") orderBy: String = "title"): Call<List<Product>>


    @POST("orders")
    fun createOrder(@Body body: Order): Call<OrderResponse>

    @GET("products/count")
    fun getProductCount(): Call<Int>
}