package com.webhopers.medorder.productList

import com.webhopers.medorder.fake.fakeServices.ProductService
import com.webhopers.medorder.interfaces.Presenter
import com.webhopers.medorder.interfaces.View
import com.webhopers.medorder.models.Product

class ProductListPresenter(val view: ProductListView): Presenter {
    val dataset: List<Product>
    init {
        dataset = ProductService.getProducts()
        view.setAdater(dataset)
    }
}

interface ProductListView: View {
    fun setAdater(dataset: List<Product>)
}