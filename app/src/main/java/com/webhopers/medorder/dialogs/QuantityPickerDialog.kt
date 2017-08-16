package com.webhopers.medorder.dialogs

import android.content.Context
import android.support.v7.app.AlertDialog
import com.webhopers.medorder.R
import com.webhopers.medorder.models.Product
import com.webhopers.medorder.models.ProductF
import com.webhopers.medorder.services.firebase.FirebaseDatabaseService
import kotlinx.android.synthetic.main.quantity_picker_dialog.*


class QuantityPickerDialog (context: Context, product: Product) {
    init {
        AlertDialog.Builder(context)
                .setView(R.layout.quantity_picker_dialog)
                .create()
                .apply {
                    show()
                    qpd_num_picker.minValue = 1
                    qpd_num_picker.maxValue = if (product.quantity != null) {
                        product.quantity!!.toInt()
                    } else { 10 }
                    qpd_cancel_btn.setOnClickListener { dismiss() }
                    qpd_done_btn.setOnClickListener {
                        val id = product.id
                        val name = product.name
                        val orderedQuantity = qpd_num_picker.value
                        val totalPrice = if (!product.price.isNullOrBlank()) {
                            product.price!!.toInt() * orderedQuantity
                        } else { -1 }

                        val productF = ProductF(id, name, orderedQuantity.toLong(), totalPrice.toLong())
                        FirebaseDatabaseService.addToCart("user_id", productF)
                        dismiss()
                    }

                }

    }
}