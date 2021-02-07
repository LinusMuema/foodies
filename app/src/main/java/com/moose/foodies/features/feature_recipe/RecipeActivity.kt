package com.moose.foodies.features.feature_recipe

import android.graphics.Rect
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.moose.foodies.R
import com.moose.foodies.util.*
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_recipe.*
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

        img_food.setHeight(HeightCalculator.getImageHeight(this))
        img_food.loadImage("")

        recipeViewModel.exception.observe(this, {
            showSnackbar(recipe_layout, it)
        })

        recipeViewModel.isFavorite.observe(this, {
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