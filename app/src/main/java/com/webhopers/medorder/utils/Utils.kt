package com.webhopers.medorder.utils

import android.content.Context
import android.content.res.Resources
import android.net.ConnectivityManager
import android.util.TypedValue

fun isConnected(context: Context): Boolean {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return cm.activeNetworkInfo != null && cm.activeNetworkInfo.isConnected
}

fun convertDpToPixels(dip: Float, r: Resources) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, r.displayMetrics)