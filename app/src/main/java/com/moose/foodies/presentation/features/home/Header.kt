package com.moose.foodies.presentation.features.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.SpaceBetween
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.moose.foodies.R
import com.moose.foodies.domain.models.Profile
import java.util.*

@Composable
fun Header(profile: Profile?){
    Box(modifier = Modifier.padding(10.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = SpaceBetween) {
            Column {
                Text(
                    text = "Hi ${profile?.username},",
                    style = typography.h5.copy(color = colors.primary),
                )
                TimeCaption()
            }
            Spacer(modifier = Modifier)
            Box(
                contentAlignment = Center,
                modifier = Modifier.size(36.dp).clip(shapes.large).clickable { },
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    contentDescription = "notifications",
                    painter = painterResource(id = R.drawable.ic_bell),
                )
            }
        }
    }
}

@Composable
private fun TimeCaption(){
    val time = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    val caption = when {
        time >= 22 -> "Feeling snackish late at night?"
        time >= 18 -> "End your day well with these recipes!"
        time >= 15 -> "Some afternoon snacks for you"
        time >= 12 -> "Power your day with these lunch time recipes!"
        time >= 9 -> "Interested in these snacks?"
        time >= 4 -> "Start your day with these recipes!"
        else -> "Feeling snackish late at night?"
    }

    Text(caption, style = typography.body1)
}