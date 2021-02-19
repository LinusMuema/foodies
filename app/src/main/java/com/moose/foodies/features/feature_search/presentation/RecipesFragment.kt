package com.moose.foodies.features.feature_search.presentation

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.moose.foodies.R
import com.moose.foodies.databinding.PagerLayoutBinding
import com.moose.foodies.features.feature_search.adapters.RecipeResultsAdapter
import com.moose.foodies.features.feature_search.domain.Recipe
import com.moose.foodies.util.extensions.hideBottomBar
import com.moose.foodies.util.extensions.showToast
import com.moose.foodies.util.onError
import com.moose.foodies.util.onSuccess
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


class RecipesFragment : Fragment(R.layout.pager_layout) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: SearchViewModel
    private lateinit var binding: PagerLayoutBinding

    override fun onAttach(context: Context) {
        this.requireActivity().hideBottomBar()
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity(), viewModelFactory).get(SearchViewModel::class.java)
        binding = PagerLayoutBinding.bind(view)

        viewModel.recipes.observe(viewLifecycleOwner, { result ->
            result.onSuccess { updateUi(it) }
            result.onError { requireActivity().showToast(it) }
        })
    }

    private fun updateUi(recipes: List<Recipe>) {
        if (recipes.isEmpty()) {
            binding.errorLayout.message.text = resources.getString(R.string.not_found, "recipes")
            binding.root.transitionToState(R.id.error)
        } else {
            Log.d("Foodies", "updateUi: recipes === $recipes")
            binding.root.transitionToState(R.id.loading)
            binding.recyclerView.apply {
                setHasFixedSize(true)
                adapter = RecipeResultsAdapter(recipes)
            }
        }
    }

}