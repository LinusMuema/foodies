package com.moose.foodies.features.add

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.PathEffect.Companion.dashPathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import coil.transform.RoundedCornersTransformation
import com.github.dhaval2404.imagepicker.ImagePicker
import com.moose.foodies.components.OutlinedInput
import com.moose.foodies.components.Required
import com.moose.foodies.components.SmallSpacing
import com.moose.foodies.components.TextFieldState
import com.moose.foodies.features.add.ui.ImageUpload
import com.moose.foodies.theme.FoodiesTheme
import com.moose.foodies.util.UploadState
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
            Activity.RESULT_OK -> viewmodel.setUri(data?.data!!)
            ImagePicker.RESULT_ERROR -> toast(ImagePicker.getError(data))
            else -> toast("Image upload cancelled")
        }
    }

    private fun getImage() {
        ImagePicker.with(this).galleryOnly().createIntent { launcher.launch(it) }
    }

    @Composable
    private fun Content() {
        val path by viewmodel.path.observeAsState()
        val nameState = remember { TextFieldState(validators = listOf(Required())) }
        val descriptionState = remember { TextFieldState(validators = listOf(Required())) }

        FoodiesTheme {
            Surface(color = colors.primary) {
                Column(modifier = Modifier.padding(10.dp)) {
                    SmallSpacing()
                    Text(
                        text = "Upload a new recipe",
                        style = typography.h6.copy(color = colors.onSurface)
                    )
                    SmallSpacing()
                    ImageUpload(path = path, onClick = { getImage() })
                    SmallSpacing()
                    OutlinedInput(
                        label = "Name",
                        state = nameState,
                        type = KeyboardType.Email,
                    )
                    SmallSpacing()
                    OutlinedInput(
                        label = "Description",
                        state = descriptionState,
                        type = KeyboardType.Email,
                    )
                }
            }
        }
    }
}