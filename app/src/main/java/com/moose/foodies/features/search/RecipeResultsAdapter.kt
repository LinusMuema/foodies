package com.moose.foodies.features.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.moose.foodies.R
import com.moose.foodies.models.RecipeResults

class RecipeResultsAdapter(private val results: List<RecipeResults>): RecyclerView.Adapter<RecipeResultsAdapter.RecipeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder = RecipeViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.recipe_result_item, parent, false)
    )
    override fun getItemCount(): Int = results.size

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) = holder.bind(results[position])

    class RecipeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(recipeResults: RecipeResults) {

        }
    }
}