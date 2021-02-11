package com.moose.foodies.features.feature_search.adapters

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater.from
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.moose.foodies.databinding.RecipeResultItemBinding
import com.moose.foodies.databinding.RecipeResultItemBinding.inflate
import com.moose.foodies.features.feature_search.adapters.RecipeResultsAdapter.RecipeViewHolder
import com.moose.foodies.features.feature_search.domain.Recipe
import com.moose.foodies.util.extensions.setImageHeight


class RecipeResultsAdapter(private val recipes: List<Recipe>): Adapter<RecipeViewHolder>() {

    class RecipeViewHolder(val binding: RecipeResultItemBinding): ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding = inflate(from(parent.context), parent, false)
        return RecipeViewHolder(binding)
    }

    override fun getItemCount(): Int = recipes.size

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipes[position]
        val url = recipe.sourceUrl.toUri()

        with(holder.binding){
            val image = "https://spoonacular.com/recipeImages/${recipe.id}-636x393.jpg"
            val context = root.context

            recipeImage.setImageHeight()
            recipeImage.load(image)
            content.recipeName.text = recipe.title.formatRecipeName()
            Log.d("Foodies", "onBindViewHolder: ${recipe.title}")

            content.time.text = "Ready in ${recipe.readyInMinutes} minutes."
            content.people.text = "Serves ${recipe.servings} people."
            content.readMore.setOnClickListener {
                context.startActivity(Intent(Intent.ACTION_VIEW, url))
            }
        }
    }

    private fun String.formatRecipeName(): String {
        return when {
            this.contains("-") -> this.split("-").first()
            this.contains(":") -> this.split(":").first()
            this.contains("–") -> this.split("–").first()
            else -> this
        }
    }
}