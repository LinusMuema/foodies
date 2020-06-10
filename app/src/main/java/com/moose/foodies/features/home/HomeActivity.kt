package com.moose.foodies.features.home

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.moose.foodies.R
import com.moose.foodies.features.auth.AuthActivity
import com.moose.foodies.features.favorites.FavoritesActivity
import com.moose.foodies.features.recipe.RecipeActivity
import com.moose.foodies.util.*
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum
import com.nightonke.boommenu.BoomButtons.SimpleCircleButton
import com.nightonke.boommenu.Piece.PiecePlaceEnum
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_recipe.*
import kotlinx.android.synthetic.main.carousel_item.view.*
import javax.inject.Inject

class HomeActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var heightCalculator: HeightCalculator

    private val homeViewModel by viewModels<HomeViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        ActivityHelper.initialize(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setUpBoomMenu()

        val scale: Float = this.resources.displayMetrics.density
        val pixels = (heightCalculator.getImageHeight() * scale + 0.5f).toInt()

        homeViewModel.state.observe(this, Observer {
            home_swipe.stopRefreshing()
            showSnackbar(home_layout, it.reason!!)
        })

        homeViewModel.recipes.observe(this, Observer {
            home_swipe.stopRefreshing()
            carousel.apply {
                size = it.size
                setCarouselViewListener { view, position ->
                    val recipe = it[position]
                    val url = recipe.info.image.replace("312x231", "636x393")
                    view.carousel_item.setHeight(pixels)
                    view.pick_image.loadCarouselImage(url)
                    view.pick_name.text = recipe.info.title
                    view.setOnClickListener {
                        startActivity(Intent(this@HomeActivity, RecipeActivity::class.java).putExtra("recipe", Gson().toJson(recipe)))
                    }
                }
                show()
            }
        })

        homeViewModel.getRecipes()
        home_swipe.setOnRefreshListener {
            if (connectionAvailable()) {
                homeViewModel.updateRecipes()
            }
            else {
                showSnackbar(home_layout, "Check your connection and try again")
                home_swipe.stopRefreshing()
            }
        }
    }

    private fun setUpBoomMenu() {
        val icons = arrayOf(R.drawable.ic_logout, R.drawable.ic_favorites, R.drawable.ic_account, R.drawable.ic_baseline_info_24, R.drawable.ic_search)
        for (icon in icons){
            val builder = SimpleCircleButton.Builder()
                .normalColorRes(R.color.primary)
                .normalImageRes(icon)
                .imagePadding(Rect(10, 10, 10, 10))
                .listener {
                    handleBoomClick(it)
                }

            bmb.addBuilder(builder)
        }
        bmb.buttonPlaceEnum = ButtonPlaceEnum.SC_5_4
        bmb.piecePlaceEnum = PiecePlaceEnum.DOT_5_4
    }

    private fun handleBoomClick(index: Int) {
        when(index){
            0 -> {
                homeViewModel.logout()
                startActivity(Intent(this, AuthActivity::class.java))
            }
            1 -> {
                startActivity(Intent(this, FavoritesActivity::class.java))
            }
            2 -> {
                Log.d("Boom", "handleBoomClick: accounts clicked")
            }
            3 -> {
                Log.d("Boom", "handleBoomClick: info clicked")
            }
            4 -> {
                Log.d("Boom", "handleBoomClick: search clicked")
            }
        }
    }

    private fun connectionAvailable(): Boolean {
        val manager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = manager.activeNetworkInfo
        return activeNetwork != null
    }
}