package com.webhopers.medorder.dialogs

import android.content.Context
import android.support.v7.app.AlertDialog
import com.webhopers.medorder.R
import kotlinx.android.synthetic.main.quantity_picker_dialog.*


class QuantityPickerDialog (context: Context, quantity: Int) {
    init {
        AlertDialog.Builder(context)
                .setView(R.layout.quantity_picker_dialog)
                .create()
                .apply {
                    show()
                    qpd_num_picker.maxValue = quantity
                    qpd_cancel_btn.setOnClickListener { dismiss() }
                    qpd_done_btn.setOnClickListener {
                        val v = qpd_num_picker.value
                        if (v == 0)
                            return@setOnClickListener
                        println(v)
                        dismiss()
                    }

                }

    }
}