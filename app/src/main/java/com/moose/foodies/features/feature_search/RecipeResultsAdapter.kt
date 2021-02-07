package com.moose.foodies.features.feature_search

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.moose.foodies.R
import com.moose.foodies.models.RecipeResults
import com.moose.foodies.util.HeightCalculator
import com.moose.foodies.util.loadImage
import com.moose.foodies.util.setHeight
import kotlinx.android.synthetic.main.recipe_result_details.view.*
import kotlinx.android.synthetic.main.recipe_result_item.view.*


class RecipeResultsAdapter(private val results: List<RecipeResults>): RecyclerView.Adapter<RecipeResultsAdapter.RecipeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder = RecipeViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.recipe_result_item, parent, false)
    )
    override fun getItemCount(): Int = results.size

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) = holder.bind(results[position])

    inner class RecipeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(recipeResults: RecipeResults) {
            val image = "https://spoonacular.com/recipeImages/${recipeResults.id}-636x393.jpg"
            val context = itemView.context

            itemView.recipe_image.setHeight(HeightCalculator.getImageHeight(context))
            itemView.recipe_image.loadImage(image)
            itemView.card_expand.setTitle(recipeResults.title)
            itemView.card_expand.setOnExpandedListener { v, expanded ->
                if (!expanded) itemView.card_expand.setTitle(recipeResults.title)
                else{
                    itemView.card_expand.setTitle(" ")
                    v.recipe.text = recipeResults.title
                    v.time.text = context.resources.getString(R.string.cook_time, recipeResults.readyInMinutes)
                    v.servings.text = context.resources.getString(R.string.servings, recipeResults.servings)
                    v.read_more.setOnClickListener {
                        context.startActivity(Intent(Intent.ACTION_VIEW, recipeResults.sourceUrl.toUri()))
                    }
                }
            }

        }

    }
}