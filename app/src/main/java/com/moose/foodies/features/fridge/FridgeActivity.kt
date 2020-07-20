package com.moose.foodies.features.fridge

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.chip.Chip
import com.moose.foodies.R
import com.moose.foodies.util.*
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_fridge.*
import javax.inject.Inject

class FridgeActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val fridgeViewModel by viewModels<FridgeViewModel> { viewModelFactory }

    private var ingredients = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        ActivityHelper.initialize(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fridge)

        fridgeViewModel.recipes.observe(this, Observer {
            fridge_loading.hide()
            fridge_recycler.show()
            fridge_recycler.apply {
                setHasFixedSize(true)
                adapter = FridgeRecipeAdapter(it.recipes)
            }
        })

        fridgeViewModel.exception.observe(this, Observer {
            showSnackbar(fridge_layout, it)
        })


        ingredients_input.setOnEditorActionListener { v, _, _ ->
            if (v.text.isNotEmpty()){
                ingredients.add(v.text.toString())
                val chip = Chip(this)
                chip.text = v.text
                chip.isCloseIconVisible = true
                chip.setOnCloseIconClickListener {
                    ingredients.remove(chip.text.toString())
                    ingredients_group.removeView(chip)
                    input_area.show()
                }
                chip.setOnClickListener {
                    input_area.show()
                }
                ingredients_input.setText("", TextView.BufferType.EDITABLE);
                ingredients_group.addView(chip)
            }
            true
        }

        search_btn.setOnClickListener {
            this.hideKeyboard()
            this.hideBottomBar()
            input_area.hide()
            fridge_recycler.hide()
            fridge_loading.show()
            fridgeViewModel.getRecipes(ingredients.joinToString(separator = ","))
        }

        fridge_back.setOnClickListener { onBackPressed() }
    }
}