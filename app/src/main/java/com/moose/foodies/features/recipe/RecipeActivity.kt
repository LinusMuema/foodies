package com.moose.foodies.features.recipe

import android.graphics.Rect
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.Menu
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.moose.foodies.R
import com.moose.foodies.models.Recipe
import com.moose.foodies.util.*
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_recipe.*
import kotlinx.android.synthetic.main.intolerance_list_item.view.*
import javax.inject.Inject

class RecipeActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var heightCalculator: HeightCalculator

    private val recipeViewModel by viewModels<RecipeViewModel> { viewModelFactory }
    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        ActivityHelper.initialize(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)

        val recipe = Gson().fromJson(intent.getStringExtra("recipe"), Recipe::class.java)

        val scale: Float = this.resources.displayMetrics.density
        val pixels = (heightCalculator.getImageHeight() * scale + 0.5f).toInt()

        recipeViewModel.checkFavorite(recipe.id)


        val url = recipe.info.image.replace("312x231", "636x393")
        img_food.setHeight(pixels)
        img_food.loadImage(url)

        ingredients_recycler.apply {
            setHasFixedSize(true)
            adapter = ItemListAdapter(recipe.instructions.ingredients, "ingredients")
        }
        equipment_recycler.apply {
            setHasFixedSize(true)
            adapter = ItemListAdapter(recipe.instructions.equipment, "equipment")
        }

        procedure_recycler.apply {
            setHasFixedSize(true)
            adapter = ProcedureListAdapter(recipe.instructions.sections)
        }


        recipeViewModel.state.observe(this, Observer {
            showSnackbar(recipe_layout, it.reason!!)
        })

        recipeViewModel.isFavorite.observe(this, Observer {
            isFavorite = it
            invalidateOptionsMenu()
            Log.d("Fav", "onCreate: isFavorite value is $isFavorite")
        })

        setSupportActionBar(topAppBar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        topAppBar.requestLayout()
        val layoutParams = (topAppBar.layoutParams as? ViewGroup.MarginLayoutParams)
        layoutParams?.setMargins(0, getStatusBarHeight(), 0, 0)
        topAppBar.layoutParams = layoutParams
        topAppBar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.favorite -> {
                    if (isFavorite)
                        recipeViewModel.removeFavorite(recipe)
                    else
                        recipeViewModel.addFavorite(recipe)
                    true
                }
                else -> {
                    showSnackbar(recipe_layout, "Not a valid menu item")
                    true
                }
            }
        }
    }

    private fun getStatusBarHeight(): Int {
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        return if (resourceId > 0) resources.getDimensionPixelSize(resourceId)
        else Rect().apply { window.decorView.getWindowVisibleDisplayFrame(this) }.top
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.recipe_bar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if (isFavorite)
            menu!!.findItem(R.id.favorite).icon = resources.getDrawable(R.drawable.ic_favorites)
        else
            menu!!.findItem(R.id.favorite).icon = resources.getDrawable(R.drawable.ic_favorite_border)
        return super.onPrepareOptionsMenu(menu)
    }

}