package com.moose.foodies.features.profile

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.SpaceAround
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.Icons.Outlined
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.github.dhaval2404.imagepicker.ImagePicker
import com.moose.foodies.R
import com.moose.foodies.components.*
import com.moose.foodies.features.add.AddActivity
import com.moose.foodies.util.UploadState.*
import com.moose.foodies.util.getActivity
import com.moose.foodies.util.toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.moose.foodies.components.TinySpacing


@Composable
fun Profile() {
    val viewmodel: ProfileViewmodel = hiltViewModel()

    val user by viewmodel.profile.observeAsState()
    val recipes by viewmodel.recipes.observeAsState()
    var open by remember { mutableStateOf(false) }

        user?.let { profile ->

            if (open)
                AlertDialog(
                    text = null,
                    title = null,
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = colors.background,
                    onDismissRequest = { open = false },
                    buttons = { ProfileDialog(viewmodel) },
                )

            Scaffold(floatingActionButton = { Fab() }) {
                Column(modifier = Modifier.padding(10.dp)) {
                    SmallSpacing()
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = CenterVertically,
                        horizontalArrangement = SpaceAround
                    ) {
                        TinySpacing()
                        Image(
                            painter = rememberImagePainter(
                                data = profile.avatar,
                                builder = { transformations(CircleCropTransformation()) }
                            ),
                            contentDescription = "user avatar",
                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape)
                                .clickable { open = true }
                        )
                        TinySpacing()
                        Column(horizontalAlignment = CenterHorizontally) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = SpaceAround
                            ) {
                                Column(horizontalAlignment = CenterHorizontally) {
                                    Text("24", style = typography.body1.copy(fontWeight = SemiBold))
                                    Text("Recipes", style = typography.h6.copy(fontSize = 16.sp))
                                }
                                Column(horizontalAlignment = CenterHorizontally) {
                                    Text("2.5k", style = typography.body1.copy(fontWeight = SemiBold))
                                    Text("Likes", style = typography.h6.copy(fontSize = 16.sp))
                                }
                            }
                            SmallSpacing()
                            Text(profile.description, textAlign = TextAlign.Center)
                        }
                        TinySpacing()
                    }
                    Text(
                        text = "My recipes",
                        modifier = Modifier.padding(10.dp, 20.dp),
                        style = typography.h5.copy(color = colors.primary),
                    )

                    recipes?.let { if (it.isEmpty()) Empty() else Recipes(viewmodel) }
                }
            }
        }
}

@Composable
fun Recipes(viewmodel: ProfileViewmodel) {
    val items by viewmodel.recipes.observeAsState()
    items?.let {
        LazyColumn {
            items(it) {
                val arrangement = Arrangement.SpaceBetween
                val timeGray = Color.Gray.copy(.8f)

                // create the gradient
                val variant = colors.primaryVariant
                val colors = listOf(Color.Transparent, Color.Transparent, Color.Transparent, variant)
                val gradient = Brush.verticalGradient(colors = colors)

                Box(modifier = Modifier.padding(10.dp)){
                    Card(modifier = Modifier.fillMaxWidth().height(175.dp), elevation = 5.dp) {
                        Image(
                            modifier = Modifier.fillMaxWidth(),
                            contentScale = ContentScale.Crop,
                            painter = rememberImagePainter(data = it.image),
                            contentDescription = "${it.name} image"
                        )
                        Box(modifier = Modifier
                            .fillMaxSize()
                            .background(brush = gradient)
                            .clickable { }
                            .padding(10.dp)){
                            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = arrangement) {
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = arrangement) {
                                    Box(modifier = Modifier
                                        .clip(MaterialTheme.shapes.medium)
                                        .background(timeGray)
                                        .padding(5.dp)) {
                                        Row (verticalAlignment = CenterVertically){
                                            TinySpacing()
                                            Icon(
                                                tint = Color.White,
                                                contentDescription = "time",
                                                modifier = Modifier.size(14.dp),
                                                painter = painterResource(id = R.drawable.ic_clock)
                                            )
                                            TinySpacing()
                                            Text(it.time, color = Color.White, fontSize = 14.sp)
                                            TinySpacing()
                                        }
                                    }
                                }
                                Text(
                                    text = it.name,
                                    style = typography.h6.copy(color = Color.White),
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Empty(){
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading))
    Column(modifier = Modifier.padding(10.dp), horizontalAlignment = CenterHorizontally) {
        MediumSpacing()
        LottieAnimation(
            composition = composition,
            iterations = Int.MAX_VALUE,
            modifier = Modifier.height(200.dp)
        )
        SmallSpacing()
        Text("No recipes around here...")
        Text("Click the button below to add your recipes")
    }
}

@Composable
fun ProfileDialog(viewmodel: ProfileViewmodel) {
    val context = LocalContext.current
    val activity = context.getActivity()!!
    val url by viewmodel.url.observeAsState()
    val progress by viewmodel.progress.observeAsState()
    val user by viewmodel.profile.observeAsState()

    val error by viewmodel.error.observeAsState()
    if (error != null) context.toast(error)

    val loading by viewmodel.loading.observeAsState()
    var button by remember { mutableStateOf("") }
    button = if (loading == true) "Updating profile..." else "Update profile"

    val launcher = rememberLauncherForActivityResult(StartActivityForResult()) {
        val data = it.data
        when (it.resultCode) {
            Activity.RESULT_OK -> viewmodel.uploadAvatar(data?.data!!)
            ImagePicker.RESULT_ERROR -> context.toast(ImagePicker.getError(data))
            else -> context.toast("Image upload cancelled")
        }
    }

    fun pick() = ImagePicker.with(activity).galleryOnly().createIntent { launcher.launch(it) }

    user?.let { profile ->
        val nameState = remember { TextFieldState(profile.username, listOf(Required())) }
        val descriptionState = remember { TextFieldState(profile.description, listOf(Required())) }

        when (val state = progress) {
            is Error -> context.toast(state.message)
            is Success -> viewmodel.updateProfile(profile.copy(avatar = state.url))
            is Loading -> {
                val percentage = (state.current.toDouble() / state.total.toDouble()) * 100
                button = "Uploading image...${percentage.toInt()}%"
            }
        }

        Column(horizontalAlignment = CenterHorizontally, modifier = Modifier.padding(10.dp)) {
            SmallSpacing()
            Box(modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .clickable { pick() }) {
                Image(
                    painter = rememberImagePainter(
                        data = url ?: profile.avatar,
                        builder = { transformations(CircleCropTransformation()) }
                    ),
                    contentDescription = "user avatar",
                )
                Icon(
                    Outlined.CameraAlt,
                    tint = colors.primaryVariant,
                    contentDescription = "camera icon",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            SmallSpacing()
            OutlinedInput(label = "username", type = KeyboardType.Text, state = nameState)
            OutlinedInput(label = "tagline", type = KeyboardType.Text, state = descriptionState)
            TinySpacing()
            FilledButton(text = button, size = .97f) {
                if (descriptionState.validate() && nameState.validate() && !loading!!) {
                    val update = profile.copy(username = nameState.text, description = descriptionState.text)
                    viewmodel.updateProfile(update)
                }
            }
            SmallSpacing()
        }
    }
}

@Composable
fun Fab() {
    val context = LocalContext.current
    val intent = Intent(context, AddActivity::class.java)
    FloatingActionButton(onClick = { context.startActivity(intent) }) {
        Icon(Icons.Default.Add, contentDescription = "add icon")
    }
}