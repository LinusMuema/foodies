package com.moose.foodies.features

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.moose.foodies.db.DbRepository
import com.moose.foodies.util.PreferenceHelper
import javax.inject.Inject

open class BaseActivity : AppCompatActivity() {
    @Inject lateinit var dbRepository: DbRepository
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val baseViewModel by viewModels<BaseViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (PreferenceHelper.getAuthType(this) == "signup"){
            baseViewModel.getBackup()
        }
    }

    override fun onPause() {
        super.onPause()
        baseViewModel.startBackup(this)
    }
}