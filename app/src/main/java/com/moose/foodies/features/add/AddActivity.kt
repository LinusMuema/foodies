package com.moose.foodies.features.add

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.text.Layout
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.Icons.Outlined
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.AddCircleOutline
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.accompanist.flowlayout.FlowRow
import com.moose.foodies.components.*
import com.moose.foodies.features.add.ui.ImageUpload
import com.moose.foodies.features.add.ui.Items
import com.moose.foodies.models.Item
import com.moose.foodies.theme.FoodiesTheme
import com.moose.foodies.theme.shapes
import com.moose.foodies.util.toast
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

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
        var selected by remember { mutableStateOf(0) }
        var expanded by remember { mutableStateOf(false) }
        var equipment by remember { mutableStateOf(setOf<Item>()) }
        var ingredients by remember { mutableStateOf(setOf<Item>()) }
        var steps by remember { mutableStateOf(listOf<TextFieldState>()) }
        val nameState = remember { TextFieldState(validators = listOf(Required())) }
        val timeState = remember { TextFieldState(validators = listOf(Required())) }
        val descriptionState = remember { TextFieldState(validators = listOf(Required())) }
        val times = listOf("10 mins", "15 mins", "30 mins", "45 mins", "60 mins", "Custom (mins)")

        FoodiesTheme {
            Surface(color = colors.primary) {
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
                    SmallSpacing()
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
                                .border(1.dp, colors.onSurface, shapes.large)
                                .clip(shapes.large)
                                .clickable { ingredients = ingredients - setOf(it) }
                                .padding(15.dp, 5.dp)) {

                                Text(text = it.name)
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
                                .border(1.dp, colors.onSurface, shapes.large)
                                .clip(shapes.large)
                                .clickable { equipment = equipment - setOf(it) }
                                .padding(15.dp, 5.dp)) {

                                Text(text = it.name)
                            }
                        }
                    }
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp)
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
                            ){
                                Text(text = times[selected])
                                Icon(icon, contentDescription = "dropdown icon")
                            }
                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false },
                                modifier = Modifier.background(colors.primary),
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
                    if (selected == 5){
                        OutlinedInput(
                            label = null,
                            state = timeState,
                            type = KeyboardType.Number,
                        )
                    }
                    SmallSpacing()
                    Row (
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp)
                    ){
                        Text("Procedure", modifier = Modifier.padding(top = 5.dp))
                        Row(
                            verticalAlignment = CenterVertically,
                            modifier = Modifier
                                .clip(shapes.small)
                                .clickable {
                                    steps =
                                        steps + listOf(TextFieldState(validators = listOf(Required())))
                                }
                                .padding(10.dp)
                        ) {
                            Icon(Outlined.AddCircleOutline, modifier = Modifier.size(20.dp), contentDescription = "add step icon")
                            Text("Add step",modifier = Modifier.padding(horizontal = 5.dp))
                        }
                    }
                    steps.forEachIndexed { index, state ->
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(5.dp),
                            verticalAlignment = CenterVertically
                        )  {
                            Box(modifier = Modifier.fillMaxWidth(.9f)) {
                                OutlinedInput(
                                    state = state,
                                    label = "Step ${index+1}",
                                    type = KeyboardType.Text,
                                )
                            }
                            IconButton(onClick = { steps = steps - listOf(state) }) {
                                Column{
                                    SmallSpacing()
                                    Icon(Filled.Delete, contentDescription = "delete step icon")
                                }
                            }
                        }
                    }
                    SmallSpacing()
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Center){
                        FilledButton(text = "Upload", size = .9f, onClick = {})
                    }
                }
            }
        }
    }
}