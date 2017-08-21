package com.webhopers.medorder.register

import android.widget.EditText
import com.webhopers.medorder.extensions.value
import com.webhopers.medorder.interfaces.Presenter
import com.webhopers.medorder.interfaces.View
import com.webhopers.medorder.models.Customer
import com.webhopers.medorder.models.CustomerResponse
import com.webhopers.medorder.services.retrofit.WooCommerceRetrofitClient
import com.webhopers.medorder.services.woocommerce.WooCommerceService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class RegisterPresenter(val view: RegisterView): Presenter {

    private lateinit var firstName: String
    private lateinit var lastName: String
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var confirmPassword: String

    fun onRegister() {
        getInput()

        if (!validInput()) return

        createCustomer()
    }

    private fun getInput() {
        firstName = view.getFirstNameField().value()
        lastName = view.getLastNameField().value()
        email  = view.getEmailField().value()
        password = view.getPasswordField().value()
        confirmPassword = view.getConfirmPasswordField().value()
    }

    private fun validInput(): Boolean {

        if (firstName.isNullOrBlank()) {
            view.getFirstNameField().error = "Empty"
            return false
        }

        if (lastName.isNullOrBlank()) {
            view.getLastNameField().error = "Empty"
            return false
        }

        if (email.isNullOrBlank()) {
            view.getEmailField().error = "Empty"
            return false
        }

        if (password.isNullOrBlank()) {
            view.getPasswordField().error = "Empty"
            return false
        }

        if (confirmPassword.isNullOrBlank()) {
            view.getConfirmPasswordField().error = "Empty"
            return false
        }

        if (!validEmail(email)) {
            view.getEmailField().error = "Enter valid email"
            return false
        }

        if (password != confirmPassword) {
            view.getConfirmPasswordField().error = "Password does not match"
            return false
        }

        return true
    }

    private fun validEmail(email: String): Boolean {
        val matcher = Pattern.compile("\\w+@\\w+\\.\\w+").matcher(email)
        return matcher.find()
    }

    private fun createCustomer() {
        view.showProgressBar(true)

        val customer = Customer(email, firstName, lastName, password)

        WooCommerceRetrofitClient.retrofit.create(WooCommerceService::class.java)
                .createCustomer(customer)
                .enqueue(object : Callback<CustomerResponse> {
                    override fun onResponse(call: Call<CustomerResponse>, response: Response<CustomerResponse>) {
                        view.showProgressBar(false)
                        if (response.isSuccessful) println(response.body()!!.password)
                        else println(response.code())
                    }

                    override fun onFailure(call: Call<CustomerResponse>, t: Throwable) {
                        view.showProgressBar(false)
                        println(t.message)
                    }

                })
    }
}


interface RegisterView: View {

    fun getFirstNameField(): EditText
    fun getLastNameField(): EditText
    fun getEmailField(): EditText
    fun getPasswordField(): EditText
    fun getConfirmPasswordField(): EditText
}