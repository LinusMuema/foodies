package com.moose.foodies.presentation.features.home.feed

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.moose.foodies.R
import com.moose.foodies.domain.models.Profile
import com.moose.foodies.presentation.components.*
import com.moose.foodies.util.toast


@Composable
@ExperimentalCoilApi
@ExperimentalPagerApi
fun Feed(controller: NavController) {
    val viewmodel: FeedViewmodel = hiltViewModel()

    val error by remember { viewmodel.error }
    error?.let { LocalContext.current.toast(it) }

    val chefs by remember { viewmodel.chefs }
    val counter by remember { viewmodel.seconds }
    val featured by remember { viewmodel.featured }
    val refreshing by remember { viewmodel.refreshing }

    val profile by viewmodel.profile.collectAsState(initial = null)

    SwipeRefresh(
        state = rememberSwipeRefreshState(refreshing),
        onRefresh = { viewmodel.refresh() },
        indicator = { state, trigger -> SwipeIndicator(state, trigger) },
        content = {
            ScrollableColumn {
                SmallSpace()
                profile?.let { Header(profile = it) }
                SmallSpace()


                if (counter >= 5 && !featured.isNullOrEmpty()) {

                    val highlightState = rememberPagerState(initialPage = 0)
                    HorizontalPager(
                        count = featured.size,
                        state = highlightState,
                        contentPadding = PaddingValues(end = 125.dp),
                        content = { RecipeCard(controller, featured[it]) }
                    )

                    SmallSpace()
                    Text(
                        text = "Discover Chefs",
                        modifier = Modifier.padding(horizontal = 10.dp),
                        style = typography.h5.copy(color = colors.primary)
                    )
                    SmallSpace()
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(20.dp),
                        contentPadding = PaddingValues(horizontal = 10.dp),
                        content = {
                            items(chefs) {
                                Chef(chef = it) {
                                    controller.navigate("/chef/${it._id}")
                                }
                            }
                        },
                    )

                    SmallSpace()
                    Recipes(controller = controller)

                } else {
                    Loading()
                }
            }
        }
    )
}

@Composable
@ExperimentalCoilApi
fun Chef(chef: Profile, onClick : () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        NetImage(
            url = chef.avatar,
            modifier = Modifier
                .size(75.dp)
                .clip(CircleShape)
                .clickable { onClick() }
        )
        TinySpace()
        Text(chef.username, style = typography.body1)
    }
}

@Composable
fun Loading() {
    val height = LocalConfiguration.current.screenHeightDp
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.cooking))

    CenterColumn(modifier = Modifier.height((height * .7).dp)) {
        Text(
            text = "Getting you some recipes...",
            style = typography.body1.copy(color = colors.onSurface)
        )
        TinySpace()
        LottieAnimation(
            composition = composition,
            iterations = Int.MAX_VALUE,
            modifier = Modifier.size(250.dp)
        )
    }
}

@Composable
fun SwipeIndicator(state: SwipeRefreshState, trigger: Dp) {
    SwipeRefreshIndicator(
        scale = true,
        state = state,
        contentColor = colors.primary,
        refreshTriggerDistance = trigger,
        backgroundColor = colors.background,
    )
}
