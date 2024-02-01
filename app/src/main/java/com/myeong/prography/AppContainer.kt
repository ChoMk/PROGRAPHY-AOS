package com.myeong.prography

import com.myeong.prography.ui.event.SheetEvent
import com.myeong.prography.network.HttpClientFactory
import com.myeong.prography.network.photo.PhotoHttpClient
import com.myeong.prography.network.photo.RemotePhotoDataSource
import kotlinx.coroutines.flow.MutableSharedFlow
import source.PhotoRepositoryImpl
import usecase.LoadPhotoDetailUseCase
import usecase.LoadPhotosUseCase

/**
 * Created by MyeongKi.
 */
object AppContainer {
    val visibleSheetFlow = MutableSharedFlow<SheetEvent>()

    private val photoRepository by lazy {
        PhotoRepositoryImpl(
            remoteSource = RemotePhotoDataSource(
                httpClient = PhotoHttpClient(
                    httpClient = HttpClientFactory.createUnsplashHttpClient()
                )
            )
        )
    }
    val loadPhotosUseCase by lazy {
        LoadPhotosUseCase(
            repository = photoRepository
        )
    }
    val loadPhotoDetailUseCase by lazy {
        LoadPhotoDetailUseCase(
            repository = photoRepository
        )
    }
}