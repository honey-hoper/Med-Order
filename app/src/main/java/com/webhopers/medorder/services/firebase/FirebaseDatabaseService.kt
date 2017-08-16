package com.webhopers.medorder.services.firebase

import com.google.firebase.database.FirebaseDatabase
import com.webhopers.medorder.models.ProductF

class FirebaseDatabaseService {

    companion object {
        val PATH_CART = "cart"

        val firebaseDatabase by lazy { FirebaseDatabase.getInstance() }

        fun addToCart(userId: String, productF: ProductF) {
            firebaseDatabase.getReference(PATH_CART)
                    .child(userId)
                    .child(productF.id)
                    .setValue(productF)
        }

        fun removeFromCart(userId: String, productId: String) {
            firebaseDatabase.getReference(PATH_CART)
                    .child(userId)
                    .child(productId)
                    .removeValue()
        }
    }
}