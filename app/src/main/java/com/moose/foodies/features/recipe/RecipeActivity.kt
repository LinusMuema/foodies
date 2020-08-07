package com.moose.foodies.features.recipe

import android.graphics.Rect
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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
import kotlinx.android.synthetic.main.error_404.*
import javax.inject.Inject

class RecipeActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory


    private val recipeViewModel by viewModels<RecipeViewModel> { viewModelFactory }
    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        ActivityHelper.initialize(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)

        val recipe = Gson().fromJson(intent.getStringExtra("recipe"), Recipe::class.java)


        recipeViewModel.checkFavorite(recipe.id)


        val url = recipe.info.image.replace("312x231", "636x393")
        img_food.setHeight(HeightCalculator.getImageHeight(this))
        img_food.loadImage(url)

        if(recipe.instructions.sections.isEmpty()){
            error_layout.show()
            recipe_details.hide()
            not_found.text = this.resources.getString(R.string.not_found, "Recipe Instructions")
        }
        else {
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
        }


        recipeViewModel.exception.observe(this, Observer {
            showSnackbar(recipe_layout, it)
        })

        recipeViewModel.isFavorite.observe(this, Observer {
            isFavorite = it
            invalidateOptionsMenu()
        })

        setSupportActionBar(topAppBar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
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
                    PreferenceHelper.setBackupStatus(this, true)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}