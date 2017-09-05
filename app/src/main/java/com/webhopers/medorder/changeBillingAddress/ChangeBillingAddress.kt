package com.webhopers.medorder.changeBillingAddress

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.view.View
import android.widget.CheckBox
import android.widget.Toast
import com.webhopers.medorder.R
import com.webhopers.medorder.models.Billing
import com.webhopers.medorder.models.Customer
import com.webhopers.medorder.models.CustomerResponse
import com.webhopers.medorder.services.retrofit.WooCommerceRetrofitClient
import com.webhopers.medorder.services.woocommerce.WooCommerceService
import com.webhopers.medorder.utils.getCustomerDetails
import kotlinx.android.synthetic.main.activity_change_billing_address.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangeBillingAddress : AppCompatActivity() {

    lateinit var addressField: TextInputEditText
    lateinit var cityField: TextInputEditText
    lateinit var stateField: TextInputEditText
    lateinit var zipField: TextInputEditText
    lateinit var countryField: TextInputEditText
    lateinit var checkBox: CheckBox


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_billing_address)

        initUI()

        addressField = acba_address_field
        cityField = acba_city_field
        stateField = acba_state_field
        zipField = acba_zip_field
        countryField = acba_country_field
        checkBox = acba_check_box
        acba_default_address.text = getCustomerDetails(this).address

        acba_btn.setOnClickListener { onUpdate() }
    }

    private fun initUI() {
        setUpToolbar()
    }

    private fun setUpToolbar() {
        setSupportActionBar(acba_toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun onUpdate() {
        if (checkBox.isChecked) useDefault()
        else changeBillingAddress()
    }

    fun useDefault() {
        val customerSP = getCustomerDetails(this)
        val text = customerSP.address

        val address = text.substringBefore("\n")

        val cityAndPin = text.substringAfter("\n").substringBefore("\n")
        val city = cityAndPin.substringBefore(",")
        val pin = cityAndPin.substringAfter(", ")

        val stateAndCountry = text.substringAfter("\n").substringAfter("\n")
        val state = stateAndCountry.substringBefore(",")
        val country = stateAndCountry.substringAfter(", ")

        val customer = Customer().apply {
            this.billing = Billing().apply {
                this.address1 = address
                this.city = city
                this.postcode = pin
                this.state = state
                this.country = country
                this.email = customerSP.email
                this.phone = customerSP.phoneNo
            }
        }

        WooCommerceRetrofitClient.retrofit.create(WooCommerceService::class.java)
                .updateCustomer(body = customer)
                .enqueue(object : Callback<CustomerResponse> {
                    override fun onResponse(call: Call<CustomerResponse>, response: Response<CustomerResponse>) {
                        showProgressBar(false)
                        updateAddress("$address\n$city, $pin\n$state, $country")
                        makeToast("Adrress Updated")
                    }

                    override fun onFailure(call: Call<CustomerResponse>, t: Throwable) {
                        showProgressBar(false)
                        makeToast("Failed")
                    }
                })
    }

    fun changeBillingAddress() {
        val customerSP = getCustomerDetails(this)


        val address = addressField.text.toString().trim()
        val city = cityField.text.toString().trim()
        val state = stateField.text.toString().trim()
        val zip = zipField.text.toString().trim()
        val country = countryField.text.toString().trim()

        if (address.isNullOrBlank()) {
            addressField.error = "Empty"
            return
        }

        if (city.isNullOrBlank()) {
            cityField.error = "Empty"
            return
        }

        if (state.isNullOrBlank()) {
            stateField.error = "Empty"
            return
        }

        if (zip.isNullOrBlank()) {
            zipField.error = "Empty"
            return
        }

        if (country.isNullOrBlank()) {
            countryField.error = "Empty"
            return
        }

        if (zip.contains(Regex("[a-zA-Z]"))) {
            zipField.error = "Only number"
            return
        }

        showProgressBar(true)



        val customer = Customer().apply {
            this.billing = Billing().apply {
                this.address1 = address
                this.city = city
                this.postcode = zip
                this.state = state
                this.country = country
                this.email = customerSP.email
                this.phone = customerSP.phoneNo
            }
        }

        WooCommerceRetrofitClient.retrofit.create(WooCommerceService::class.java)
                .updateCustomer(body = customer)
                .enqueue(object : Callback<CustomerResponse> {
                    override fun onResponse(call: Call<CustomerResponse>, response: Response<CustomerResponse>) {
                        showProgressBar(false)
                        updateAddress("$address\n$city, $zip\n$state, $country")
                        makeToast("Adrress Updated")
                    }

                    override fun onFailure(call: Call<CustomerResponse>, t: Throwable) {
                        showProgressBar(false)
                        makeToast("Failed")
                    }
                })
    }

    fun showProgressBar(bool: Boolean) {
        if (bool) acba_progress_bar.visibility = View.VISIBLE
        else acba_progress_bar.visibility = View.INVISIBLE
    }

    fun makeToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun updateAddress(address: String) {
        val preferences = getSharedPreferences(getString(R.string.user_details_file), Context.MODE_PRIVATE)
        preferences.edit().putString(getString(R.string.billing_address_key), address).apply()
    }
}
