package com.moose.foodies.features.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.dhaval2404.form_validation.rule.EmailRule
import com.github.dhaval2404.form_validation.rule.MinLengthRule
import com.github.dhaval2404.form_validation.rule.NonEmptyRule
import com.github.dhaval2404.form_validation.validation.FormValidator
import com.moose.foodies.R
import com.moose.foodies.di.DaggerAppComponent
import com.moose.foodies.features.home.HomeActivity
import com.moose.foodies.features.intolerances.IntolerancesActivity
import com.moose.foodies.features.reset.ResetPasswordActivity
import com.moose.foodies.util.*
import kotlinx.android.synthetic.main.activity_auth.*
import javax.inject.Inject

class AuthActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val authViewModel by viewModels<AuthViewModel> {viewModelFactory}

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerAppComponent.factory().create(this).inject(this)
        ActivityHelper.initialize(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        rotateloading.start()

        authViewModel.response.observe(this, Observer {
            rotateloading.hide()
            login_btn.show()
            when {
                it == "login" -> {
                    startActivity(Intent(this@AuthActivity, HomeActivity::class.java))
                    finish()
                }
                it == "signup" -> {
                    startActivity(Intent(this@AuthActivity, IntolerancesActivity::class.java).putExtra("signup", true))
                    finish()
                }
                "403" in it -> showSnackbar(auth_layout, "Check your password and try again")
                else -> showSnackbar(auth_layout, it)
            }
        })
        login_btn.setOnClickListener {
            if (!isValidForm()) return@setOnClickListener
            login_btn.hide()
            rotateloading.show()
            authViewModel.startAuth(password.text.toString(), email.text.toString())
        }

        reset.setOnClickListener {
            startActivity(Intent(this, ResetPasswordActivity::class.java))
        }
    }

    private fun isValidForm(): Boolean {
        return FormValidator.getInstance()
            .addField(email, NonEmptyRule("Please provide an email address"), EmailRule("Email address is not valid"))
            .addField(password, NonEmptyRule("Please provide a password"), MinLengthRule(8, "At least 8 characters"))
            .validate()
    }
}
