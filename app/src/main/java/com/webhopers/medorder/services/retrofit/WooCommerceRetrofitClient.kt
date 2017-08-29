package com.webhopers.medorder.services.retrofit

import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

object WooCommerceRetrofitClient {
    val INNOVEXIA_BASE_URL = "http://www.innovexia.com/wp-json/wc/v2/"
    lateinit var cacheDir: File
    val retrofit by lazy {
        val oauth1WooCommerce = OAuthInterceptor()
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val cacheSize: Long = 10 * 1024 * 1024
        val cache = Cache(cacheDir, cacheSize)

        val client = OkHttpClient.Builder()
                .cache(cache)
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .addInterceptor(oauth1WooCommerce)
                .hostnameVerifier { hostname, session -> return@hostnameVerifier true }
                .build()


        Retrofit.Builder()
                .baseUrl(INNOVEXIA_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

    }
}