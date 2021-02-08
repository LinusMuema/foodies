package com.moose.foodies.features.feature_recipe.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.moose.foodies.databinding.ProcedureListItemBinding
import com.moose.foodies.features.feature_home.domain.Section
import com.moose.foodies.features.feature_recipe.adapters.ProcedureListAdapter.ProcedureViewHolder
import com.moose.foodies.util.hide

class ProcedureListAdapter(private val procedures: List<Section>): Adapter<ProcedureViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProcedureViewHolder {
        val binding = ProcedureListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProcedureViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProcedureViewHolder, position: Int) {
        holder.bind(procedures[position])
    }


    override fun getItemCount(): Int = procedures.size

    inner class ProcedureViewHolder(private val binding: ProcedureListItemBinding): ViewHolder(binding.root) {

        fun bind(section: Section) {
            if (section.name == "") binding.procedureName.hide()
            else binding.procedureName.text = section.name

            binding.stepRecycler.apply {
                setHasFixedSize(true)
                adapter = StepListAdapter(section.steps)
            }
        }

    }
}