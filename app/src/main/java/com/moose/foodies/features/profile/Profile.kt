package com.moose.foodies.features.profile

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.moose.foodies.components.CenterColumn
import com.moose.foodies.features.add.AddActivity

@Composable
fun Profile() {
    Scaffold(backgroundColor = colors.primary, floatingActionButton = { Fab() }) {
        CenterColumn(modifier = Modifier.padding(10.dp).verticalScroll(rememberScrollState())) {
            Image(
                painter = rememberImagePainter(
                    data = "https://avatars.githubusercontent.com/u/47350130?v=4",
                    builder = {
                        transformations(CircleCropTransformation())
                    }
                ),
                contentDescription = "user avatar",
                modifier = Modifier
                    .size(125.dp)
                    .clip(MaterialTheme.shapes.large)
                    .clickable { }
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