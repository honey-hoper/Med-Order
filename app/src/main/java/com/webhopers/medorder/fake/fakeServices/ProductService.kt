package com.webhopers.medorder.fake.fakeServices

import com.webhopers.medorder.models.Product
import java.util.*

object ProductService {

    private val productNames = arrayListOf<String>(
            "Acesoft-MR",
            "Artifix",
            "Calmunch",
            "Cefvent-S 1.5",
            "Contimax",
            "Candiwiz-150",
            "Dailyrich",
            "Defmune 6",
            "Gored XT",
            "Glimisoft Mi",
            "Gut Ultra",
            "Grimtus CS",
            "Histamax",
            "Histamune-M",
            "Mefpure-P",
            "Neuroxia",
            "Nixim CV",
            "Nudec 25",
            "Trupod 100 DT",
            "Telmisoft 40",
            "ZGO"
    )

    private val productPrices = arrayListOf<String>(
            "100",
            "1250",
            "49",
            "34.50",
            "499",
            "60",
            "72",
            "108",
            "199",
            "25",
            "300",
            "40"
    )

    private val productQuantities = arrayListOf<String>(
            "54",
            "12",
            "98",
            "143",
            "432",
            "55",
            "2",
            "0",
            "85",
            "36",
            "123",
            "78",
            "235",
            "97"
    )

    fun getProducts(): List<Product> {
        val rand = Random()
        val products = List<Product>(productNames.size) {
            val product = Product()
            product.name = productNames[it]
            product.price = productPrices[rand.nextInt(productPrices.size)]
            product.quantity = productQuantities[rand.nextInt(productQuantities.size)]
            product
        }
        return products
    }
}