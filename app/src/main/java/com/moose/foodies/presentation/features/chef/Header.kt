package com.moose.foodies.presentation.features.chef

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.SpaceAround
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.moose.foodies.domain.models.Profile
import com.moose.foodies.presentation.components.SmallSpace
import com.moose.foodies.presentation.components.TinySpace
import com.moose.foodies.presentation.theme.typography

@Composable
fun Header(chef: Profile, count: Int){
    Row(modifier = Modifier.fillMaxWidth(),  SpaceAround, CenterVertically) {
        TinySpace()
        Image(
            contentDescription = "user avatar",
            modifier = Modifier.size(100.dp).clip(CircleShape),
            painter = rememberImagePainter(
                data = chef.avatar,
                builder = { transformations(CircleCropTransformation()) }
            ),
        )
        TinySpace()
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = SpaceAround) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = count.toString(),
                        style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.SemiBold)
                    )
                    Text(
                        text = "Recipes",
                        style = MaterialTheme.typography.h6.copy(fontSize = 16.sp)
                    )
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "0",
                        style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.SemiBold)
                    )
                    Text(
                        text = "Likes",
                        style = MaterialTheme.typography.h6.copy(fontSize = 16.sp)
                    )
                }
            }
            SmallSpace()
            Text(chef.description, textAlign = TextAlign.Center)
        }
        TinySpace()
    }
}