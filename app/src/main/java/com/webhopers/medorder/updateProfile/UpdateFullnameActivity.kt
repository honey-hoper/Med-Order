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
import kotlinx.android.synthetic.main.activity_update_fullname.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateFullnameActivity : AppCompatActivity() {

    lateinit var firstNameField: TextInputEditText
    lateinit var lastNameField: TextInputEditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_fullname)

        initUI()

        firstNameField = auf_first_name_field
        lastNameField = auf_last_name_field
        auf_btn.setOnClickListener { onUpdate() }
    }

    private fun initUI() {
        setField()
        setUpToolbar()
    }

    private fun setField() {
        val fullname = intent.getStringExtra("DATA")
        val firstName = fullname.substringBeforeLast(" ")
        val lastName = fullname.substringAfterLast(" ")

        auf_first_name_field.setText(firstName)
        auf_last_name_field.setText(lastName)
    }

    private fun setUpToolbar() {
        setSupportActionBar(auf_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun onUpdate() {
        val firstName = firstNameField.text.toString().trim()
        val lastName = lastNameField.text.toString().trim()

        if (firstName.isNullOrBlank()) {
            firstNameField.error = "Empty"
            return
        }

        if (lastName.isNullOrBlank()) {
            lastNameField.error = "Empty"
            return
        }

        showProgressBar(true)

        val customer = Customer().apply {
            this.firstName = firstName
            this.lastName = lastName
        }

        WooCommerceRetrofitClient.retrofit.create(WooCommerceService::class.java)
                .updateCustomer(body = customer)
                .enqueue(object : Callback<CustomerResponse> {
                    override fun onResponse(call: Call<CustomerResponse>, response: Response<CustomerResponse>) {
                        showProgressBar(false)
                        updateFullName("$firstName $lastName")
                        makeToast("Name Updated")
                    }

                    override fun onFailure(call: Call<CustomerResponse>, t: Throwable) {
                        showProgressBar(false)
                        makeToast("Failed")
                    }
                })
    }

    fun showProgressBar(bool: Boolean) {
        if (bool) auf_progress_bar.visibility = View.VISIBLE
        else auf_progress_bar.visibility = View.INVISIBLE
    }

    fun makeToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun updateFullName(fullname: String) {
        val preferences = getSharedPreferences(getString(R.string.user_details_file), Context.MODE_PRIVATE)
        preferences.edit().putString(getString(R.string.fullname_key), fullname).apply()
    }
}
