package com.moose.foodies.features.add

import android.app.Activity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.PathEffect.Companion.dashPathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.github.dhaval2404.imagepicker.ImagePicker
import com.moose.foodies.components.SmallSpacing
import com.moose.foodies.theme.FoodiesTheme
import com.moose.foodies.util.toast

class AddActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { Content() }
    }

    private val launcher = registerForActivityResult(StartActivityForResult()) {
        when (it.resultCode) {
            Activity.RESULT_OK -> {
                val path = it.data?.data!!
            }
            ImagePicker.RESULT_ERROR -> toast(ImagePicker.getError(it.data))
            else -> toast("Image upload cancelled")
        }
    }

    @Composable
    private fun Content() {

        val dashColor = colors.onSurface
        val effect = dashPathEffect(floatArrayOf(10f, 10f), 0f)
        val stroke = Stroke(width = 2f, pathEffect = effect)

        FoodiesTheme {
            Column(modifier = Modifier.padding(10.dp)) {
                Text(
                    text = "Upload a new recipe",
                    style = typography.h6.copy(color = colors.onSurface)
                )
                SmallSpacing()

                Box(Modifier.size(250.dp, 60.dp), contentAlignment = Alignment.Center) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        drawRoundRect(color = dashColor, style = stroke)
                    }
                    Text(
                        textAlign = TextAlign.Center, text = "Tap here to introduce yourself"
                    )
                }
            }
        }
    }
}