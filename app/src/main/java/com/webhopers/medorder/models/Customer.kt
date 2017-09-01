package com.webhopers.medorder.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Customer (
    @SerializedName("username")
    @Expose
    var username: String? = null,
    @SerializedName("email")
    @Expose
    var email: String? = null,
    @SerializedName("first_name")
    @Expose
    var firstName: String? = null,
    @SerializedName("last_name")
    @Expose
    var lastName: String? = null,
    @SerializedName("phone")
    @Expose
    var phone: String? = null,
    @SerializedName("password")
    @Expose
    var password: String? = null,
    @SerializedName("billing")
    @Expose
    var billing: Billing? = null,
    @SerializedName("shipping")
    @Expose
    var shipping: Shipping? = null,
    @SerializedName("meta_data")
    @Expose
    var metaData: List<MetaData>? = null

)

class CustomerResponse {
    @SerializedName("id")
    @Expose
    var id: Long? = null
    @SerializedName("email")
    @Expose
    var email: String? = null
    @SerializedName("password")
    @Expose
    var password: String? = null
}


class Billing {

    @SerializedName("first_name")
    @Expose
    var firstName: String? = null
    @SerializedName("last_name")
    @Expose
    var lastName: String? = null
    @SerializedName("company")
    @Expose
    var company: String? = null
    @SerializedName("address_1")
    @Expose
    var address1: String? = null
    @SerializedName("address_2")
    @Expose
    var address2: String? = null
    @SerializedName("city")
    @Expose
    var city: String? = null
    @SerializedName("state")
    @Expose
    var state: String? = null
    @SerializedName("postcode")
    @Expose
    var postcode: String? = null
    @SerializedName("country")
    @Expose
    var country: String? = null
    @SerializedName("email")
    @Expose
    var email: String? = null
    @SerializedName("phone")
    @Expose
    var phone: String? = null

}

class Shipping {

    @SerializedName("first_name")
    @Expose
    var firstName: String? = null
    @SerializedName("last_name")
    @Expose
    var lastName: String? = null
    @SerializedName("company")
    @Expose
    var company: String? = null
    @SerializedName("address_1")
    @Expose
    var address1: String? = null
    @SerializedName("address_2")
    @Expose
    var address2: String? = null
    @SerializedName("city")
    @Expose
    var city: String? = null
    @SerializedName("state")
    @Expose
    var state: String? = null
    @SerializedName("postcode")
    @Expose
    var postcode: String? = null
    @SerializedName("country")
    @Expose
    var country: String? = null

}


class MetaData {
    @SerializedName("key")
    @Expose
    var key: String? = null
    @SerializedName("value")
    @Expose
    var value: String? = null
}

class CustomerSP (
        val username: String,
        val fullname: String,
        val address: String,
        val email: String,
        val gstNo: String,
        val drugLicense: String,
        val phoneNo: String
)
