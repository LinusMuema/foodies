package com.moose.foodies.features.search

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.moose.foodies.R
import com.moose.foodies.util.*
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_recipes.*
import javax.inject.Inject


class RecipesFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var searchViewModel: SearchViewModel

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        requireActivity().hideAllBars()
        searchViewModel = ViewModelProviders.of(requireActivity(), viewModelFactory).get(SearchViewModel::class.java)
        return inflater.inflate(R.layout.fragment_recipes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val scale: Float = this.resources.displayMetrics.density
        val pixels = (HeightCalculator.getImageHeight(this.requireContext()) * scale + 0.5f).toInt()
        searchViewModel.recipes.observe(viewLifecycleOwner, Observer {
            recipes_loading.hide()
            if (it.isEmpty()) empty_recipes.text = resources.getString(R.string.empty_search, "recipes")
            recipes_recycler.apply {
                setHasFixedSize(true)
                adapter = RecipeResultsAdapter(it, pixels)
            }
        })
    }

}