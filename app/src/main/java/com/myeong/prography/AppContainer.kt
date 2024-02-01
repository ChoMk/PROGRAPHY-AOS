package com.myeong.prography

import com.myeong.prography.domain.model.Photo
import com.myeong.prography.domain.source.PhotoDataSource
import com.myeong.prography.domain.source.PhotoRepositoryImpl
import com.myeong.prography.domain.source.request.LoadPhotosOption
import com.myeong.prography.domain.usecase.AddPhotoBookmarkUseCase
import com.myeong.prography.domain.usecase.DeletePhotoBookmarkUseCase
import com.myeong.prography.domain.usecase.LoadPhotoDetailUseCase
import com.myeong.prography.domain.usecase.LoadPhotosUseCase
import com.myeong.prography.network.HttpClientFactory
import com.myeong.prography.network.photo.PhotoHttpClient
import com.myeong.prography.network.photo.RemotePhotoDataSource
import com.myeong.prography.ui.event.SheetEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

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
            ),
            localSource = object : PhotoDataSource {
                override fun loadPhotos(requestOption: LoadPhotosOption): Flow<List<Photo>> {
                    throw Exception()
                }

                override fun loadPhoto(photoId: String): Flow<Photo> {
                    throw Exception()
                }

                override fun addPhotoBookmark(photo: Photo): Flow<Photo> {
                    throw Exception()
                }

                override fun deletePhotoBookmark(photoId: String): Flow<String> {
                    throw Exception()
                }

                override fun loadPhotoBookmarks(): Flow<List<Photo>> {
                    throw Exception()
                }

                override fun loadPhotoBookmark(photoId: String): Flow<Photo> {
                    throw Exception()
                }
            }
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