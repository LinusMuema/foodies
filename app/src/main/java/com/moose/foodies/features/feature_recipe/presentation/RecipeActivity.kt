package com.moose.foodies.features.feature_recipe.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.moose.foodies.R
import com.moose.foodies.databinding.ActivityRecipeBinding
import com.moose.foodies.features.feature_home.domain.Instructions
import com.moose.foodies.features.feature_recipe.adapters.ItemListAdapter
import com.moose.foodies.features.feature_recipe.adapters.ProcedureListAdapter
import com.moose.foodies.models.onError
import com.moose.foodies.models.onSuccess
import com.moose.foodies.util.ActivityHelper
import com.moose.foodies.util.clean
import com.moose.foodies.util.formatUrl
import com.moose.foodies.util.setImageHeight
import dagger.android.AndroidInjection
import javax.inject.Inject

class RecipeActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var binding: ActivityRecipeBinding
    private val viewModel by viewModels<RecipeViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        ActivityHelper.initialize(this)

        binding = ActivityRecipeBinding.inflate(layoutInflater)
        binding.recipeImage.setImageHeight()

        var id: Int = intent.getIntExtra("recipeId", 0)
        if (id == 0)
            id = intent.data!!.getQueryParameter("id")!!.toInt()

        // Get the recipe
        viewModel.getRecipe(id)

        viewModel.recipe.observe(this, { result ->
            result.onSuccess {
                val url = it.info.image.formatUrl()
                binding.recipeImage.load(url)
                updateRecyclerViews(it.instructions)
            }
            result.onError { Log.e("Foodies", "onCreate: $it") }
        })

        viewModel.isFavorite.observe(this, { result ->
            result.onSuccess { isFavorite ->
                if (isFavorite) binding.favoriteIcon.load(R.drawable.ic_favorite)
                else binding.favoriteIcon.load(R.drawable.ic_favorite_outline)
            }
            result.onError { Log.e("Foodies", "onCreate: $it") }
        })

        setContentView(binding.root)
    }

    private fun updateRecyclerViews(instructions: Instructions) {
        binding.ingredientsRecycler.apply {
            val list = instructions.ingredients.clean()
            setHasFixedSize(true)
            adapter = ItemListAdapter(list, "ingredients")
        }

        binding.equipmentRecycler.apply {
            val list = instructions.equipment.clean()
            setHasFixedSize(true)
            adapter = ItemListAdapter(list, "equipment")
        }

        binding.procedureRecycler.apply {
            adapter = ProcedureListAdapter(instructions.sections)
        }
    }

}