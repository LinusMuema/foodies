package com.moose.foodies.features.feature_search.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.moose.foodies.databinding.RecipeResultItemBinding
import com.moose.foodies.features.feature_search.adapters.RecipeResultsAdapter.RecipeViewHolder
import com.moose.foodies.features.feature_search.domain.Recipe
import com.moose.foodies.util.setImageHeight
import kotlinx.android.synthetic.main.recipe_result_details.view.*


class RecipeResultsAdapter(private val recipes: List<Recipe>): Adapter<RecipeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding = RecipeResultItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

            cardExpand.setTitle(recipe.title)
            cardExpand.setOnExpandedListener { v, expanded ->
                if (!expanded) cardExpand.setTitle(recipe.title)
                else {
                    cardExpand.setTitle(" ")
                    v.recipe.text = recipe.title
                    v.time.text = "Ready in ${recipe.readyInMinutes} minutes."
                    v.people.text = "Serves ${recipe.servings} people."
                    v.read_more.setOnClickListener {
                        context.startActivity(Intent(Intent.ACTION_VIEW, url))
                    }
                }
            }
        }
    }

    class RecipeViewHolder(val binding: RecipeResultItemBinding): ViewHolder(binding.root)
}