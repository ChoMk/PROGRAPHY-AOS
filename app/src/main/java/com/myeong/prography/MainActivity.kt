package com.myeong.prography

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.myeong.prography.detail.DetailRoute
import com.myeong.prography.detail.DetailViewModel
import com.myeong.prography.holder.HolderRoute
import com.myeong.prography.photos.PhotosScreenRoute
import com.myeong.prography.photos.PhotosViewModel
import com.myeong.prography.random.RandomRoute
import com.myeong.prography.random.RandomViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HolderRoute(
                visibleSheetFlow = AppContainer.visibleSheetFlow,
                photosContent = {
                    val viewModel: PhotosViewModel = viewModel(
                        factory = PhotosViewModel.provideFactory(
                            refreshBookmarkItemFlow = AppContainer.refreshBookmarkItemFlow,
                            visibleSheetFlow = AppContainer.visibleSheetFlow,
                            loadPhotosUseCase = AppContainer.loadPhotosUseCase,
                            loadPhotoBookmarkUseCase = AppContainer.loadPhotoBookmarksUseCase
                        )
                    )
                    PhotosScreenRoute(viewModel = viewModel)
                },
                randomContent = {
                    val viewModel: RandomViewModel = viewModel(
                        factory = RandomViewModel.provideFactory(
                            refreshBookmarkItemFlow = AppContainer.refreshBookmarkItemFlow,
                            visibleSheetFlow = AppContainer.visibleSheetFlow,
                            addPhotoBookmarkUseCase = AppContainer.addPhotoBookmarkUseCase,
                            loadRandomPhotosUseCase = AppContainer.loadRandomPhotosUseCase
                        )
                    )
                    RandomRoute(viewModel = viewModel)
                },
                detailSheet = { photoId ->
                    val viewModel: DetailViewModel = viewModel(
                        factory = DetailViewModel.provideFactory(
                            refreshBookmarkItemFlow = AppContainer.refreshBookmarkItemFlow,
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
