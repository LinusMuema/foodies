package com.moose.foodies.features.home

import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.SpaceBetween
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ContentScale.Companion.Crop
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.moose.foodies.R
import com.moose.foodies.R.drawable.ic_clock
import com.moose.foodies.R.drawable.sample
import com.moose.foodies.components.SmallSpacing
import com.moose.foodies.components.TinySpacing
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
    }
}

@Composable
private fun Header(profile: Profile?){
    Box(modifier = Modifier.padding(10.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = SpaceBetween) {
            Column {
                Text(
                    "Hi ${profile?.username},",
                    fontWeight = SemiBold,
                    style = typography.h5,
                    color = colors.onSurface
                )
                TimeCaption()
            }
            Spacer(modifier = Modifier)
            Box(
                contentAlignment = Center,
                modifier = Modifier
                    .size(36.dp)
                    .clip(shapes.large)
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

    Text(caption, style = typography.body1)
}

@Composable
@ExperimentalCoilApi
private fun RecipeCard(){
    val arrangement = SpaceBetween
    val variant = colors.secondaryVariant

    // create the gradient
    val colors = listOf(Transparent, Transparent, Transparent, variant)
    val gradient = Brush.verticalGradient(colors = colors)

    Box(modifier = Modifier.padding(start = 10.dp, end = 10.dp)){
        Card(modifier = Modifier
            .fillMaxWidth(0.85f)
            .fillMaxHeight(0.275f)) {
            Image(
                modifier = Modifier.fillMaxWidth(),
                contentScale = Crop,
                painter = painterResource(id = sample),
                contentDescription = "sample"
            )
            Box(modifier = Modifier
                .fillMaxSize()
                .background(brush = gradient)
                .padding(10.dp)){
                Column(modifier = Modifier.fillMaxSize(), verticalArrangement = arrangement) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = arrangement) {
                        Box(modifier = Modifier
                            .clip(shape = shapes.medium)
                            .background(Gray.copy(.7f))
                            .padding(5.dp)) {

                            Row (verticalAlignment = Alignment.CenterVertically){
                                TinySpacing()
                                Icon(
                                    contentDescription = "time",
                                    modifier = Modifier.size(14.dp),
                                    tint = White,
                                    painter = painterResource(id = ic_clock)
                                )
                                TinySpacing()
                                Text("30 mins", color = White, fontSize = 14.sp)
                                TinySpacing()
                            }
                        }
                        Box(modifier = Modifier.clickable { }
                            .clip(shapes.large).background(variant).padding(2.5.dp)){
                            Image(
                                painter = rememberImagePainter(
                                    data = "https://avatars.githubusercontent.com/u/47350130?v=4",
                                    builder = {
                                        transformations(CircleCropTransformation())
                                    }
                                ),
                                contentDescription = "chef avatar",
                                modifier = Modifier.size(35.dp)
                            )
                        }
                    }
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            "Pancakes",
                            style = typography.h6.copy(color = White),
                            fontWeight = FontWeight.Bold
                        )
                        Text("3 stars", color = White)
                    }
                }
            }
        }
    }
}