package com.moose.foodies.features.fridge

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.moose.foodies.R
import com.moose.foodies.models.RecipeSuggestion
import com.moose.foodies.util.HeightCalculator
import com.moose.foodies.util.loadImage
import com.moose.foodies.util.setHeight
import kotlinx.android.synthetic.main.ingredients_search_item.view.*

class FridgeRecipeAdapter(private val recipes: List<RecipeSuggestion>):
    RecyclerView.Adapter<FridgeRecipeAdapter.RecipeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder =
        RecipeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.ingredients_search_item, parent, false))

    override fun getItemCount(): Int = recipes.size

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) = holder.bind(recipes[position])


    inner class RecipeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val context: Context = itemView.context
        fun bind(recipe: RecipeSuggestion) {
            val scale: Float = context.resources.displayMetrics.density
            val pixels = (HeightCalculator.getImageHeight(context) * scale + 0.5f).toInt()
            val url = recipe.image.replace("312x312", "636x393")
            Log.d("fridge", "bind: $url")
            itemView.recipe_suggestion_name.text = recipe.title
            itemView.recipe_suggestion_image.loadImage(url)
            itemView.recipe_suggestion_image.setHeight(pixels)
        }

    }
}