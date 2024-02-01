package com.myeong.prography.random

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.alexstyl.swipeablecard.Direction
import com.alexstyl.swipeablecard.ExperimentalSwipeableCardApi
import com.alexstyl.swipeablecard.rememberSwipeableCardState
import com.alexstyl.swipeablecard.swipableCard
import com.myeong.prography.domain.model.Photo
import com.myeong.prography.ui.R
import kotlinx.coroutines.launch

/**
 * Created by MyeongKi.
 */

@Composable
fun RandomRoute(viewModel: RandomViewModel) {
    val uiState = viewModel.uiState.collectAsState().value
    val intentInvoker = remember {
        viewModel.intentInvoker
    }
    val eventInvoker = remember {
        viewModel.eventInvoker
    }
    LaunchedEffect(true) {
        eventInvoker(RandomEvent.LoadRandomPhotos)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
            .padding(top = 28.dp)
            .padding(bottom = 44.dp)
    ) {
        uiState.photos.reversed().forEach {
            PhotoCard(
                photo = it,
                leftSwipe = {
                    intentInvoker(RandomIntent.ToSwipeLeft)
                },
                onClickInfo = {
                    intentInvoker(RandomIntent.ClickInfo)
                },
                rightSwipe = {
                    intentInvoker(RandomIntent.ToSwipeRight)
                }
            )
        }
    }
}

@OptIn(ExperimentalSwipeableCardApi::class)
@Composable
fun PhotoCard(
    photo: Photo,
    onClickInfo: () -> Unit,
    leftSwipe: () -> Unit,
    rightSwipe: () -> Unit
) {
    val state = rememberSwipeableCardState()
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .swipableCard(
                state = state,
                onSwiped = { direction ->
                    if (direction == Direction.Right) {
                        rightSwipe()
                    } else if (direction == Direction.Left) {
                        leftSwipe()

                    }
                },
                onSwipeCancel = {
                    println("The swiping was cancelled")
                }
            )
            .border(1.dp, colorResource(id = R.color.gray_30), RoundedCornerShape(15.dp))
            .background(Color.White)

    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
                .background(Color.Black, RoundedCornerShape(10.dp))
                .weight(1f)
                .clip(RoundedCornerShape(10.dp))
        ) {
            val aspectRatio = photo.imageWidth.toFloat() / photo.imageHeight.toFloat()

            SubcomposeAsyncImage(
                model = photo.imageUrl.small,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(aspectRatio)
                    .clipToBounds(),
                contentDescription = "photo"
            )
        }
        Row(
            modifier = Modifier
                .height(120.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(30.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            SmallCircleButton(
                iconRes = R.drawable.x,
                onClickItem = {
                    scope.launch {
                        state.swipe(Direction.Left)
                    }
                },
            )
            BookmarkButton(onClickItem = {
                scope.launch {
                    state.swipe(Direction.Right)
                }
            })
            SmallCircleButton(
                iconRes = R.drawable.info,
                onClickItem = onClickInfo
            )
        }
    }
}

@Composable
fun BookmarkButton(
    onClickItem: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(72.dp)
            .background(colorResource(id = R.color.brandcolor), CircleShape)
            .clickable(onClick = onClickItem)
    ) {
        Image(
            painter = painterResource(id = R.drawable.bookmark),
            contentDescription = "close",
            modifier = Modifier
                .wrapContentWidth()
                .size(32.dp)
                .align(Alignment.Center),
            colorFilter = ColorFilter.tint(Color.White)

        )
    }
}

@Composable
fun SmallCircleButton(
    iconRes: Int,
    onClickItem: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(52.dp)
            .border(1.dp, colorResource(id = R.color.gray_30), CircleShape)
            .clickable(onClick = onClickItem)
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = "info",
            modifier = Modifier
                .wrapContentWidth()
                .size(36.dp)
                .align(Alignment.Center),
            colorFilter = ColorFilter.tint(colorResource(id = R.color.gray_60))
        )
    }
}