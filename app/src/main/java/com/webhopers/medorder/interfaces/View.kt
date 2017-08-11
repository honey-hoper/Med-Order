package com.webhopers.medorder.interfaces

interface View {
    fun showProgressBar(bool: Boolean) {}
    fun startActivity(activityClass: Class<*>) {}
}