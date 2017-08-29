package com.webhopers.medorder.services.firebase

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.webhopers.medorder.models.ProductF
import io.reactivex.Observable
import io.reactivex.Single

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

        fun cartListener(userId: String): Observable<Boolean> {
            return Observable.create<Boolean> {e ->
                firebaseDatabase.getReference(PATH_CART)
                        .child(userId)
                        .addValueEventListener(object : ValueEventListener {
                            override fun onCancelled(error: DatabaseError) {
                                e.onError(Throwable("Cancelled"))
                            }

                            override fun onDataChange(snapshot: DataSnapshot) {
                                if (snapshot.hasChildren())
                                    e.onNext(false)
                                else
                                    e.onNext(true)
                            }

                        })
            }
        }

        fun getCartItems(userId: String): Single<ArrayList<ProductF>?> {
            return Single.create<ArrayList<ProductF>> { e ->
                firebaseDatabase.getReference(PATH_CART)
                        .child(userId)
                        .addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onCancelled(error: DatabaseError) {
                                e.onError(Throwable("Cancelled"))
                            }

                            override fun onDataChange(snapshot: DataSnapshot) {
                                if (snapshot.hasChildren()) {
                                    val list = ArrayList<ProductF>()
                                    snapshot.children.forEach { list.add(it.getValue(ProductF::class.java)!!) }
                                    e.onSuccess(list)
                                }
                            }
                        })

            }

        }

        fun emptyCart(userId: String) {
            firebaseDatabase.getReference(PATH_CART)
                    .child(userId)
                    .removeValue()
        }

    }
}