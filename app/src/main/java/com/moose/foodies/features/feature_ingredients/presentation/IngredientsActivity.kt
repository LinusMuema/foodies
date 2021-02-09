package com.moose.foodies.features.feature_ingredients.presentation

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.chip.Chip
import com.moose.foodies.R
import com.moose.foodies.databinding.ActivityIngredientsBinding
import com.moose.foodies.util.ActivityHelper
import com.moose.foodies.util.hideKeyPad
import dagger.android.AndroidInjection
import javax.inject.Inject

class IngredientsActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<IngredientsViewModel> { viewModelFactory }
    private lateinit var binding: ActivityIngredientsBinding
    private val ingredients = mutableSetOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        ActivityHelper.initialize(this)

        binding = ActivityIngredientsBinding.inflate(layoutInflater)
        with(binding) {

            search.setOnClickListener {
                root.transitionToState(R.id.loading)
                Handler(Looper.getMainLooper()).postDelayed({
                    root.transitionToState(R.id.loaded)
                }, 5000)
            }
            form.root.setOnClickListener { root.transitionToState(R.id.loaded) }

            form.addIngredient.setOnClickListener {
                val ingredient = form.ingredient.text!!

                if (ingredient.isNotEmpty()) {
                    hideKeyPad()
                    addIngredient(ingredient.toString())
                    ingredient.clear()
                    root.transitionToState(R.id.loaded)
                } else form.inputLayout.error = "This field can't be empty"
            }

            add.setOnClickListener { root.transitionToState(R.id.form) }
            back.setOnClickListener { onBackPressed() }
        }

        setContentView(binding.root)
    }


    private fun addIngredient(ingredient: String) {
        ingredients.add(ingredient)

        val chip = Chip(this)
        chip.text = ingredient
        chip.chipBackgroundColor = getColorStateList(R.color.primary)
        chip.setTextColor(getColor(R.color.light_text))
        binding.chipGroup.addView(chip)

        chip.setOnClickListener {
            binding.chipGroup.removeView(it)
            ingredients.remove(ingredient)
        }
    }
}