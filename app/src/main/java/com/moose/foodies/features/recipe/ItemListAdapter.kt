package com.moose.foodies.features.recipe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.moose.foodies.R
import com.moose.foodies.models.InstructionItem
import com.moose.foodies.util.loadCarouselImage
import kotlinx.android.synthetic.main.recipe_item.view.*

class ItemListAdapter(private val items: List<InstructionItem>, private val type: String, private val imageHeight: Int): RecyclerView.Adapter<ItemListAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder = ItemViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.recipe_item, parent, false)
    )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) = holder.bind(items[position])

    inner class ItemViewHolder(view: View): RecyclerView.ViewHolder(view) {

        fun bind(item: InstructionItem) {
            val imageUrl: String = "https://spoonacular.com/cdn/${type}_100x100/${item.image}"
            itemView.item_name.text = item.name
            itemView.item_image.loadCarouselImage(imageUrl, imageHeight)
        }

    }
}