package com.moose.foodies.features.fridge

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.BulletSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.moose.foodies.R
import com.moose.foodies.models.FridgeIngredient
import kotlinx.android.synthetic.main.fragment_ingredients.*

class UsedIngredientFragment(private val ingredients: List<FridgeIngredient>) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_ingredients, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var builder = ""

        ingredients.forEach {
            builder += "• ${it.amount} ${it.unit} of ${it.name} \n\n"
        }
        ingredients_list.text = builder
    }
}