package com.moose.foodies.features.feature_recipe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.moose.foodies.R
import com.moose.foodies.models.Step
import kotlinx.android.synthetic.main.step_list_item.view.*

class StepListAdapter(private val steps: List<Step>): RecyclerView.Adapter<StepListAdapter.StepListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepListViewHolder = StepListViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.step_list_item, parent, false)
    )

    override fun getItemCount(): Int = steps.size

    override fun onBindViewHolder(holder: StepListViewHolder, position: Int) = holder.bind(steps[position])

    inner class StepListViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun bind(step: Step) {
            itemView.step_number.text = step.number.toString()
            itemView.step.text = step.instruction
        }

    }
}