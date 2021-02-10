package com.moose.foodies.features.feature_recipe.adapters

import android.view.LayoutInflater.from
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.moose.foodies.databinding.ProcedureListItemBinding
import com.moose.foodies.databinding.ProcedureListItemBinding.inflate
import com.moose.foodies.features.feature_home.domain.Section
import com.moose.foodies.features.feature_recipe.adapters.ProcedureListAdapter.ProcedureViewHolder
import com.moose.foodies.util.extensions.hide

class ProcedureListAdapter(private val sections: List<Section>): Adapter<ProcedureViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProcedureViewHolder {
        val binding = inflate(from(parent.context), parent, false)
        return ProcedureViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProcedureViewHolder, position: Int) {
        val section = sections[position]
        with(holder.binding){
            if (section.name == "") procedureName.hide()
            else procedureName.text = section.name

            stepRecycler.apply {
                setHasFixedSize(true)
                adapter = StepListAdapter(section.steps)
            }
        }
    }


    override fun getItemCount(): Int = sections.size

    class ProcedureViewHolder(val binding: ProcedureListItemBinding): ViewHolder(binding.root)
}