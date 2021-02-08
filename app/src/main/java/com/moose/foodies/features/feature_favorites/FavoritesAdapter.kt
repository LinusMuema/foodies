package com.moose.foodies.features.feature_favorites

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.moose.foodies.R
import com.moose.foodies.models.Recipe
import com.moose.foodies.util.loadImage
import kotlinx.android.synthetic.main.favorite_item.view.*

class FavoritesAdapter(
    private val recipes: List<Recipe>,
    val showRecipe: (recipe: Recipe) -> Unit
) :
    RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder = FavoritesViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.favorite_item, parent, false)
    )

    override fun getItemCount(): Int = recipes.size

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) = holder.bind(recipes[position])


    inner class FavoritesViewHolder(view: View): RecyclerView.ViewHolder(view) {

        fun bind(recipe: Recipe) {
            val url = recipe.info.image.replace("312x231", "90x90")
            itemView.recipe_name.text = recipe.info.title
            itemView.recipe_image.loadImage(url)

            itemView.setOnClickListener {
                showRecipe(recipe)
            }
        }

    }

}