package com.myeong.prography

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.lifecycle.viewmodel.compose.viewModel
import com.myeong.prography.detail.DetailRoute
import com.myeong.prography.detail.DetailViewModel
import com.myeong.prography.holder.HolderRoute
import com.myeong.prography.photos.PhotosScreenRoute
import com.myeong.prography.photos.PhotosViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HolderRoute(
                visibleSheetFlow = AppContainer.visibleSheetFlow,
                photosContent = {
                    val viewModel: PhotosViewModel = viewModel(
                        factory = PhotosViewModel.provideFactory(
                            visibleSheetFlow = AppContainer.visibleSheetFlow,
                            loadPhotosUseCase = AppContainer.loadPhotosUseCase,
                            loadPhotoBookmarkUseCase = AppContainer.loadPhotoBookmarksUseCase
                        )
                    )
                    PhotosScreenRoute(viewModel = viewModel)
                },
                randomContent = {
                    Text(text = "random page")
                },
                detailSheet = { photoId ->
                    val viewModel: DetailViewModel = viewModel(
                        factory = DetailViewModel.provideFactory(
                            visibleSheetFlow = AppContainer.visibleSheetFlow,
                            loadPhotoDetailUseCase = AppContainer.loadPhotoDetailUseCase,
                            addPhotoBookmarkUseCase = AppContainer.addPhotoBookmarkUseCase,
                            deletePhotoBookmarkUseCase = AppContainer.deletePhotoBookmarkUseCase
                        )
                    )
                    DetailRoute(viewModel, photoId)
                }
            )
        }
    }
}
