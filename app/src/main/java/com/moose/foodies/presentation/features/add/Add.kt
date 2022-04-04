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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dsc.form_builder.SelectState
import com.dsc.form_builder.TextFieldState
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.systemBarsPadding
import com.moose.foodies.R
import com.moose.foodies.presentation.components.*
import com.moose.foodies.presentation.theme.*
import com.moose.foodies.util.*

@Composable
fun Add(controller: NavController) {
    val context = LocalContext.current
    val activity = context.getActivity()!!

    val viewmodel: AddViewmodel = hiltViewModel()
    val path by viewmodel.path.observeAsState()
    val loading by viewmodel.loading.observeAsState()
    val progress by viewmodel.progress.observeAsState()

    val ingredients by remember { mutableStateOf(viewmodel.getItems("", "Ingredients")) }
    var equipment by remember { mutableStateOf(viewmodel.getItems("", "Equipment")) }


    var selected by remember { mutableStateOf(0) }
    var expanded by remember { mutableStateOf(false) }
    var button by remember { mutableStateOf("Upload") }

    val formState = remember { viewmodel.formState }

    val steps: SelectState = formState.getState("steps")
    val nameState: TextFieldState = formState.getState("name")
    val timeState: TextFieldState = formState.getState("time")
    val imageState: TextFieldState = formState.getState("image")
    val equipmentState: SelectState = formState.getState("equipment")
    val categoriesState : SelectState = formState.getState("categories")
    val ingredientsState: SelectState = formState.getState("ingredients")
    val descriptionState: TextFieldState = formState.getState("description")

    val times = listOf("10 mins", "15 mins", "30 mins", "45 mins", "60 mins", "Custom (mins)")

    val inputModifier = Modifier.fillMaxWidth()
    val labelModifier = Modifier.fillMaxWidth().smallVPadding()

    val result by viewmodel.result.observeAsState()
    result?.let {
        it.onSuccess { controller.navigateUp() }
        it.onError { error -> context.toast(error) }
    }

    val launcher = rememberLauncherForActivityResult(StartActivityForResult()) {
        val data = it.data
        imageState.hideError()
        when (it.resultCode) {
            Activity.RESULT_OK -> viewmodel.setUri(data?.data!!)
            ImagePicker.RESULT_ERROR -> context.toast(ImagePicker.getError(data))
            else -> context.toast("Image upload cancelled")
        }
    }

    fun getImage() = ImagePicker.with(activity).galleryOnly().createIntent { launcher.launch(it) }

//    when (val state = progress){
//        is UploadState.Error -> context.toast(state.message)
//        is UploadState.Loading -> {
//            val percentage = (state.current.toDouble() / state.total.toDouble()) * 100
//            button = "Uploading image...${percentage.toInt()}%"
//        }
//        is UploadState.Success -> {
//            button = "Saving recipe..."
//            val allSteps = steps.map { it.text }
//            val time = if (selected == 5) "${timeState.text} mins" else times[selected]
//            viewmodel.uploadRecipe(
//                RawRecipe(
//                    name = nameState.text,
//                    categories = categories,
//                    description = descriptionState.text,
//                    equipment = equipment.map { it._id },
//                    ingredients = ingredients.map { it._id },
//                    image = state.url, time = time, steps = allSteps,
//                )
//            )
//        }
//    }

    FoodiesTheme {
        Surface {
            ProvideWindowInsets {
                Scaffold(modifier = Modifier.systemBarsPadding()) {
                    Column(modifier = Modifier
                        .padding(10.dp)
                        .verticalScroll(rememberScrollState())) {
                        TinySpace()
                        IconButton(onClick = { controller.popBackStack() }) {
                            Icon(
                                modifier = Modifier
                                    .size(30.dp)
                                    .padding(5.dp),
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
                        if (imageState.hasError){
                            TinySpace()
                            Text(imageState.errorMessage, color = colors.error)
                        }

                        SmallSpace()

                        Text(text = "Name", modifier = labelModifier, textAlign = TextAlign.Start)
                        TextInput(state = nameState, modifier = inputModifier, type = KeyboardType.Email)

                        SmallSpace()

                        Text(text = "Description", modifier = labelModifier, textAlign = TextAlign.Start)
                        TextInput(state = descriptionState, modifier = inputModifier)

                        SmallSpace()

                        Items(
                            type = "Ingredients",
                            viewmodel = viewmodel,
                            onClick = { ingredientsState.select(it._id) },
                        )
                        if (ingredientsState.hasError){
                            TinySpace()
                            Text(ingredientsState.errorMessage, color = colors.error)
                        }
                        TinySpace()
                        FlowRow {
                            ingredientsState.value.forEach {
                                Box(modifier = Modifier
                                    .padding(5.dp)
                                    .border(1.dp, colors.primary, shapes.large)
                                    .clip(shapes.large)
                                    .clickable { ingredientsState.unselect(it) }
                                    .padding(15.dp, 5.dp)) {
                                    Text(
                                        text = ingredients.first { i -> i._id == it }.name,
                                        style = typography.body1.copy(color = colors.primary)
                                    )
                                }
                            }
                        }

                        SmallSpace()

                        Items(
                            type = "Equipment",
                            viewmodel = viewmodel,
                            onClick = { equipmentState.select(it._id) },
                        )
                        if (equipmentState.hasError){
                            TinySpace()
                            Text(equipmentState.errorMessage, color = colors.error)
                        }
                        TinySpace()
                        FlowRow(modifier = Modifier.padding(top = 10.dp)) {
                            equipmentState.value.forEach {
                                Box(modifier = Modifier
                                    .padding(5.dp)
                                    .border(1.dp, colors.primary, shapes.large)
                                    .clip(shapes.large)
                                    .clickable { equipmentState.unselect(it) }
                                    .padding(15.dp, 5.dp)) {

                                    Text(
                                        text = equipment.first { e -> e._id == it }.name,
                                        style = typography.body1.copy(color = colors.primary)
                                    )
                                }
                            }
                        }

                        SmallSpace()

                        Text(
                            text = "Select the recipe categories",
                            modifier = Modifier.padding(10.dp, 5.dp)
                        )
                        Categories(state = categoriesState)
                        if (categoriesState.hasError){
                            TinySpace()
                            Text(categoriesState.errorMessage, color = colors.error)
                        }

                        SmallSpace()
                        Row(
                            horizontalArrangement = SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
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
                                            timeState.change(times[index])
                                            expanded = false
                                        }) {
                                            Text(text = s)
                                        }
                                    }
                                }
                            }
                        }
                        if (selected == 5) {
                            TextInput(state = timeState, modifier = inputModifier)
                            if (timeState.hasError){
                                TinySpace()
                                Text(timeState.errorMessage, color = colors.error)
                            }
                        }
                        SmallSpace()
                        Row(
                            horizontalArrangement = SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp)
                        ) {
                            Text("Procedure", modifier = Modifier.padding(top = 5.dp))
                            Row(
                                verticalAlignment = CenterVertically,
                                modifier = Modifier
                                    .clip(shapes.small)
                                    .clickable { steps.select("") }
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
                        steps.value.forEachIndexed { index, step ->
                            val state = TextFieldState("$index")
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(5.dp),
                                verticalAlignment = CenterVertically
                            ) {
                                Column(modifier = Modifier.fillMaxWidth(.9f)) {
                                    Text(text = "Step ${index+1}", modifier = labelModifier, textAlign = TextAlign.Start)
                                    TextInput(state = state, modifier = inputModifier)
                                }
                                IconButton(onClick = { steps.unselect(step) }) {
                                    Column {
                                        SmallSpace()
                                        Icon(Filled.Delete, contentDescription = "delete step icon")
                                    }
                                }
                            }
                        }
                        if (steps.hasError){
                            TinySpace()
                            Text(steps.errorMessage, color = colors.error)
                        }
                        SmallSpace()
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Center) {
                            FilledButton(text = button, modifier = Modifier.fillMaxWidth()) {
                                if (path != null){
                                    viewmodel.uploadImage()
                                } else {
                                    imageState.showError("The recipe image is required")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
