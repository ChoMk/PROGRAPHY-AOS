package com.myeong.prography

import com.myeong.prography.database.PhotoLocalDataSource
import com.myeong.prography.database.provideDatabaseDriver
import com.myeong.prography.domain.source.PhotoRepositoryImpl
import com.myeong.prography.domain.usecase.AddPhotoBookmarkUseCase
import com.myeong.prography.domain.usecase.DeletePhotoBookmarkUseCase
import com.myeong.prography.domain.usecase.LoadPhotoDetailUseCase
import com.myeong.prography.domain.usecase.LoadPhotosUseCase
import com.myeong.prography.network.HttpClientFactory
import com.myeong.prography.network.photo.PhotoHttpClient
import com.myeong.prography.network.photo.PhotoRemoteDataSource
import com.myeong.prography.ui.event.SheetEvent
import kotlinx.coroutines.flow.MutableSharedFlow

/**
 * Created by MyeongKi.
 */
object AppContainer {
    val visibleSheetFlow = MutableSharedFlow<SheetEvent>()
    private val photoRepository by lazy {
        PhotoRepositoryImpl(
            remoteSource = PhotoRemoteDataSource(
                httpClient = PhotoHttpClient(
                    httpClient = HttpClientFactory.createUnsplashHttpClient()
                )
            ),
            localSource = PhotoLocalDataSource(
                driver = provideDatabaseDriver(PrographyApplication.applicationContext())
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
    val addPhotoBookmarkUseCase by lazy {
        AddPhotoBookmarkUseCase(
            repository = photoRepository
        )
    }
    val deletePhotoBookmarkUseCase by lazy {
        DeletePhotoBookmarkUseCase(
            repository = photoRepository
        )
    }
}