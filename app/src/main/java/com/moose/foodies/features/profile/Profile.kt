package com.moose.foodies.features.profile

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.SpaceAround
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.github.dhaval2404.imagepicker.ImagePicker
import com.moose.foodies.components.CenterColumn
import com.moose.foodies.components.SmallSpacing
import com.moose.foodies.components.TinySpacing
import com.moose.foodies.features.add.AddActivity
import com.moose.foodies.models.Profile
import com.moose.foodies.util.getActivity
import com.moose.foodies.util.toast

@Composable
fun Profile() {
    val viewmodel: ProfileViewmodel = hiltViewModel()
    val profile by viewmodel.profile.observeAsState()
    var open by remember { mutableStateOf(false)  }

    if (open)
        AlertDialog(
            buttons = {},
            text = { ProfileDialog(viewmodel, profile!!) },
            backgroundColor = colors.background,
            onDismissRequest = { open = false },
            title = { Text(text = "Update your profile") },
        )

    Scaffold(floatingActionButton = { Fab() }) {
        CenterColumn(modifier = Modifier
            .padding(10.dp)
            .verticalScroll(rememberScrollState())) {
            SmallSpacing()
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = CenterVertically,
                horizontalArrangement = SpaceAround
            ) {
               TinySpacing()
                Image(
                    painter = rememberImagePainter(
                        data = profile?.avatar,
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
                    Row(modifier = Modifier.fillMaxSize(),  horizontalArrangement = SpaceAround) {
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
                    Text("Your awesome tagline here...", textAlign = TextAlign.Center)
                }
                TinySpacing()
            }
        }
    }
}


@Composable
fun ProfileDialog(viewmodel: ProfileViewmodel, profile: Profile){
    val context = LocalContext.current
    val activity = context.getActivity()!!
    val url by viewmodel.path.observeAsState()

    val launcher = rememberLauncherForActivityResult(StartActivityForResult()){
        val data = it.data
        when (it.resultCode) {
            Activity.RESULT_OK -> viewmodel.setUri(data?.data!!)
            ImagePicker.RESULT_ERROR -> context.toast(ImagePicker.getError(data))
            else -> context.toast("Image upload cancelled")
        }
    }

    fun pick() = ImagePicker.with(activity).galleryOnly().createIntent { launcher.launch(it) }

    Column {
        Box(modifier = Modifier.size(100.dp).clip(CircleShape).clickable { pick() }) {
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