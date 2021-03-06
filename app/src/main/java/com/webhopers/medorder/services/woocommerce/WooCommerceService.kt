package com.webhopers.medorder.services.woocommerce

import com.webhopers.medorder.models.*
import retrofit2.Call
import retrofit2.http.*

interface WooCommerceService {

    @GET("products")
    fun getProducts(
            @Query("per_page") perPage: String = "50",
            @Query("offset") offset: String = "0",
            @Query("order") order: String = "asc",
            @Query("orderby") orderBy: String = "title"): Call<List<Product>>

    @GET("products")
    fun getProductsByCategory(
            @Query("category") category: String,
            @Query("per_page") perPage: String = "100",
            @Query("order") order: String = "asc",
            @Query("orderby") orderBy: String = "title"
    ): Call<List<Product>>

    @GET("products/categories")
    fun getProductCategories(
            @Query("per_page") perPage: String = "100",
            @Query("order") order: String = "asc"
    ): Call<List<ProductCategory>>


    @POST("orders")
    fun createOrder(@Body body: Order): Call<OrderResponse>

    @GET("orders")
    fun getOrderByCustomerId(@Query("customer") customerId: Long = 18): Call<List<OrderResponse>>

    @POST("customers")
    fun createCustomer(@Body body: Customer): Call<CustomerResponse>

    @POST("customers/{id}")
    fun updateCustomer(@Path("id") id: String = "18", @Body body: Customer): Call<CustomerResponse>

}