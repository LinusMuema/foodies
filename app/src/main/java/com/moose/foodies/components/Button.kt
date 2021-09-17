package com.moose.foodies.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
    val modifier = Modifier.fillMaxWidth(fraction = size).height(40.dp)
    val colors = ButtonDefaults.textButtonColors(backgroundColor = MaterialTheme.colors.secondary)

    Button(onClick = onClick, modifier = modifier, colors = colors) {
        Text(label, style = style, color = color)
    }
}

@Composable
fun TextButton(text: String, onClick: () -> Unit){
    val shape = MaterialTheme.shapes.small
    Box(modifier = Modifier.clip(shape).clickable { onClick() }){
        Text(text = text, modifier = Modifier.padding(20.dp, 10.dp))
    }
}