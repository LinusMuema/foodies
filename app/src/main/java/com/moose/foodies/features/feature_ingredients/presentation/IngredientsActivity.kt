package com.moose.foodies.features.feature_ingredients.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.moose.foodies.databinding.ActivityIngredientsBinding
import com.moose.foodies.util.ActivityHelper
import dagger.android.AndroidInjection
import javax.inject.Inject

class IngredientsActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<IngredientsViewModel> { viewModelFactory }
    private lateinit var binding: ActivityIngredientsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        ActivityHelper.initialize(this)

        binding = ActivityIngredientsBinding.inflate(layoutInflater)
        binding.back.setOnClickListener { onBackPressed() }

        setContentView(binding.root)
    }
}