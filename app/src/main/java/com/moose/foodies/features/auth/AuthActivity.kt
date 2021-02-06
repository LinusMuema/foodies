package com.moose.foodies.features.auth

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.github.dhaval2404.form_validation.rule.EmailRule
import com.github.dhaval2404.form_validation.rule.NonEmptyRule
import com.github.dhaval2404.form_validation.validation.FormValidator
import com.moose.foodies.databinding.ActivityAuthBinding
import com.moose.foodies.features.onError
import com.moose.foodies.features.onSuccess
import com.moose.foodies.util.ActivityHelper
import com.moose.foodies.util.PreferenceHelper
import com.moose.foodies.util.showToast
import dagger.android.AndroidInjection
import javax.inject.Inject

class AuthActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<AuthViewModel> {viewModelFactory}
    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AndroidInjection.inject(this)
        ActivityHelper.initialize(this)

        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginBtn.setOnClickListener {
            val email = binding.email.text.toString()
            if (isValidForm()) viewModel.register(email)
        }

        viewModel.token.observe(this, { result ->
            result.onSuccess {
                PreferenceHelper.setAccessToken(this, it.token)
            }
            result.onError {
                this.showToast(it)
            }
        })
    }

    private fun isValidForm(): Boolean {
        return FormValidator.getInstance()
            .addField(
                binding.email,
                NonEmptyRule("Please provide an email address"),
                EmailRule("Email address is not valid")
            )
            .validate()
    }
}
