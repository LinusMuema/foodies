package com.moose.foodies.features

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Rect
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.crashlytics.internal.common.CrashlyticsCore
import com.moose.foodies.R
import com.moose.foodies.features.auth.AuthActivity
import com.moose.foodies.features.favorites.FavoritesActivity
import com.moose.foodies.features.home.HomeActivity
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum
import com.nightonke.boommenu.BoomButtons.SimpleCircleButton
import com.nightonke.boommenu.Piece.PiecePlaceEnum
import kotlinx.android.synthetic.main.activity_base.*
import javax.inject.Inject

open class BaseActivity @Inject constructor(): AppCompatActivity() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }

    fun setUpBoomMenu() {
        val icons = arrayOf(R.drawable.ic_home, R.drawable.ic_favorites, R.drawable.ic_search, R.drawable.ic_account, R.drawable.ic_logout)
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
        val activity = this.localClassName
        Log.d("Activity", "handleBoomClick: ${this.localClassName}")
        when(index){
            0 -> {
                if (!activity.contains("HomeActivity"))
                    startActivity(Intent(this, HomeActivity::class.java))
            }
            1 -> {
                if (!activity.contains("FavoritesActivity"))
                    startActivity(Intent(this, FavoritesActivity::class.java))
            }
            2 -> {
                Log.d("Boom", "handleBoomClick: search clicked")
            }
            3 -> {
                Log.d("Boom", "handleBoomClick: account clicked")
            }
            4 -> {
                sharedPreferences.edit().putBoolean("logged", false).apply()
                startActivity(Intent(this, AuthActivity::class.java))
            }
        }
    }
}