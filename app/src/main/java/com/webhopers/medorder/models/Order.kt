package com.webhopers.medorder.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Order {
    @SerializedName("customer_id")
    @Expose
    var customerId: Long = 18
    @SerializedName("payment_method")
    @Expose
    var paymentMethod: String? = null
    @SerializedName("payment_method_title")
    @Expose
    var paymentMethodTitle: String? = null
    @SerializedName("set_paid")
    @Expose
    var setPaid: Boolean? = null
    @SerializedName("line_items")
    @Expose
    var lineItems: List<LineItem>? = null
}

class LineItem : Serializable{
    @SerializedName("product_id")
    @Expose
    var productId: Int? = null
    @SerializedName("name")
    @Expose
    var productName: String? = null
    @SerializedName("quantity")
    @Expose
    var quantity: Int? = null
}

class OrderResponse : Serializable{

    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("number")
    @Expose
    var orderNumber: String? = null
    @SerializedName("date_created")
    @Expose
    var dateCreated: String? = null
    @SerializedName("status")
    @Expose
    var status: String? = null
    @SerializedName("total")
    @Expose
    var totalAmount: String? = null
    @SerializedName("total_tax")
    @Expose
    var totalTax: String? = null
    @SerializedName("line_items")
    @Expose
    var lineItems: List<LineItem>? = null
}
