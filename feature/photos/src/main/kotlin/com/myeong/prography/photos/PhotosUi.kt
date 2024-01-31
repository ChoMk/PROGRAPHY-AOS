package com.myeong.prography.photos

import DefaultIndicator
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridScope
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.SubcomposeAsyncImage
import com.myeong.prography_aos.R
import kotlinx.coroutines.flow.Flow
import model.Photo

/**
 * Created by MyeongKi.
 */

@Composable
fun PhotosScreenRoute(viewModel: PhotosViewModel) {
    val pagingFlow = remember { viewModel.photosPagingFlow }
    PhotosScreen(photosPagingFlow = pagingFlow)
}

@Composable
fun PhotosScreen(
    photosPagingFlow: Flow<PagingData<Photo>>
) {
    Column {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 20.dp, vertical = 10.dp),
            text = "최신 이미지",
            fontSize = 20.sp,
            color = colorResource(id = R.color.title_color),
            fontWeight = FontWeight.Bold
        )
        PhotosView(photosPagingFlow)
    }
}

@Composable
fun PhotosView(
    photosPagingFlow: Flow<PagingData<Photo>>
) {
    val photosPagingItems: LazyPagingItems<Photo> = photosPagingFlow.collectAsLazyPagingItems()
    val listState = rememberLazyStaggeredGridState()
    LazyVerticalStaggeredGrid(
        state = listState,
        modifier = Modifier.padding(10.dp),
        columns = StaggeredGridCells.FixedSize(166.dp),
        verticalItemSpacing = 10.dp,
        horizontalArrangement = Arrangement.spacedBy(10.dp, alignment = Alignment.CenterHorizontally)
    ) {
        items(photosPagingItems.itemCount) { index ->
            photosPagingItems[index]?.let { photo ->
                GridPhotoItem(photo)
            }
        }
        photosPagingItems.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    (this@LazyVerticalStaggeredGrid).photoSkeleton()
                }

                loadState.append is LoadState.Loading -> {
                    item {
                        AppendLoading()
                    }
                }
            }
        }
    }
}

@Composable
fun GridPhotoItem(
    photo: Photo
) {
    val aspectRatio = photo.imageWidth.toFloat() / photo.imageHeight.toFloat()
    val imageSizeModifier = Modifier
        .fillMaxWidth()
        .aspectRatio(aspectRatio)
    Box(
        modifier = imageSizeModifier.clip(RoundedCornerShape(10.dp))
    ) {
        SubcomposeAsyncImage(
            model = photo.imageUrl.small,
            modifier = imageSizeModifier.clip(RoundedCornerShape(10.dp)),
            loading = {
                PhotoSkeletonItem(
                    imageSizeModifier
                )
            },
            contentDescription = "photo"
        )
        Text(
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.BottomStart),
            text = photo.description,
            maxLines = 2,
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
fun AppendLoading() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
    ) {
        DefaultIndicator(modifier = Modifier.align(Alignment.Center))
    }
}
