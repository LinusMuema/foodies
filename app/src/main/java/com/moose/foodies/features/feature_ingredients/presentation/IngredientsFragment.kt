package com.moose.foodies.features.feature_ingredients.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.moose.foodies.R

class IngredientsFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_ingredients, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var builder = ""

//        ingredients.forEach {
//            builder += "• ${it.amount} ${it.unit} of ${it.name} \n\n"
//        }
//        ingredients_list.text = builder
    }
}