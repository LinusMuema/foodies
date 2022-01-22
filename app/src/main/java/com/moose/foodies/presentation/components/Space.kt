package com.moose.foodies.presentation.components

import android.widget.Space
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TinySpace() = Spacer(modifier = Modifier.size(5.dp))

@Composable
fun SmallSpace() = Spacer(modifier = Modifier.size(20.dp))

@Composable
fun MediumSpace() = Spacer(modifier = Modifier.size(15.dp))