package com.myeong.prography_aos

import com.myeong.prography.holder.sheet.SheetEvent
import com.myeong.prography.network.HttpClientFactory
import com.myeong.prography.network.photo.PhotoHttpClient
import com.myeong.prography.network.photo.RemotePhotoDataSource
import kotlinx.coroutines.flow.MutableSharedFlow
import source.PhotoRepositoryImpl
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
}