package com.webhopers.medorder.cart

import com.webhopers.medorder.fake.fakeServices.ProductService
import com.webhopers.medorder.interfaces.View
import com.webhopers.medorder.models.Product

class CartPresenter(val view: CartView) {
    val dataset: MutableList<Product>
    init {
        dataset = ProductService.getProducts().toMutableList()
        view.setAdapter(dataset)
    }
}

interface CartView: View {
    fun setAdapter(dataset: MutableList<Product>)
}