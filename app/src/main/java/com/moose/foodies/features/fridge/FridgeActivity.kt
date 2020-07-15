package com.moose.foodies.features.fridge

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.chip.Chip
import com.moose.foodies.R
import com.moose.foodies.util.ActivityHelper
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_fridge.*
import javax.inject.Inject

class FridgeActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<FridgeViewModel> { viewModelFactory }

    private var ingredients = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        ActivityHelper.initialize(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fridge)

        ingredients_input.setOnEditorActionListener { v, _, _ ->
            if (v.text.isNotEmpty()){
                ingredients.add(v.text.toString())
                val chip = Chip(this)
                chip.text = v.text
                chip.isCloseIconVisible = true
                chip.setOnCloseIconClickListener {
                    ingredients.remove(chip.text.toString())
                    ingredients_group.removeView(chip)
                }
                ingredients_input.setText("", TextView.BufferType.EDITABLE);
                ingredients_group.addView(chip)
            }
            true
        }

        search_btn.setOnClickListener {

        }

        fridge_back.setOnClickListener { onBackPressed() }
    }
}