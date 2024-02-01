package com.myeong.prography_aos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.myeong.prography.holder.HolderRoute
import com.myeong.prography.holder.sheet.SheetEvent
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
                            showDetailSheet = { photoId ->
                                AppContainer.visibleSheetFlow.emit(
                                    SheetEvent.ShowDetailSheet
                                )
                            },
                            loadPhotosUseCase = AppContainer.loadPhotosUseCase
                        )
                    )
                    PhotosScreenRoute(viewModel = viewModel)
                },
                randomContent = {
                    Text(text = "random page")
                },
                detailSheet = {
                    Spacer(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black)
                    )
                }
            )
        }
    }
}
