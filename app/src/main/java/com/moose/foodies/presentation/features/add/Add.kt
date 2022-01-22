package com.moose.foodies.presentation.features.add

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.systemBarsPadding
import com.moose.foodies.R
import com.moose.foodies.presentation.components.*
import com.moose.foodies.domain.models.Item
import com.moose.foodies.domain.models.RawRecipe
import com.moose.foodies.presentation.theme.FoodiesTheme
import com.moose.foodies.presentation.theme.shapes
import com.moose.foodies.util.*


@Composable
fun Add(controller: NavController) {
    val viewmodel: AddViewmodel = hiltViewModel()
    val path by viewmodel.path.observeAsState()
    val context = LocalContext.current
    val activity = context.getActivity()!!
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

    val result by viewmodel.result.observeAsState()
    result?.let {
        it.onSuccess { controller.navigateUp() }
        it.onError { error -> context.toast(error) }
    }

    val launcher = rememberLauncherForActivityResult(StartActivityForResult()) {
        val data = it.data
        when (it.resultCode) {
            Activity.RESULT_OK -> viewmodel.setUri(data?.data!!)
            ImagePicker.RESULT_ERROR -> context.toast(ImagePicker.getError(data))
            else -> context.toast("Image upload cancelled")
        }
    }

    fun getImage() = ImagePicker.with(activity).galleryOnly().createIntent { launcher.launch(it) }

    when (val state = progress){
        is UploadState.Error -> context.toast(state.message)
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
            ProvideWindowInsets {
                Scaffold(modifier = Modifier.systemBarsPadding()) {
                    Column(modifier = Modifier.padding(10.dp).verticalScroll(rememberScrollState())) {
                        TinySpace()
                        IconButton(onClick = { controller.popBackStack() }) {
                            Icon(
                                modifier = Modifier.size(30.dp).padding(5.dp),
                                painter = painterResource(id = R.drawable.ic_back),
                                contentDescription = "back icon",
                            )
                        }
                        SmallSpace()
                        Text(
                            text = "Upload a new recipe",
                            style = typography.h6.copy(color = colors.onSurface)
                        )
                        SmallSpace()
                        ImageUpload(path = path, onClick = { getImage() })
                        SmallSpace()
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
                        TinySpace()
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
                        SmallSpace()
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
                                        steps = steps + listOf(
                                            TextFieldState(validators = listOf(Required()))
                                        )
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
                                        SmallSpace()
                                        Icon(Filled.Delete, contentDescription = "delete step icon")
                                    }
                                }
                            }
                        }
                        SmallSpace()
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Center) {
                            FilledButton(text = button, size = .95f) {
                                val validSteps = steps.map { it.validate() }.all { it }
                                val validTime = if (selected == 5) timeState.validate() else true

                                when {
                                    loading == true -> {}
                                    path == null -> context.toast("Upload the recipe image")
                                    steps.isEmpty() -> context.toast("Add at least one step")
                                    equipment.isEmpty() -> context.toast("Add at least one equipment")
                                    categories.isEmpty() -> context.toast("Select at least one category")
                                    ingredients.isEmpty() -> context.toast("Add at least one ingredient")
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
