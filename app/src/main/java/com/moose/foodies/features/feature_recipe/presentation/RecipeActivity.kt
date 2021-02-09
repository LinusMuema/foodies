package com.moose.foodies.features.feature_recipe.presentation

import android.content.Intent
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
    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        ActivityHelper.initialize(this)

        // Get id passed via intent
        var id: Int = intent.getIntExtra("recipeId", 0)
        if (id == 0)
            id = intent.data!!.getQueryParameter("id")!!.toInt()

        binding = ActivityRecipeBinding.inflate(layoutInflater)
        binding.recipeImage.setImageHeight()

        // Get the recipe
        viewModel.getRecipe(id)

        viewModel.recipe.observe(this, { result ->
            result.onSuccess { recipe ->
                val url = recipe.info.image.formatUrl()
                binding.recipeImage.load(url)
                updateRecyclerViews(recipe.instructions)
                enableShare(recipe.id, recipe.info.title)

                binding.favorite.setOnClickListener {
                    if (isFavorite) viewModel.removeFavorite(recipe.id)
                    else viewModel.addFavorite(recipe)
                }
            }
            result.onError { Log.e(this.localClassName, "onCreate: $it") }
        })

        viewModel.isFavorite.observe(this, { result ->
            result.onSuccess {
                isFavorite = it
                if (it) binding.favoriteIcon.load(R.drawable.ic_favorite)
                else binding.favoriteIcon.load(R.drawable.ic_favorite_outline)
            }
            result.onError { Log.e(this.localClassName, "onCreate: $it") }
        })

        setContentView(binding.root)
    }

    private fun enableShare(id: Int, title: String) {
        val message = getString(R.string.share_recipe, title, id)
        binding.share.setOnClickListener {
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, message)
                type = "text/plain"
            }
            startActivity(Intent.createChooser(intent, "Share this recipe via:"))
        }
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