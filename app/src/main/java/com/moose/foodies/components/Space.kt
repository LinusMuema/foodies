package com.moose.foodies.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SmallSpacing() = Box(modifier = Modifier.padding(10.dp))

@Composable
fun MediumSpacing() = Box(modifier = Modifier.padding(15.dp))