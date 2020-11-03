package com.moose.foodies.features.fridge

import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.chip.Chip
import com.google.gson.Gson
import com.moose.foodies.R
import com.moose.foodies.features.recipe.RecipeActivity
import com.moose.foodies.models.FridgeSearch
import com.moose.foodies.models.Info
import com.moose.foodies.models.Recipe
import com.moose.foodies.models.RecipeSuggestion
import com.moose.foodies.util.*
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_fridge.*
import kotlinx.android.synthetic.main.ingredients_search_item.view.*
import javax.inject.Inject

class FridgeActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val fridgeViewModel by viewModels<FridgeViewModel> { viewModelFactory }

    private var ingredients = mutableListOf<String>()

    private lateinit var currentRecipe: RecipeSuggestion
    private var currentPosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        ActivityHelper.initialize(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fridge)

        fridgeViewModel.response.observe(this, {
            it as FridgeSearch
            fridge_loading.hide()
            fridge_recycler.show()
            fridge_recycler.apply {
                setHasFixedSize(true)
                adapter = FridgeRecipeAdapter(it.recipes, this@FridgeActivity){recipe, position ->
                    currentRecipe = recipe
                    currentPosition = position
                    fridgeViewModel.getRecipeById(recipe.id.toString())
                }
            }
        })

        fridgeViewModel.exception.observe(this, {
            showSnackbar(fridge_layout, it)
        })

        fridgeViewModel.instructions.observe(this, { instructions ->
            val holder = fridge_recycler.findViewHolderForAdapterPosition(currentPosition)!!
            holder.itemView.btn_prepare.show()
            holder.itemView.prepare_loading.hide()

            push<RecipeActivity>{
                it.putExtra("recipe", Gson().toJson(Recipe(
                    currentRecipe.id,
                    Info(currentRecipe.id, currentRecipe.image, "jpg", currentRecipe.title),
                    instructions.instructions
                )))
            }
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