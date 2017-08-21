package com.webhopers.medorder.register

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.webhopers.medorder.R
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : RegisterView, AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val presenter = RegisterPresenter(this)
        ar_register_btn.setOnClickListener { presenter.onRegister() }
    }


    //View Functions

    override fun getFirstNameField() = ar_first_name

    override fun getLastNameField() = ar_last_name

    override fun getEmailField() = ar_email

    override fun getPasswordField() = ar_password

    override fun getConfirmPasswordField() = ar_confirm_password

    override fun showProgressBar(bool: Boolean) {
        if (bool) ar_progress_bar.visibility = View.VISIBLE
        else ar_progress_bar.visibility = View.INVISIBLE
    }
}
