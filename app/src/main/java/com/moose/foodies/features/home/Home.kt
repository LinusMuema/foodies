package com.moose.foodies.features.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.SpaceBetween
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.moose.foodies.R
import com.moose.foodies.components.SmallSpacing
import com.moose.foodies.models.Profile
import java.util.*

@Composable
@ExperimentalCoilApi
@ExperimentalPagerApi
@Preview(name = "Light Theme")
@Preview(name = "Dark Theme", uiMode = UI_MODE_NIGHT_YES)
fun Home(){
    val viewmodel: HomeViewmodel = hiltViewModel()
    val profile = viewmodel.profile.observeAsState().value
    val pagerState = rememberPagerState(pageCount = 10,  initialOffscreenLimit = 2)


    Column {
        SmallSpacing()
        Header(profile = profile)
        SmallSpacing()
        HorizontalPager(
            state = pagerState,
            horizontalAlignment = Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            RecipeCard()
        }
        SmallSpacing()
        Row(
            verticalAlignment = CenterVertically,
            horizontalArrangement = SpaceBetween,
            modifier = Modifier.fillMaxWidth().padding(10.dp)
        ){
            Text(
                text = "Discover Chefs",
                style = typography.h5.copy(fontWeight = SemiBold, color = colors.onSurface)
            )
            Text(
                text = "See all",
                style = typography.body1.copy(color = colors.secondary)
            )
        }
    }
}