package com.myeong.prography.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.myeong.prography.ui.DefaultIndicator
import com.myeong.prography_aos.R
import kotlinx.collections.immutable.ImmutableList

/**
 * Created by MyeongKi.
 */
@Composable
fun DetailRoute(viewModel: DetailViewModel, photoId: String) {
    val uiState = viewModel.uiState.collectAsState().value
    val intentInvoker = remember {
        viewModel.intentInvoker
    }
    val eventInvoker = remember {
        viewModel.eventInvoker
    }
    LaunchedEffect(photoId){
        eventInvoker(DetailEvent.UpdateDetail(photoId))
    }
    when (uiState) {
        is DetailUiState.Loading -> {
            LoadingDetailDataScreen()
        }

        is DetailUiState.HasData -> {
            HasDetailDataScreen(uiState, intentInvoker)
        }
    }
}

@Composable
fun LoadingDetailDataScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.9f))
            .pointerInput(Unit) {
            }
    ) {
        DefaultIndicator(modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
fun HasDetailDataScreen(
    uiState: DetailUiState.HasData,
    intentInvoker: (DetailIntent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.9f))
            .pointerInput(Unit) {
            }
    ) {
        DetailHeader(
            userName = uiState.userName,
            isBookmark = uiState.isBookmark,
            onClickClose = {
                intentInvoker(DetailIntent.ClickClose)
            },
            onClickBookmark = {
                intentInvoker(DetailIntent.ClickBookmark)
            }
        )
        DetailBody(
            modifier = Modifier.weight(1f),
            imageHeight = uiState.imageHeight,
            imageWidth = uiState.imageWidth,
            imageUrl = uiState.imageUrl
        )
        DetailBottom(
            title = uiState.title,
            description = uiState.description,
            tags = uiState.tags
        )
    }
}

@Composable
fun DetailHeader(
    userName: String,
    isBookmark: Boolean,
    onClickClose: () -> Unit,
    onClickBookmark: () -> Unit
) {
    Row(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(Color.White, CircleShape)
                .clickable(onClick = onClickClose)
        ) {
            Image(
                painter = painterResource(id = R.drawable.x),
                contentDescription = "close",
                modifier = Modifier
                    .wrapContentWidth()
                    .size(20.dp)
                    .align(Alignment.Center)
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = userName,
            modifier = Modifier.weight(1f),
            fontWeight = FontWeight.Bold,
            color = Color.White,
            fontSize = 20.sp,
        )
        Spacer(modifier = Modifier.width(8.dp))
        Image(
            painter = painterResource(id = R.drawable.bookmark),
            contentDescription = "bookmark",
            modifier = Modifier
                .wrapContentWidth()
                .size(40.dp)
                .padding(10.dp)
                .clickable(onClick = onClickBookmark),
            colorFilter = ColorFilter.tint(Color.White.copy(alpha = if (isBookmark) 0.3f else 1f))
        )
    }
}

@Composable
fun DetailBody(
    modifier: Modifier,
    imageUrl: String,
    imageWidth: Int,
    imageHeight: Int
) {
    val aspectRatio = try {
        imageWidth.toFloat() / imageHeight.toFloat()
    } catch (_: Exception) {
        1f
    }

    Box(modifier = modifier.padding(12.dp)) {
        SubcomposeAsyncImage(
            model = imageUrl,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(aspectRatio)
                .clip(RoundedCornerShape(10.dp))
                .align(Alignment.Center),
            contentDescription = "photo"
        )
    }
}

@Composable
fun DetailBottom(
    title: String,
    description: String,
    tags: ImmutableList<String>
) {
    Column(
        modifier = Modifier.padding(vertical = 8.dp, horizontal = 20.dp)
    ) {
        Text(
            text = title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = description,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Medium,
            fontSize = 15.sp,
            color = Color.White

        )
        Spacer(modifier = Modifier.height(8.dp))
        if (tags.isNotEmpty()) {
            Text(
                text = tags.joinToString(" ") { "#${it}" },
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Medium,
                fontSize = 15.sp,
                color = Color.White
            )
        }
    }
}