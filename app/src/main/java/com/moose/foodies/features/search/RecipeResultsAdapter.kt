package com.moose.foodies.features.search

import android.content.Intent
import android.transition.Slide
import android.transition.Transition
import android.transition.TransitionManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.moose.foodies.R
import com.moose.foodies.models.RecipeResults
import com.moose.foodies.util.*
import kotlinx.android.synthetic.main.recipe_result_item.view.*


class RecipeResultsAdapter(private val results: List<RecipeResults>, private val pixels: Int): RecyclerView.Adapter<RecipeResultsAdapter.RecipeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder = RecipeViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.recipe_result_item, parent, false)
    )
    override fun getItemCount(): Int = results.size

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) = holder.bind(results[position])

    inner class RecipeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(recipeResults: RecipeResults) {
            val image = "https://spoonacular.com/recipeImages/${recipeResults.id}-636x393.jpg"
            var cardOpen = false
            val context = itemView.context

            itemView.recipe_image.setHeight(pixels)
            itemView.recipe_image.loadImage(image)
            itemView.time.text = context.resources.getString(R.string.cook_time, recipeResults.readyInMinutes)
            itemView.servings.text = context.resources.getString(R.string.servings, recipeResults.servings)

            itemView.read_more.setOnClickListener {
                context.startActivity(Intent(Intent.ACTION_VIEW, recipeResults.sourceUrl.toUri()))
            }

            itemView.collapse.setOnClickListener {
                when(cardOpen){
                    true -> itemView.collapse.loadDrawable(R.drawable.ic_collapse)
                    false -> itemView.collapse.loadDrawable(R.drawable.ic_close)
                }
                toggle(cardOpen, itemView.card_info, itemView.card_expand as ViewGroup)
                cardOpen = !cardOpen
            }
        }

        private fun toggle(show: Boolean, view: View, parent: ViewGroup) {
            val transition: Transition = Slide(Gravity.TOP)
            transition.duration = 600
            transition.addTarget(view)
            TransitionManager.beginDelayedTransition(parent, transition)
            view.visibility = if (show) View.GONE else View.VISIBLE
        }
    }
}