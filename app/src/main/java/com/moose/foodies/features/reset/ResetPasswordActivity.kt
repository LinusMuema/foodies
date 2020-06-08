package com.moose.foodies.features.reset

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.dhaval2404.form_validation.rule.EmailRule
import com.github.dhaval2404.form_validation.rule.NonEmptyRule
import com.github.dhaval2404.form_validation.validation.FormValidator
import com.moose.foodies.R
import com.moose.foodies.features.auth.AuthActivity
import com.moose.foodies.util.hide
import com.moose.foodies.util.hideBottomBar
import com.moose.foodies.util.show
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_reset_password.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.toast
import org.jetbrains.anko.yesButton
import javax.inject.Inject

class ResetPasswordActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val resetPasswordViewModel by viewModels<ResetPasswordViewModel> {viewModelFactory}

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)
        resetloading.start()
        this.hideBottomBar()

        resetPasswordViewModel.response.observe(this, Observer {
            resetloading.hide()
            reset_btn.show()

            alert(it, "Password reset") {
                yesButton { startActivity(Intent(this@ResetPasswordActivity, AuthActivity::class.java)) }
            }.show()
        })

        reset_btn.setOnClickListener {
            if (!isValidForm()) return@setOnClickListener
            reset_btn.hide()
            resetloading.show()
            resetPasswordViewModel.resetPassword(email.text.toString())
        }
    }

    private fun isValidForm(): Boolean {
        return FormValidator.getInstance()
            .addField(email, NonEmptyRule("Please provide an email address"), EmailRule("Email address is not valid"))
            .validate()
    }
}
