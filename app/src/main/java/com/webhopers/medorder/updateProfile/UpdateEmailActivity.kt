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
import com.webhopers.medorder.services.retrofit.WooCommerceRetrofitClient
import com.webhopers.medorder.services.woocommerce.WooCommerceService
import kotlinx.android.synthetic.main.activity_update_email.*
import kotlinx.android.synthetic.main.activity_update_fullname.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateEmailActivity : AppCompatActivity() {

    lateinit var emailField: TextInputEditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_email)

        initUI()

        emailField = aue_email_field
        aue_btn.setOnClickListener { onUpdate() }
    }

    private fun initUI() {
        setUpToolbar()
    }

    private fun setUpToolbar() {
        setSupportActionBar(aue_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun onUpdate() {
        val email = emailField.text.toString().trim()

        if (email.isNullOrBlank()) {
            emailField.error = "Empty"
            return
        }

        showProgressBar(true)

        val customer = Customer().apply {
            this.email = email
        }

        WooCommerceRetrofitClient.retrofit.create(WooCommerceService::class.java)
                .updateCustomer(body = customer)
                .enqueue(object : Callback<CustomerResponse> {
                    override fun onResponse(call: Call<CustomerResponse>, response: Response<CustomerResponse>) {
                        showProgressBar(false)
                        updateFullName(email)
                        makeToast("Email Updated")
                    }

                    override fun onFailure(call: Call<CustomerResponse>, t: Throwable) {
                        showProgressBar(false)
                        makeToast("Failed")
                    }
                })
    }

    fun showProgressBar(bool: Boolean) {
        if (bool) aue_progress_bar.visibility = View.VISIBLE
        else aue_progress_bar.visibility = View.INVISIBLE
    }

    fun makeToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun updateFullName(fullname: String) {
        val preferences = getSharedPreferences(getString(R.string.user_details_file), Context.MODE_PRIVATE)
        preferences.edit().putString(getString(R.string.email_key), fullname).apply()
    }
}
