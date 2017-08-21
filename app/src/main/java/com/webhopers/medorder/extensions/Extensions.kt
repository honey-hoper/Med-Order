package com.webhopers.medorder.extensions

import android.widget.EditText

fun EditText.value(): String {
    return text.toString().trim()
}