package com.webhopers.medorder.utils

import android.content.Context
import android.content.res.Resources
import android.net.ConnectivityManager
import android.util.TypedValue
import com.webhopers.medorder.R
import com.webhopers.medorder.models.CustomerSP

fun isConnected(context: Context): Boolean {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return cm.activeNetworkInfo != null && cm.activeNetworkInfo.isConnected
}

fun convertDpToPixels(dip: Float, r: Resources) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, r.displayMetrics)

fun getCustomerDetails(context: Context): CustomerSP {
    val preferences = context.getSharedPreferences(
            context.getString(R.string.user_details_file), Context.MODE_PRIVATE)

    val username = preferences.getString(context.getString(R.string.username_key), "")
    val fullname = preferences.getString(context.getString(R.string.fullname_key), "")
    val address = preferences.getString(context.getString(R.string.address_key), "")
    val email = preferences.getString(context.getString(R.string.email_key), "")
    val gstNo = preferences.getString(context.getString(R.string.gst_no_key), "")
    val drugLicense = preferences.getString(context.getString(R.string.drug_licence_key), "")
    val phoneNo = preferences.getString(context.getString(R.string.phone_no_key), "")
    val panNo = preferences.getString(context.getString(R.string.pan_no_key), "")

    return CustomerSP(username, fullname, address, email, gstNo, drugLicense, phoneNo, panNo)
}