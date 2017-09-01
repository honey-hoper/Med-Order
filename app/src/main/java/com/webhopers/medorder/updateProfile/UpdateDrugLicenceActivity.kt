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
import kotlinx.android.synthetic.main.activity_update_drug_licence.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateDrugLicenceActivity : AppCompatActivity() {

    lateinit var drugLicenceField: TextInputEditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_drug_licence)

        initUI()

        drugLicenceField = audl_dl_field
        audl_btn.setOnClickListener { onUpdate() }
    }

    private fun initUI() {
        setUpToolbar()
    }

    private fun setUpToolbar() {
        setSupportActionBar(audl_toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true

    }

    fun onUpdate() {
        val drugLicence = drugLicenceField.text.toString().trim()

        if (drugLicence.isNullOrBlank()) {
            drugLicenceField.error = "Empty"
            return
        }

        showProgressBar(true)

        val customer = Customer().apply {
            this.metaData = listOf(MetaData().apply {
                this.key = "drug_licence"
                this.value = drugLicence
            })
        }

        WooCommerceRetrofitClient.retrofit.create(WooCommerceService::class.java)
                .updateCustomer(body = customer)
                .enqueue(object : Callback<CustomerResponse> {
                    override fun onResponse(call: Call<CustomerResponse>, response: Response<CustomerResponse>) {
                        showProgressBar(false)
                        updateDrugLicence(drugLicence)
                        makeToast("Drug License Updated")
                    }

                    override fun onFailure(call: Call<CustomerResponse>, t: Throwable) {
                        showProgressBar(false)
                        makeToast("Failed")
                    }
                })
    }

    fun showProgressBar(bool: Boolean) {
        if (bool) audl_progress_bar.visibility = View.VISIBLE
        else audl_progress_bar.visibility = View.INVISIBLE
    }

    fun makeToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun updateDrugLicence(drugLicence: String) {
        val preferences = getSharedPreferences(getString(R.string.user_details_file), Context.MODE_PRIVATE)
        preferences.edit().putString(getString(R.string.drug_licence_key), drugLicence).apply()
    }
}
