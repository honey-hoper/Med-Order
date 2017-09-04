package com.webhopers.medorder.updateProfile

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.view.View
import android.widget.Toast
import com.webhopers.medorder.R
import com.webhopers.medorder.models.Customer
import com.webhopers.medorder.models.CustomerResponse
import com.webhopers.medorder.models.MetaData
import com.webhopers.medorder.services.retrofit.WooCommerceRetrofitClient
import com.webhopers.medorder.services.woocommerce.WooCommerceService
import kotlinx.android.synthetic.main.activity_update_pan.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdatePANActivity : AppCompatActivity() {

    lateinit var panField: TextInputEditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_pan)

        initUI()

        panField = au_pan_pan_field
        au_pan_btn.setOnClickListener { onUpdate() }
    }

    private fun initUI() {
        setField()
        setUpToolbar()
    }

    private fun setField() {
        val PAN = intent.getStringExtra("DATA")
        au_pan_pan_field.setText(PAN)
    }

    private fun setUpToolbar() {
        setSupportActionBar(au_pan_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun onUpdate() {
        val panNo = panField.text.toString().trim()

        if (panNo.isNullOrBlank()) {
            panField.error = "Empty"
            return
        }

        showProgressBar(true)

        val customer = Customer().apply {
            this.metaData = listOf(MetaData().apply {
                this.key = "pan_no"
                this.value = panNo
            })
        }

        WooCommerceRetrofitClient.retrofit.create(WooCommerceService::class.java)
                .updateCustomer(body = customer)
                .enqueue(object : Callback<CustomerResponse> {
                    override fun onResponse(call: Call<CustomerResponse>, response: Response<CustomerResponse>) {
                        showProgressBar(false)
                        updatePAN(panNo)
                        makeToast("PAN Number Updated")
                    }

                    override fun onFailure(call: Call<CustomerResponse>, t: Throwable) {
                        showProgressBar(false)
                        makeToast("Failed")
                    }
                })
    }

    fun showProgressBar(bool: Boolean) {
        if (bool) au_pan_progress_bar.visibility = View.VISIBLE
        else au_pan_progress_bar.visibility = View.INVISIBLE
    }

    fun makeToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun updatePAN(PAN: String) {
        val preferences = getSharedPreferences(getString(R.string.user_details_file), Context.MODE_PRIVATE)
        preferences.edit().putString(getString(R.string.pan_no_key), PAN).apply()
    }
}
