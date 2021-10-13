package com.moose.foodies.features.add

import android.app.Activity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.PathEffect.Companion.dashPathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.github.dhaval2404.imagepicker.ImagePicker
import com.moose.foodies.components.SmallSpacing
import com.moose.foodies.theme.FoodiesTheme
import com.moose.foodies.util.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddActivity : AppCompatActivity() {
    private val viewmodel: AddViewmodel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { Content() }
    }

    private val launcher = registerForActivityResult(StartActivityForResult()) {
        val data = it.data
        when (it.resultCode) {
            Activity.RESULT_OK -> viewmodel.uploadImage(data?.data!!)
            ImagePicker.RESULT_ERROR -> toast(ImagePicker.getError(data))
            else -> toast("Image upload cancelled")
        }
    }

    private fun getImage(){
        ImagePicker.with(this).galleryOnly().createIntent { launcher.launch(it) }
    }

    @Composable
    private fun Content() {
        FoodiesTheme {
           Surface(color = colors.primary) {
               val dashColor = colors.onSurface
               val effect = dashPathEffect(floatArrayOf(10f, 10f), 0f)
               val stroke = Stroke(width = 2f, pathEffect = effect)

               Column(modifier = Modifier.padding(10.dp)) {
                   SmallSpacing()
                   Text(
                       text = "Upload a new recipe",
                       style = typography.h6.copy(color = colors.onSurface)
                   )
                   SmallSpacing()
                   Box(
                       modifier = Modifier
                           .fillMaxWidth()
                           .height(100.dp),
                       contentAlignment = Alignment.Center
                   ) {
                       Canvas(modifier = Modifier
                           .fillMaxSize()
                           .clickable { getImage() }) {
                           drawRoundRect(
                               style = stroke,
                               color = dashColor,
                               cornerRadius = CornerRadius(10f, 10f)
                           )
                       }
                       Text(
                           textAlign = TextAlign.Center,
                           text = "Tap to upload the photo",
                           style = typography.body1.copy(color = colors.onPrimary)
                       )
                   }
               }
            }
        }
    }
}