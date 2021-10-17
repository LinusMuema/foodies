package com.moose.foodies.features.add

import android.app.Activity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.github.dhaval2404.imagepicker.ImagePicker
import com.moose.foodies.components.*
import com.moose.foodies.features.add.ui.ImageUpload
import com.moose.foodies.features.add.ui.Items
import com.moose.foodies.models.Item
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
        var ingredients by remember { mutableStateOf(mutableListOf<Item>()) }
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
                    Items(
                        type = "Ingredient",
                        viewmodel = viewmodel,
                        onClick = { ingredients.add(it) },
                    )
                }
            }
        }
    }
}