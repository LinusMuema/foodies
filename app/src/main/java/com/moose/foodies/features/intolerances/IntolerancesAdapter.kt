package com.moose.foodies.features.intolerances

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.moose.foodies.R
import com.moose.foodies.models.Intolerance
import com.moose.foodies.util.loadDrawable
import com.moose.foodies.util.loadRoundImage
import kotlinx.android.synthetic.main.intolerance_list_item.view.*

class IntolerancesAdapter(val intolerances: List<Intolerance>, val handleItem: (Intolerance, selected:Boolean) -> Unit): RecyclerView.Adapter<IntolerancesAdapter.IntolerancesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = IntolerancesViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.intolerance_list_item, parent, false)
    )

    override fun getItemCount(): Int = intolerances.size

    override fun onBindViewHolder(holder: IntolerancesViewHolder, position: Int) {
        holder.bind(intolerances[position])
    }

    inner class IntolerancesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val image = itemView.image
        private val name = itemView.name


        private var selected = false
        fun bind(intolerance: Intolerance) {
            image.loadRoundImage(intolerance.image_url)
            name.text = intolerance.name
            image.setOnClickListener {
                selected = !selected
                handleItem(intolerance, selected)
                if (selected) image.loadDrawable(R.drawable.selected)
                else image.loadRoundImage(intolerance.image_url)
            }
        }
    }
}
