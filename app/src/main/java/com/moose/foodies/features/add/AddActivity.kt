package com.moose.foodies.features.add

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.unit.dp
import com.moose.foodies.theme.FoodiesTheme

class AddActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { Content() }
    }

    @Composable
    private fun Content(){
        FoodiesTheme {
            Column(modifier = Modifier.padding(10.dp)) {
                Text(
                    text = "Upload a new recipe",
                    style = typography.h6.copy(fontWeight = SemiBold, color = colors.onSurface)
                )
            }
        }
    }
}