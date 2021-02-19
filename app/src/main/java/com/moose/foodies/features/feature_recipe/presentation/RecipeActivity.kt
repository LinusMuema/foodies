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
import com.moose.foodies.features.feature_home.presentation.HomeActivity
import com.moose.foodies.features.feature_recipe.adapters.ItemListAdapter
import com.moose.foodies.features.feature_recipe.adapters.ProcedureListAdapter
import com.moose.foodies.util.ActivityHelper
import com.moose.foodies.util.PreferenceHelper
import com.moose.foodies.util.extensions.*
import com.moose.foodies.util.onError
import com.moose.foodies.util.onSuccess
import dagger.android.AndroidInjection
import javax.inject.Inject

class RecipeActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var binding: ActivityRecipeBinding
    private val viewModel by viewModels<RecipeViewModel> { viewModelFactory }
    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        ActivityHelper.initialize(this)

        // Get id passed via intent
        val id = getRecipeId()
        viewModel.getRecipe(id)

        binding = ActivityRecipeBinding.inflate(layoutInflater)

        viewModel.recipe.observe(this, { result ->
            result.onSuccess { recipe ->
                val url = recipe.info.image.largeImage()
                binding.recipeImage.load(url)
                binding.title.text = recipe.info.title

                binding.back.setOnClickListener {
                    push<HomeActivity>()
                }
                binding.share.setOnClickListener {
                    shareRecipe(recipe.info.title, recipe.id)
                }

                if (recipe.instructions.sections.isEmpty()) {
                    binding.contentLayout.transitionToState(R.id.error)
                    binding.errorLayout.message.text =
                        resources.getString(R.string.not_found, "instructions")
                    return@onSuccess
                }

                binding.contentLayout.transitionToEnd()
                updateRecyclerViews(recipe.instructions)

                binding.favorite.setOnClickListener {
                    PreferenceHelper.setBackupStatus(this, true)
                    if (isFavorite) viewModel.removeFavorite(recipe.id)
                    else viewModel.addFavorite(recipe)
                }
            }
            result.onError { showToast(it) }
        })

        viewModel.isFavorite.observe(this, { result ->
            result.onSuccess {
                isFavorite = it
                if (it) binding.favoriteIcon.load(R.drawable.ic_favorite)
                else binding.favoriteIcon.load(R.drawable.ic_favorite_outline)
            }
            result.onError { Log.e("Foodies", "onCreate: $it") }
        })

        setContentView(binding.root)
    }

    private fun getRecipeId(): Int {
        val fromActivity = intent.getIntExtra("recipeId", 0)
        val fromUri = intent.data?.getQueryParameter("id")?.toInt()
        return if(fromActivity == 0) fromUri!! else fromActivity
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