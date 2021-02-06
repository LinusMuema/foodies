package com.moose.foodies.features.auth

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.dhaval2404.form_validation.rule.EmailRule
import com.github.dhaval2404.form_validation.rule.MinLengthRule
import com.github.dhaval2404.form_validation.rule.NonEmptyRule
import com.github.dhaval2404.form_validation.validation.FormValidator
import com.moose.foodies.R
import com.moose.foodies.features.home.HomeActivity
import com.moose.foodies.features.intolerances.IntolerancesActivity
import com.moose.foodies.features.reset.ResetPasswordActivity
import com.moose.foodies.util.*
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_auth.*
import javax.inject.Inject

class AuthActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val authViewModel by viewModels<AuthViewModel> {viewModelFactory}

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        ActivityHelper.initialize(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        rotateloading.start()

        authViewModel.response.observe(this, { it ->
            rotateloading.hide()
            login_btn.show()
            when (it as String) {
                "login" -> {
                    authViewModel.getBackup()
                    pop<HomeActivity>() {
                        it.putExtra("type", "login")
                    }
                }
                "signup" -> {
                    pop<IntolerancesActivity> {
                        it.putExtra("type", "signup")
                    }
                }
            }
        })

        authViewModel.exception.observe(this, Observer {
            rotateloading.hide()
            login_btn.show()
            showSnackbar(auth_layout, it)
        })

        login_btn.setOnClickListener {
            if (!isValidForm()) return@setOnClickListener
            login_btn.hide()
            rotateloading.show()
            authViewModel.startAuth(password.text.toString(), email.text.toString())
        }

        reset.setOnClickListener {
            push<ResetPasswordActivity>()
        }
    }

    private fun isValidForm(): Boolean {
        return FormValidator.getInstance()
            .addField(email, NonEmptyRule("Please provide an email address"), EmailRule("Email address is not valid"))
            .addField(password, NonEmptyRule("Please provide a password"), MinLengthRule(8, "At least 8 characters"))
            .validate()
    }
}
