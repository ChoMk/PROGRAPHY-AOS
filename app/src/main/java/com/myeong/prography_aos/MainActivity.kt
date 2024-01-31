package com.myeong.prography_aos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.lifecycle.viewmodel.compose.viewModel
import com.myeong.prography.holder.HolderRoute
import com.myeong.prography.photos.PhotosScreenRoute
import com.myeong.prography.photos.PhotosViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HolderRoute(
                photosContent = {
                    val viewModel: PhotosViewModel = viewModel(
                        factory = PhotosViewModel.provideFactory(
                            loadPhotosUseCase = AppContainer.loadPhotosUseCase
                        )
                    )
                    PhotosScreenRoute(viewModel = viewModel)
                },
                randomContent = {
                    Text(text = "random page")
                }
            )
        }
    }
}
