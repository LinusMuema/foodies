package com.moose.foodies.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun FilledButton(text: String, size: Float, loading: Boolean = false, onClick: () -> Unit){
    val style = MaterialTheme.typography.body1
    val color = MaterialTheme.colors.onSecondary
    val label = if (loading) "Loading..." else text
    val modifier = Modifier.fillMaxWidth(fraction = size).fillMaxHeight(fraction = 0.085f)
    val colors = ButtonDefaults.textButtonColors(backgroundColor = MaterialTheme.colors.secondary)

    Button(onClick = onClick, modifier = modifier, colors = colors) {
        Text(label, style = style, color = color)
    }
}

@Composable
fun TextButton(text: String, onClick: () -> Unit){
    val shape = MaterialTheme.shapes.small
    Box(modifier = Modifier.clip(shape).clickable { onClick() }){
        Text(text = text, modifier = Modifier.padding(10.dp, 20.dp))
    }
}