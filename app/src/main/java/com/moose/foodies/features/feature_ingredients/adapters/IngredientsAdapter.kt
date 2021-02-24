package com.moose.foodies.features.feature_ingredients.adapters

import android.view.LayoutInflater.from
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.moose.foodies.databinding.IngredientsSearchItemBinding
import com.moose.foodies.databinding.IngredientsSearchItemBinding.inflate
import com.moose.foodies.features.feature_ingredients.adapters.IngredientsAdapter.RecipeViewHolder
import com.moose.foodies.features.feature_ingredients.domain.Recipe
import com.moose.foodies.features.feature_ingredients.domain.clean
import com.moose.foodies.util.extensions.largeImage
import com.moose.foodies.util.extensions.loadImage

class IngredientsAdapter(
    private val recipes: List<Recipe>,
    private val prepare: (id: Int) -> Unit
): Adapter<RecipeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding = inflate(from(parent.context), parent, false)
        return RecipeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipes[position]
        val url = recipe.image.largeImage()

        with(holder.binding){
            image.loadImage(url)
            recipeName.text = recipe.title
            recyclerView.adapter = MissedIngredientsAdapter(recipe.missedIngredients.clean())
            prepare.setOnClickListener {
                prepare(recipe.id)
            }
        }
    }

    override fun getItemCount(): Int = recipes.size

    class RecipeViewHolder(val binding: IngredientsSearchItemBinding): ViewHolder(binding.root)
}