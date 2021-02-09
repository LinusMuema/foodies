package com.moose.foodies.features.feature_ingredients.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.moose.foodies.databinding.IngredientsSearchItemBinding
import com.moose.foodies.features.feature_ingredients.adapters.IngredientsAdapter.RecipeViewHolder
import com.moose.foodies.features.feature_ingredients.domain.Recipe

class IngredientsAdapter(private val recipes: List<Recipe>): Adapter<RecipeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding = IngredientsSearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
    }

    override fun getItemCount(): Int = recipes.size

    class RecipeViewHolder(binding: IngredientsSearchItemBinding): ViewHolder(binding.root)
}