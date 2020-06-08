package com.moose.foodies.features.favorites

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.moose.foodies.R
import com.moose.foodies.models.Recipe
import kotlinx.android.synthetic.main.favorite_item.view.*

class FavoritesAdapter(private val recipes: List<Recipe>) :
    RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder = FavoritesViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.favorite_item, parent, false)
    )

    override fun getItemCount(): Int = recipes.size

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) = holder.bind(recipes[position])


    inner class FavoritesViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val name: MaterialTextView = view.recipe_name

        fun bind(recipe: Recipe) {
            name.text = recipe.info.title
        }

    }

}