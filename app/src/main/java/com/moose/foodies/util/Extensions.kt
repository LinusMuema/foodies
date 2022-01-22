package com.moose.foodies.util

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.widget.Toast
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.unit.dp
import com.moose.foodies.presentation.theme.grey200
import androidx.compose.material.TextFieldDefaults.outlinedTextFieldColors as FieldColors

fun Context.toast(message: String?){
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.getActivity(): Activity? {
    return when (this) {
        is Activity -> this
        is ContextWrapper -> this.baseContext.getActivity()
        else -> null
    }
}

fun Modifier.customTabIndicatorOffset(position: TabPosition): Modifier {
    return composed(debugInspectorInfo { position }) {
        val indicatorWidth = 7.5.dp
        val currentTabWidth = position.width
        val indicatorOffset by animateDpAsState(
            targetValue = position.left + currentTabWidth / 2 - indicatorWidth / 2,
            animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
        )
        fillMaxWidth()
            .wrapContentSize(Alignment.BottomStart)
            .offset(x = indicatorOffset)
            .width(indicatorWidth)
            .clip(MaterialTheme.shapes.large)
    }
}

@Composable
fun MaterialTheme.getTextFieldColors(): TextFieldColors {
    val color = grey200.copy(alpha = .2f)
    return FieldColors(
        backgroundColor = color,
        trailingIconColor = color,
        errorBorderColor = Color.Transparent,
        focusedBorderColor = Color.Transparent,
        unfocusedBorderColor = Color.Transparent,
        focusedLabelColor = colors.onPrimary,
        unfocusedLabelColor = colors.onPrimary,
    )
}