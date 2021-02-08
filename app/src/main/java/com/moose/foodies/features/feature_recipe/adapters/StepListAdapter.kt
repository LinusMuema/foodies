package com.moose.foodies.features.feature_recipe.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.moose.foodies.databinding.StepListItemBinding
import com.moose.foodies.features.feature_home.domain.Step
import com.moose.foodies.features.feature_recipe.adapters.StepListAdapter.StepListViewHolder

class StepListAdapter(private val steps: List<Step>): Adapter<StepListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepListViewHolder {
        val binding = StepListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StepListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StepListViewHolder, position: Int) {
        holder.bind(steps[position])
    }

    override fun getItemCount(): Int = steps.size

    inner class StepListViewHolder(private val binding: StepListItemBinding): ViewHolder(binding.root) {
        fun bind(step: Step) {
            binding.stepNumber.text = step.number.toString()
            binding.step.text = step.instruction
        }

    }
}