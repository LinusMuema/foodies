package com.moose.foodies.features.feature_recipe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.moose.foodies.R
import com.moose.foodies.models.Section
import com.moose.foodies.util.hide
import kotlinx.android.synthetic.main.procedure_list_item.view.*

class ProcedureListAdapter(private val procedures: List<Section>): RecyclerView.Adapter<ProcedureListAdapter.ProcedureViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProcedureViewHolder = ProcedureViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.procedure_list_item, parent, false)
    )

    override fun getItemCount(): Int = procedures.size

    override fun onBindViewHolder(holder: ProcedureViewHolder, position: Int) = holder.bind(procedures[position])

    inner class ProcedureViewHolder(view: View): RecyclerView.ViewHolder(view) {

        fun bind(section: Section) {
            when(section.name){
                "" -> itemView.procedure_name.hide()
                else ->  itemView.procedure_name.text = section.name
            }
            itemView.step_recycler.apply {
                setHasFixedSize(true)
                adapter = StepListAdapter(section.steps)
            }
        }

    }
}