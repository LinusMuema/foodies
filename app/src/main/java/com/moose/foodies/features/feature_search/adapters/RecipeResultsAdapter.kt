package com.moose.foodies.features.feature_search.adapters

import android.view.LayoutInflater.from
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.moose.foodies.R
import com.moose.foodies.databinding.RecipeResultItemBinding
import com.moose.foodies.databinding.RecipeResultItemBinding.inflate
import com.moose.foodies.features.feature_search.adapters.RecipeResultsAdapter.RecipeViewHolder
import com.moose.foodies.features.feature_search.domain.Recipe
import com.moose.foodies.util.extensions.setImageHeight


class   RecipeResultsAdapter(private val recipes: List<Recipe>): Adapter<RecipeViewHolder>() {

    class RecipeViewHolder(val binding: RecipeResultItemBinding): ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding = inflate(from(parent.context), parent, false)
        return RecipeViewHolder(binding)
    }

    override fun getItemCount(): Int = recipes.size

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val context = holder.binding.root.context
        val recipe = recipes[position]
        val url = recipe.sourceUrl.toUri()
        val builder = CustomTabsIntent.Builder()
        builder.setToolbarColor(context.getColor(R.color.primary))
        val intent = builder.build()

        with(holder.binding){
            val image = "https://spoonacular.com/recipeImages/${recipe.id}-636x393.jpg"

            recipeImage.setImageHeight()
            recipeImage.load(image){ placeholder(R.drawable.loading) }
            content.recipeName.text = recipe.title.formatRecipeName()

            content.time.text = context.getString(R.string.cook_time, recipe.readyInMinutes)
            content.people.text = context.getString(R.string.servings, recipe.servings)
            content.readMore.setOnClickListener {
                intent.launchUrl(context, url)
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