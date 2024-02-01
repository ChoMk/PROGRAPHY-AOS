package com.myeong.prography.photos

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridScope
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.SubcomposeAsyncImage
import com.myeong.prography.ui.R
import com.myeong.prography.domain.model.Photo
import com.myeong.prography.ui.DefaultIndicator
import kotlinx.coroutines.flow.Flow

/**
 * Created by MyeongKi.
 */

@Composable
fun PhotosScreenRoute(viewModel: PhotosViewModel) {
    val bookmarksUiState = viewModel.uiState.collectAsState().value
    val pagingFlow = remember { viewModel.photosPagingFlow }
    val intentInvoker = remember {
        viewModel.intentInvoker
    }
    val eventInvoker = remember {
        viewModel.eventInvoker
    }
    PhotosView(
        bookmarksUiState = bookmarksUiState,
        photosPagingFlow = pagingFlow,
        intentInvoker = intentInvoker,
        eventInvoker = eventInvoker
    )
}

@Composable
fun PhotosView(
    bookmarksUiState: PhotoBookmarksUiState,
    photosPagingFlow: Flow<PagingData<Photo>>,
    intentInvoker: (PhotosIntent) -> Unit,
    eventInvoker: (PhotosEvent) -> Unit

) {
    val photosPagingItems: LazyPagingItems<Photo> = photosPagingFlow.collectAsLazyPagingItems()
    val appendLoading = remember { mutableStateOf(false) }
    LaunchedEffect(true) {
        eventInvoker(PhotosEvent.LoadPhotoBookmarks)
    }
    LazyVerticalStaggeredGrid(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxHeight(),
        columns = StaggeredGridCells.FixedSize(166.dp),
        verticalItemSpacing = 10.dp,
        horizontalArrangement = Arrangement.spacedBy(10.dp, alignment = Alignment.CenterHorizontally)
    ) {
        if (bookmarksUiState.photos.isNotEmpty()) {
            item(span = StaggeredGridItemSpan.FullLine) {
                PhotosListTitle(title = "북마크")
            }
            item(span = StaggeredGridItemSpan.FullLine) {
                Row(
                    modifier = Modifier
                        .height(152.dp)
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    bookmarksUiState.photos.forEach { photo ->
                        val aspectRatio = photo.imageWidth.toFloat() / photo.imageHeight.toFloat()
                        PhotoItem(
                            modifier = Modifier
                                .fillMaxHeight()
                                .aspectRatio(aspectRatio),
                            imageUrl = photo.imageUrl.small,
                            description = photo.description
                        ) {
                            intentInvoker(PhotosIntent.ClickPhoto(photo))
                        }
                    }
                }
            }

        }
        item(span = StaggeredGridItemSpan.FullLine) {
            PhotosListTitle(title = "최신 이미지")
        }
        items(photosPagingItems.itemCount, key = { index ->
            photosPagingItems[index]?.id ?: -1
        }) { index ->
            photosPagingItems[index]?.let { photo ->
                val aspectRatio = photo.imageWidth.toFloat() / photo.imageHeight.toFloat()

                PhotoItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(aspectRatio),
                    imageUrl = photo.imageUrl.small,
                    description = photo.description
                ) {
                    intentInvoker(PhotosIntent.ClickPhoto(photo))
                }
            }
        }
        if (appendLoading.value) {
            item {
                AppendLoading(modifier = Modifier)
            }
        }
        photosPagingItems.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    (this@LazyVerticalStaggeredGrid).photoSkeleton()
                }

                loadState.append is LoadState.Loading -> {
                    appendLoading.value = true
                }

                loadState.append is LoadState.NotLoading -> {
                    appendLoading.value = false
                }
            }
        }
    }
}

@Composable
fun PhotosListTitle(title: String) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 10.dp),
        text = title,
        fontSize = 20.sp,
        color = colorResource(id = R.color.title_color),
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun PhotoItem(
    modifier: Modifier,
    imageUrl: String,
    description: String,
    onClickItem: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .clickable(onClick = onClickItem)
    ) {
        SubcomposeAsyncImage(
            model = imageUrl,
            modifier = modifier.clip(RoundedCornerShape(10.dp)),
            loading = {
                PhotoSkeletonItem(
                    modifier
                )
            },
            contentDescription = "photo"
        )
        Text(
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.BottomStart),
            text = description,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Medium,
            color = colorResource(id = R.color.desc_color)
        )
    }

}

fun LazyStaggeredGridScope.photoSkeleton() {
    repeat(8) {
        val height = when (it % 4) {
            0 -> {
                172.dp
            }

            1 -> {
                246.dp
            }

            2 -> {
                200.dp
            }

            else -> {
                185.dp
            }
        }
        item {
            PhotoSkeletonItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height)
            )
        }
    }
}

@Composable
fun PhotoSkeletonItem(
    modifier: Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        color = colorResource(id = R.color.item_background)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            DefaultIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}

@Composable
fun AppendLoading(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(Color.White)
    ) {
        DefaultIndicator(modifier = Modifier.align(Alignment.Center))
    }
}
