package com.webhopers.medorder.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class Product(
        @SerializedName("name")
        @Expose
        var name: String? = null,
        @SerializedName("price")
        @Expose
        var price: String? = null,
        @SerializedName("stock_quantity")
        @Expose
        var quantity: String? = null,
        @SerializedName("short_description")
        @Expose
        var desc: String? = null,
        @SerializedName("images")
        @Expose
        var images: List<Image>? = null,
        @SerializedName("attributes")
        @Expose
        var attributes: List<Attribute>? = null
) : Serializable

class Image(
        @SerializedName("src")
        @Expose
        var src: String? = null
) : Serializable

class Attribute(
        @SerializedName("options")
        @Expose
        var options: List<String>? = null
) : Serializable
