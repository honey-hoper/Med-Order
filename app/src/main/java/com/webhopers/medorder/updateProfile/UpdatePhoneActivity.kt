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
import kotlinx.android.synthetic.main.activity_update_phone.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdatePhoneActivity : AppCompatActivity() {

    lateinit var phoneField: TextInputEditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_phone)

        initUI()

        phoneField = aup_phone_no_field
        aup_btn.setOnClickListener { onUpdate() }
    }

    private fun initUI() {
        setUpToolbar()
    }

    private fun setUpToolbar() {
        setSupportActionBar(aup_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun onUpdate() {
        val phone = phoneField.text.toString().trim()

        if (phone.isNullOrBlank()) {
            phoneField.error = "Empty"
            return
        }

        showProgressBar(true)

        val customer = Customer().apply {
            this.metaData = listOf(MetaData().apply {
                this.key = "phone1"
                this.value = phone
            })
        }

        WooCommerceRetrofitClient.retrofit.create(WooCommerceService::class.java)
                .updateCustomer(body = customer)
                .enqueue(object : Callback<CustomerResponse> {
                    override fun onResponse(call: Call<CustomerResponse>, response: Response<CustomerResponse>) {
                        showProgressBar(false)
                        updatePhoneNumber(phone)
                        makeToast("Phone Number Updated")
                    }

                    override fun onFailure(call: Call<CustomerResponse>, t: Throwable) {
                        showProgressBar(false)
                        makeToast("Failed")
                    }
                })
    }

    fun showProgressBar(bool: Boolean) {
        if (bool) aup_progress_bar.visibility = View.VISIBLE
        else aup_progress_bar.visibility = View.INVISIBLE
    }

    fun makeToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun updatePhoneNumber(phone: String) {
        val preferences = getSharedPreferences(getString(R.string.user_details_file), Context.MODE_PRIVATE)
        preferences.edit().putString(getString(R.string.phone_no_key), phone).apply()
    }
}
