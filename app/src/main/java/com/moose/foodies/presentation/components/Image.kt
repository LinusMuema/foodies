package com.moose.foodies.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter.State.Error
import coil.compose.ImagePainter.State.Loading
import coil.compose.rememberImagePainter
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.fade
import com.google.accompanist.placeholder.material.placeholder
import com.moose.foodies.R


@Composable
@ExperimentalCoilApi
fun NetImage(modifier: Modifier = Modifier, url: String, description: String = ""){
    val painter = rememberImagePainter(data = url, builder = { crossfade(true) })

    when (painter.state){
        is Loading -> {
            Box(
                content = {},
                modifier = modifier.placeholder(
                    visible = true,
                    color = colors.primaryVariant.copy(alpha = .3f),
                    highlight = PlaceholderHighlight.fade()
                )
            )
        }
        is Error -> {
            Image(
                contentDescription = "error",
                modifier = Modifier.width(10.dp),
                painter = painterResource(id = R.drawable.error)
            )
        }
        else -> {}
    }

    Image(
        painter = painter,
        modifier = modifier,
        contentScale = ContentScale.Crop,
        contentDescription = description,
    )

}