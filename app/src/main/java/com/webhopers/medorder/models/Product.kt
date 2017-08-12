package com.webhopers.medorder.models

import java.io.Serializable


data class Product(var name: String? = null,
                   var price: String? = null,
                   var quantity: String? = null,
                   var desc: String? = null) : Serializable