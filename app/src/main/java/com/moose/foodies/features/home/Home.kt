package com.moose.foodies.features.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.moose.foodies.R
import com.moose.foodies.components.SmallSpacing
import com.moose.foodies.models.Profile
import java.util.*

@Composable
@ExperimentalPagerApi
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
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            RecipeCard()
        }
    }
}

@Composable
private fun Header(profile: Profile?){
    Box(modifier = Modifier.padding(10.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Column {
                Text(
                    "Hi ${profile?.username},",
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.onSurface
                )
                TimeCaption()
            }
            Spacer(modifier = Modifier)
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(36.dp)
                    .clip(MaterialTheme.shapes.large)
                    .clickable { },
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
        else -> "Start your day with these recipes!"
    }

    Text(caption, style = MaterialTheme.typography.body1)
}

@Composable
private fun RecipeCard(){
    Box(modifier = Modifier.padding(start = 10.dp, end = 10.dp)){
        Card(modifier = Modifier.fillMaxWidth(0.85f).fillMaxHeight(0.275f)) {
            Image(
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop,
                painter = painterResource(id = R.drawable.sample),
                contentDescription = "sample"
            )
            Box(modifier = Modifier.fillMaxSize()){
                Column {

                }
            }
        }
    }
}