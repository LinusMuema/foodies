package com.moose.foodies.features.feature_auth

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.moose.foodies.R
import com.moose.foodies.databinding.ActivityAuthBinding
import com.moose.foodies.features.feature_home.HomeActivity
import com.moose.foodies.models.onError
import com.moose.foodies.models.onSuccess
import com.moose.foodies.util.ActivityHelper
import com.moose.foodies.util.PreferenceHelper
import com.moose.foodies.util.pop
import com.moose.foodies.util.showToast
import dagger.android.AndroidInjection
import javax.inject.Inject

class AuthActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<AuthViewModel>{ viewModelFactory }
    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AndroidInjection.inject(this)
        ActivityHelper.initialize(this)

        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginBtn.setOnClickListener {
            val email = binding.email.text.toString()

            if (email.isEmpty())
                binding.emailLayout.error = "Please provide an email address"
            else {
                binding.loginBtn.text = getString(R.string.loading)
                binding.loginBtn.isEnabled = false
                viewModel.register(email)
            }
        }

        viewModel.token.observe(this, { result ->
            binding.loginBtn.text = getString(R.string.login)
            binding.loginBtn.isEnabled = true
            result.onSuccess {
                PreferenceHelper.setAccessToken(this, it.token)
                PreferenceHelper.setLogged(this, true)
                pop<HomeActivity>()
            }
            result.onError {
                this.showToast(it)
            }
        })
    }
}
