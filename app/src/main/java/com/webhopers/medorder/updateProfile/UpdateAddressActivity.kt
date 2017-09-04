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
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_update_address.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateAddressActivity : AppCompatActivity() {

    lateinit var addressField: TextInputEditText
    lateinit var cityField: TextInputEditText
    lateinit var stateField: TextInputEditText
    lateinit var zipField: TextInputEditText
    lateinit var countryField: TextInputEditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_address)

        initUI()

        addressField = aua_address_field
        cityField = aua_city_field
        stateField = aua_state_field
        zipField = aua_zip_field
        countryField = aua_country_field

        aua_btn.setOnClickListener { onUpdate() }
    }

    private fun initUI() {
        setUpToolbar()
        setFields()

    }

    private fun setFields() {
        val text = intent.getStringExtra("DATA")
        if (text.isNullOrBlank())
            return

        val address = text.substringBefore("\n")

        val cityAndPin = text.substringAfter("\n").substringBefore("\n")
        val city = cityAndPin.substringBefore(",")
        val pin = cityAndPin.substringAfter(", ")

        val stateAndCountry = text.substringAfter("\n").substringAfter("\n")
        val state = stateAndCountry.substringBefore(",")
        val country = stateAndCountry.substringAfter(", ")


        aua_address_field.setText(address)
        aua_city_field.setText(city)
        aua_zip_field.setText(pin)
        aua_state_field.setText(state)
        aua_country_field.setText(country)
    }

    private fun setUpToolbar() {
        setSupportActionBar(aua_toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun onUpdate() {
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
            this.metaData = listOf(
                    MetaData().apply {
                        key = "addr1"
                        value = address
                    },
                    MetaData().apply {
                        key = "city"
                        value = city
                    },
                    MetaData().apply {
                        key = "thestate"
                        value = state
                    },
                    MetaData().apply {
                        key = "zip"
                        value = zip
                    },
                    MetaData().apply {
                        key = "country"
                        value = country
                    })
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
        if (bool) aua_progress_bar.visibility = View.VISIBLE
        else aua_progress_bar.visibility = View.INVISIBLE
    }

    fun makeToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun updateAddress(address: String) {
        val preferences = getSharedPreferences(getString(R.string.user_details_file), Context.MODE_PRIVATE)
        preferences.edit().putString(getString(R.string.address_key), address).apply()
    }
}
