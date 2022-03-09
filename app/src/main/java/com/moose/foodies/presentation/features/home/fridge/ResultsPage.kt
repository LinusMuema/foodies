package com.moose.foodies.presentation.features.home.fridge

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.moose.foodies.presentation.components.SmallSpace
import com.moose.foodies.presentation.theme.typography

@Composable
fun ResultsPage(onOpen: () -> Unit){
    Box(modifier = Modifier.fillMaxSize().padding(10.dp)){
        Column {
            SmallSpace()
            Text(
                text = "What you can cook",
                style = typography.h5.copy(color = colors.primary)
            )
        }
        Box(modifier = Modifier.align(Alignment.BottomEnd)) {
            FloatingActionButton(onClick = { onOpen() }) {
                Icon(Icons.Default.FilterList, contentDescription = "fab icon")
            }
        }
    }
}