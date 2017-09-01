package com.webhopers.medorder.profile

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.webhopers.medorder.R
import com.webhopers.medorder.updateProfile.*
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
        addButtonListeners()
    }

    private fun setUpToolbar() {
        setSupportActionBar(ap_toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun addButtonListeners() {
        ap_edit_fullname_btn.setOnClickListener {startActivity(Intent(this, UpdateFullnameActivity::class.java))}
        ap_edit_email_btn.setOnClickListener { startActivity(Intent(this, UpdateEmailActivity::class.java)) }
        ap_edit_phone_no_btn.setOnClickListener { startActivity(Intent(this, UpdatePhoneActivity::class.java)) }
        ap_edit_gst_btn.setOnClickListener { startActivity(Intent(this, UpdateGSTActivity::class.java)) }
        ap_edit_drgl_btn.setOnClickListener { startActivity(Intent(this, UpdateDrugLicenceActivity::class.java)) }
        ap_edit_address_btn.setOnClickListener { startActivity(Intent(this, UpdateAddressActivity::class.java)) }
        ap_edit_pan_no_btn.setOnClickListener { startActivity(Intent(this, UpdatePANActivity::class.java)) }
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
        ap_pan_no_view.text = customer.panNo

    }




}
