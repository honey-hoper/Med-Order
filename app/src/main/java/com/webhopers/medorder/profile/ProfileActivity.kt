package com.webhopers.medorder.profile

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.webhopers.medorder.R
import com.webhopers.medorder.utils.getCustomerDetails
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        initUI()
    }

    private fun initUI() {
        setUpToolbar()
    }

    private fun setUpToolbar() {
        setSupportActionBar(ap_toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onStart() {
        super.onStart()

        initUI2()
    }

    private fun initUI2() {
        val customer = getCustomerDetails(this)

        ap_username_view.text = customer.username
        ap_fullname_view.text = customer.fullname
        ap_email_view.text = customer.email
        ap_address_view.text = customer.address
        ap_gst_view.text = customer.gstNo
        ap_drgl_view.text = customer.drugLicense
        ap_phone_no_view.text = customer.phoneNo

    }




}
