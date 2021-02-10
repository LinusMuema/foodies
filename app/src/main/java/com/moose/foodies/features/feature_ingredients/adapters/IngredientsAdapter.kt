package com.moose.foodies.features.feature_ingredients.adapters

import android.view.LayoutInflater.from
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.moose.foodies.databinding.IngredientsSearchItemBinding
import com.moose.foodies.databinding.IngredientsSearchItemBinding.inflate
import com.moose.foodies.features.feature_ingredients.adapters.IngredientsAdapter.RecipeViewHolder
import com.moose.foodies.features.feature_ingredients.domain.Recipe
import com.moose.foodies.util.formatUrl
import com.moose.foodies.util.setImageHeight

class IngredientsAdapter(private val recipes: List<Recipe>): Adapter<RecipeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding = inflate(from(parent.context), parent, false)
        return RecipeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipes[position]
        val url = recipe.image.formatUrl()

        with(holder.binding){
            image.setImageHeight()
            image.load(url)
            recipeName.text = recipe.title
            recyclerView.adapter = MissedIngredientsAdapter(recipe.missedIngredients)
        }
    }

    override fun getItemCount(): Int = recipes.size

    class RecipeViewHolder(val binding: IngredientsSearchItemBinding): ViewHolder(binding.root)
}