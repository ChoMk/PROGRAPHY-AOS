package com.myeong.prography_aos

import com.myeong.prography.network.HttpClientFactory
import com.myeong.prography.network.photo.PhotoHttpClient
import com.myeong.prography.network.photo.RemotePhotoDataSource
import source.PhotoRepositoryImpl
import usecase.LoadPhotosUseCase

/**
 * Created by MyeongKi.
 */
object AppContainer {
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