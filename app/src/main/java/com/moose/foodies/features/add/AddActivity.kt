package com.moose.foodies.features.add

import android.app.Activity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Arrangement.SpaceBetween
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.Icons.Outlined
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.AddCircleOutline
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.accompanist.flowlayout.FlowRow
import com.moose.foodies.components.*
import com.moose.foodies.features.add.ui.Categories
import com.moose.foodies.features.add.ui.ImageUpload
import com.moose.foodies.features.add.ui.Items
import com.moose.foodies.models.Item
import com.moose.foodies.models.RawRecipe
import com.moose.foodies.theme.FoodiesTheme
import com.moose.foodies.theme.shapes
import com.moose.foodies.util.UploadState
import com.moose.foodies.util.onError
import com.moose.foodies.util.onSuccess
import com.moose.foodies.util.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddActivity : AppCompatActivity() {
    private val viewmodel: AddViewmodel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { Content() }

        viewmodel.result.observe(this, {
            it.onSuccess { finish() }
            it.onError { error -> toast(error) }
        })
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
        val loading by viewmodel.loading.observeAsState()
        val progress by viewmodel.progress.observeAsState()
        var selected by remember { mutableStateOf(0) }
        var expanded by remember { mutableStateOf(false) }
        var button by remember { mutableStateOf("Upload") }
        var equipment by remember { mutableStateOf(setOf<Item>()) }
        var ingredients by remember { mutableStateOf(setOf<Item>()) }
        var categories by remember { mutableStateOf(listOf<String>()) }
        var steps by remember { mutableStateOf(listOf<TextFieldState>()) }
        val nameState = remember { TextFieldState(validators = listOf(Required())) }
        val timeState = remember { TextFieldState(validators = listOf(Required())) }
        val descriptionState = remember { TextFieldState(validators = listOf(Required())) }
        val times = listOf("10 mins", "15 mins", "30 mins", "45 mins", "60 mins", "Custom (mins)")

        when (val state = progress){
            is UploadState.Error -> toast(state.message)
            is UploadState.Loading -> {
                val percentage = (state.current.toDouble() / state.total.toDouble()) * 100
                button = "Uploading image...${percentage.toInt()}%"
            }
            is UploadState.Success -> {
                button = "Saving recipe..."
                val allSteps = steps.map { it.text }
                val time = if (selected == 5) "${timeState.text} mins" else times[selected]
                viewmodel.uploadRecipe(
                    RawRecipe(
                        name = nameState.text,
                        categories = categories,
                        description = descriptionState.text,
                        equipment = equipment.map { it._id },
                        ingredients = ingredients.map { it._id },
                        image = state.url, time = time, steps = allSteps,
                    )
                )
            }
        }

        FoodiesTheme {
            Surface {
                Scaffold {
                    Column(modifier = Modifier.padding(10.dp).verticalScroll(rememberScrollState())) {
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
                        OutlinedInput(
                            label = "Description",
                            state = descriptionState,
                            type = KeyboardType.Email,
                        )
                        Items(
                            type = "Ingredients",
                            viewmodel = viewmodel,
                            onClick = { ingredients = ingredients + setOf(it) },
                        )
                        FlowRow(modifier = Modifier.padding(top = 10.dp)) {
                            ingredients.forEach {
                                Box(modifier = Modifier
                                    .padding(5.dp)
                                    .border(1.dp, colors.primary, shapes.large)
                                    .clip(shapes.large)
                                    .clickable { ingredients = ingredients - setOf(it) }
                                    .padding(15.dp, 5.dp)) {
                                    Text(
                                        text = it.name,
                                        style = typography.body1.copy(color = colors.primary)
                                    )
                                }
                            }
                        }
                        Items(
                            type = "Equipment",
                            viewmodel = viewmodel,
                            onClick = { equipment = equipment + setOf(it) },
                        )
                        FlowRow(modifier = Modifier.padding(top = 10.dp)) {
                            equipment.forEach {
                                Box(modifier = Modifier
                                    .padding(5.dp)
                                    .border(1.dp, colors.primary, shapes.large)
                                    .clip(shapes.large)
                                    .clickable { equipment = equipment - setOf(it) }
                                    .padding(15.dp, 5.dp)) {

                                    Text(
                                        text = it.name,
                                        style = typography.body1.copy(color = colors.primary)
                                    )
                                }
                            }
                        }
                        TinySpacing()
                        Text(
                            text = "Select the recipe categories",
                            modifier = Modifier.padding(10.dp, 5.dp)
                        )
                        Categories(onChange = { categories = it })
                        Row(
                            horizontalArrangement = SpaceBetween,
                            modifier = Modifier.fillMaxWidth().padding(10.dp, 5.dp)
                        ) {
                            Text("Preparation time", modifier = Modifier.padding(top = 10.dp))
                            Box {
                                val icon = if (expanded) Filled.ArrowDropUp else Filled.ArrowDropDown
                                Row(
                                    verticalAlignment = CenterVertically,
                                    modifier = Modifier
                                        .clip(shapes.small)
                                        .clickable { expanded = true }
                                        .padding(10.dp)
                                ) {
                                    Text(text = times[selected])
                                    Icon(icon, contentDescription = "dropdown icon")
                                }
                                DropdownMenu(
                                    expanded = expanded,
                                    onDismissRequest = { expanded = false },
                                    modifier = Modifier.background(colors.background),
                                ) {
                                    times.forEachIndexed { index, s ->
                                        DropdownMenuItem(onClick = {
                                            selected = index
                                            expanded = false
                                        }) {
                                            Text(text = s)
                                        }
                                    }
                                }
                            }
                        }
                        if (selected == 5) {
                            OutlinedInput(
                                label = null,
                                state = timeState,
                                type = KeyboardType.Number,
                            )
                        }
                        SmallSpacing()
                        Row(
                            horizontalArrangement = SpaceBetween,
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp)
                        ) {
                            Text("Procedure", modifier = Modifier.padding(top = 5.dp))
                            Row(
                                verticalAlignment = CenterVertically,
                                modifier = Modifier
                                    .clip(shapes.small)
                                    .clickable {
                                        steps = steps + listOf(TextFieldState(validators = listOf(Required())))
                                    }
                                    .padding(10.dp)
                            ) {
                                Icon(
                                    Outlined.AddCircleOutline,
                                    modifier = Modifier.size(20.dp),
                                    contentDescription = "add step icon"
                                )
                                Text("Add step", modifier = Modifier.padding(horizontal = 5.dp))
                            }
                        }
                        steps.forEachIndexed { index, state ->
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(5.dp),
                                verticalAlignment = CenterVertically
                            ) {
                                Box(modifier = Modifier.fillMaxWidth(.9f)) {
                                    OutlinedInput(
                                        state = state,
                                        label = "Step ${index + 1}",
                                        type = KeyboardType.Text,
                                    )
                                }
                                IconButton(onClick = { steps = steps - listOf(state) }) {
                                    Column {
                                        SmallSpacing()
                                        Icon(Filled.Delete, contentDescription = "delete step icon")
                                    }
                                }
                            }
                        }
                        SmallSpacing()
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Center) {
                            FilledButton(text = button, size = .95f) {
                                val validSteps = steps.map { it.validate() }.all { it }
                                val validTime = if (selected == 5) timeState.validate() else true

                                when {
                                    loading == true -> {}
                                    path == null -> toast("Upload the recipe image")
                                    steps.isEmpty() -> toast("Add at least one step")
                                    equipment.isEmpty() -> toast("Add at least one equipment")
                                    categories.isEmpty() -> toast("Select at least one category")
                                    ingredients.isEmpty() -> toast("Add at least one ingredient")
                                    nameState.validate() && descriptionState.validate() && validSteps && validTime -> {
                                        viewmodel.uploadImage()
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}