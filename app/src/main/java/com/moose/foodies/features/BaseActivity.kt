package com.moose.foodies.features

import android.app.Activity
import android.content.Intent
import android.graphics.Rect
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.moose.foodies.R
import com.moose.foodies.features.auth.AuthActivity
import com.moose.foodies.features.favorites.FavoritesActivity
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum
import com.nightonke.boommenu.BoomButtons.SimpleCircleButton
import com.nightonke.boommenu.Piece.PiecePlaceEnum
import kotlinx.android.synthetic.main.activity_base.*

open class BaseActivity: AppCompatActivity() {

    fun setUpBoomMenu() {
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
}