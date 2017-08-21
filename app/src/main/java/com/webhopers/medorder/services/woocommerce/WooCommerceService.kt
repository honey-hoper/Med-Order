package com.webhopers.medorder.services.woocommerce

import com.webhopers.medorder.models.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface WooCommerceService {

    @GET("products")
    fun getProducts(
            @Query("per_page") perPage: String = "50",
            @Query("offset") offset: String = "0",
            @Query("order") order: String = "asc",
            @Query("orderby") orderBy: String = "title"): Call<List<Product>>


    @POST("orders")
    fun createOrder(@Body body: Order): Call<OrderResponse>

    @POST("customers")
    fun createCustomer(@Body body: Customer): Call<CustomerResponse>

}