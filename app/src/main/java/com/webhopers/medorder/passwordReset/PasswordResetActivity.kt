package com.webhopers.medorder.passwordReset

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
import kotlinx.android.synthetic.main.activity_password_reset.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PasswordResetActivity : AppCompatActivity() {

    lateinit var passwordField: TextInputEditText
    lateinit var confirmPasswordField: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_reset)

        initUI()

        passwordField = apr_pswd_field
        confirmPasswordField = apr_cnfrm_pswd_field

        apr_btn.setOnClickListener { onReset() }
    }

    private fun initUI() {
        setSupportActionBar(apr_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun onReset() {
        val password = passwordField.text.toString().trim()
        val confirmPassword = confirmPasswordField.text.toString().trim()

        if (password.isNullOrBlank()) {
            passwordField.error = "Empty"
            return
        }

        if (confirmPassword.isNullOrBlank()) {
            confirmPasswordField.error = "Empty"
            return
        }

        if (confirmPassword != password) {
            confirmPasswordField.error = "Password incorrect!"
            return
        }

        val customer = Customer().apply {
            this.password = password
        }

        showProgressBar(true)
        WooCommerceRetrofitClient.retrofit.create(WooCommerceService::class.java)
                .updateCustomer(body = customer)
                .enqueue(object: Callback<CustomerResponse> {
                    override fun onFailure(call: Call<CustomerResponse>, t: Throwable) {
                        showProgressBar(false)
                        makeToast("Failed")
                    }

                    override fun onResponse(call: Call<CustomerResponse>, response: Response<CustomerResponse>) {
                        showProgressBar(false)
                        makeToast("Password Changed")
                        clearFields()

                    }
                })


    }

    fun showProgressBar(bool: Boolean) {
        if (bool) apr_progress_bar.visibility = View.VISIBLE
        else apr_progress_bar.visibility = View.INVISIBLE
    }

    fun makeToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun clearFields() {
        passwordField.setText("")
        confirmPasswordField.setText("")
    }
}
