package com.moose.foodies.features.feature_ingredients

import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.chip.Chip
import com.moose.foodies.R
import com.moose.foodies.features.feature_recipe.RecipeActivity
import com.moose.foodies.models.RecipeSuggestion
import com.moose.foodies.util.*
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_fridge.*
import kotlinx.android.synthetic.main.ingredients_search_item.view.*
import javax.inject.Inject

class IngredientsActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val fridgeViewModel by viewModels<IngredientsViewModel> { viewModelFactory }

    private var ingredients = mutableListOf<String>()

    private lateinit var currentRecipe: RecipeSuggestion
    private var currentPosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        ActivityHelper.initialize(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fridge)

        fridgeViewModel.instructions.observe(this, { instructions ->
            val holder = fridge_recycler.findViewHolderForAdapterPosition(currentPosition)!!
            holder.itemView.btn_prepare.show()
            holder.itemView.prepare_loading.hide()

            push<RecipeActivity>()
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
                ingredients_input.setText("", TextView.BufferType.EDITABLE)
                ingredients_group.show()
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