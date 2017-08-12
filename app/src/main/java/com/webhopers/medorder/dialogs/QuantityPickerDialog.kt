package com.webhopers.medorder.dialogs

import android.content.Context
import android.support.v7.app.AlertDialog
import com.webhopers.medorder.R


class QuantityPickerDialog (context: Context, quantity: Int) {
    init {
        AlertDialog.Builder(context)
                .setView(R.layout.quantity_picker_dialog)
                .create()
                .show()

    }
}