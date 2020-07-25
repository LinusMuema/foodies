package com.moose.foodies.features.fridge

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.BulletSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.moose.foodies.R
import com.moose.foodies.models.FridgeIngredient
import kotlinx.android.synthetic.main.fragment_ingredients.*

class MissedIngredientFragment(private val ingredients: List<FridgeIngredient>) : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_ingredients, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var builder = ""

        ingredients.forEach {
            builder += "• ${it.amount} ${it.unit} of ${it.name} \n\n"
        }
        ingredients_list.text = builder
    }
}