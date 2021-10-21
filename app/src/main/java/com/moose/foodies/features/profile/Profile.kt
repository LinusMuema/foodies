package com.moose.foodies.features.profile

import android.content.Intent
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.moose.foodies.components.CenterColumn
import com.moose.foodies.components.MediumSpacing
import com.moose.foodies.components.SmallSpacing
import com.moose.foodies.components.TinySpacing
import com.moose.foodies.features.add.AddActivity

@Composable
fun Profile() {
    val viewmodel: ProfileViewmodel = hiltViewModel()
    val profile by viewmodel.profile.observeAsState()

    Scaffold(backgroundColor = colors.primary, floatingActionButton = { Fab() }) {
        CenterColumn(modifier = Modifier.padding(10.dp).verticalScroll(rememberScrollState())) {
            SmallSpacing()
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = CenterVertically,
                horizontalArrangement = SpaceAround
            ) {
               TinySpacing()
                Image(
                    painter = rememberImagePainter(
                        data = "https://avatars.githubusercontent.com/u/47350130?v=4",
                        builder = {
                            transformations(CircleCropTransformation())
                        }
                    ),
                    contentDescription = "user avatar",
                    modifier = Modifier.size(100.dp).clip(CircleShape).clickable { }
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
fun Fab() {
    val context = LocalContext.current
    val intent = Intent(context, AddActivity::class.java)
    FloatingActionButton(onClick = { context.startActivity(intent) }) {
        Icon(Icons.Default.Add, contentDescription = "add icon")
    }
}