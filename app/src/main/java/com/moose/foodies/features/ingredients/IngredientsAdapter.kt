package com.moose.foodies.features.ingredients

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayoutMediator
import com.moose.foodies.R
import com.moose.foodies.models.RecipeSuggestion
import com.moose.foodies.util.*
import kotlinx.android.synthetic.main.ingredients_search_item.view.*

class IngredientsAdapter(
    private val recipes: List<RecipeSuggestion>,
    private val activity: IngredientsActivity,
    private val getRecipe: (recipe: RecipeSuggestion, position: Int) -> Unit
):
    RecyclerView.Adapter<IngredientsAdapter.RecipeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder =
        RecipeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.ingredients_search_item, parent, false))

    override fun getItemCount(): Int = recipes.size

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) = holder.bind(recipes[position], position)

    inner class RecipeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val context: Context = itemView.context
        private val titles = arrayOf("Missed Ingredients", "Used Ingredients")
        fun bind(recipe: RecipeSuggestion, position: Int) {

            val url = recipe.image.replace("312x231", "636x393")
            itemView.prepare_loading.start()
            itemView.recipe_suggestion_name.text = recipe.title
            itemView.recipe_suggestion_image.loadImage(url)
            itemView.recipe_suggestion_image.setHeight(HeightCalculator.getImageHeight(context))

            itemView.view_pager.adapter = IngredientsPagerAdapter(activity, recipe)
            TabLayoutMediator(itemView.tabs, itemView.view_pager){tab, position ->
                tab.text = titles[position]
            }.attach()

            itemView.btn_prepare.setOnClickListener {
                itemView.btn_prepare.hide()
                itemView.prepare_loading.show()
                getRecipe(recipe, position)
            }
        }

    }
}