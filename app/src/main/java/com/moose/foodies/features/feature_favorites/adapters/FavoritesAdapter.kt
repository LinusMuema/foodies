package com.moose.foodies.features.feature_favorites.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.moose.foodies.databinding.FavoriteItemBinding
import com.moose.foodies.features.feature_favorites.adapters.FavoritesAdapter.FavoritesViewHolder
import com.moose.foodies.features.feature_home.domain.Recipe

class FavoritesAdapter(
    private val recipes: List<Recipe>,
    val showRecipe: (id: Int) -> Unit
) : Adapter<FavoritesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val binding = FavoriteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoritesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        val recipe = recipes[position]

        with(holder.binding){
            val url = recipe.info.image.replace("312x231", "90x90")
            recipeName.text = recipe.info.title
            recipeImage.load(url)
        }

        holder.binding.root.setOnClickListener {
            showRecipe(recipe.id)
        }
    }

    override fun getItemCount(): Int = recipes.size

    class FavoritesViewHolder(val binding: FavoriteItemBinding): ViewHolder(binding.root)

}