package com.moose.foodies.features.feature_ingredients.adapters

import android.view.LayoutInflater.from
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.moose.foodies.R
import com.moose.foodies.databinding.MissingIngredientBinding
import com.moose.foodies.databinding.MissingIngredientBinding.inflate
import com.moose.foodies.features.feature_ingredients.adapters.MissedIngredientsAdapter.MissedIngredientsViewHolder
import com.moose.foodies.features.feature_ingredients.domain.MissedIngredient

class MissedIngredientsAdapter(
    private val ingredients: List<MissedIngredient>,
): Adapter<MissedIngredientsViewHolder>() {

    class MissedIngredientsViewHolder(val binding: MissingIngredientBinding): ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MissedIngredientsViewHolder {
        val binding = inflate(from(parent.context))
        return MissedIngredientsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MissedIngredientsViewHolder, position: Int) {
        val ingredient = ingredients[position]
        val value = String.format("%.1f", ingredient.amount)
        val context = holder.binding.root.context

        with(holder.binding){
            image.load(ingredient.image)
            name.text = ingredient.name
            amount.text = context.getString(R.string.amount, value, ingredient.unitShort)
        }
    }

    override fun getItemCount(): Int = ingredients.size
}