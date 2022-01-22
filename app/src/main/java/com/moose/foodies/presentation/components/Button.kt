package com.moose.foodies.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults.textButtonColors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.moose.foodies.presentation.theme.smallPadding
import com.moose.foodies.presentation.theme.tinyPadding

@Composable
fun FilledButton(text: String, modifier: Modifier = Modifier, loading: Boolean = false, onClick: () -> Unit){
    val colors = textButtonColors(backgroundColor = colors.secondary, contentColor = colors.onSecondary)
    Button(onClick = onClick, modifier = modifier, colors = colors, enabled = !loading) {
        Text(
            modifier = Modifier.tinyPadding(),
            style = MaterialTheme.typography.body1,
            text = if (loading) "Loading..." else text,
        )
    }
}

@Composable
fun TextButton(text: String, onClick: () -> Unit){
    val shape = MaterialTheme.shapes.small
    Box(modifier = Modifier.clip(shape).clickable { onClick() }){
        Text(text = text, modifier = Modifier.padding(20.dp, 10.dp))
    }
}