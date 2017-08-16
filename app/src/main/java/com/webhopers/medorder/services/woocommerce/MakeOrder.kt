package com.webhopers.medorder.services.woocommerce

import com.webhopers.medorder.models.LineItem
import com.webhopers.medorder.models.Order
import com.webhopers.medorder.models.ProductF

class MakeOrder(items: ArrayList<ProductF>) {
    val list: ArrayList<LineItem> = ArrayList<LineItem>()
    init {
        items.forEach {
            list.add(LineItem().apply {
                productId = it.id!!.toInt()
                quantity = it.orderedQuantity!!.toInt()
            })
        }
    }

    fun done(): Order {
        return Order().apply {
            paymentMethod = "Bank Transfer"
            paymentMethodTitle = "Bank Transfer"
            setPaid = false
            lineItems = list
        }
    }
}