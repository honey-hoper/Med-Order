package com.webhopers.medorder.interfaces

import java.io.Serializable

interface View {
    fun showProgressBar(bool: Boolean) {}
    fun startActivity(activityClass: Class<*>, data: Serializable? = null) {}
}