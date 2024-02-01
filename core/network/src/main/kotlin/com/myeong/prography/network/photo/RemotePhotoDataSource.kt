package com.myeong.prography.network.photo

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import model.Photo
import source.PhotoDataSource
import source.request.LoadPhotosOption

/**
 * Created by MyeongKi.
 */
class RemotePhotoDataSource(
    private val httpClient: PhotoHttpClient
) : PhotoDataSource {
    override fun loadPhotos(requestOption: LoadPhotosOption): Flow<List<Photo>> {
        return flow {
            val result = httpClient.requestPhotos(requestOption)
            emit(
                result.map {
                    it.toPhoto()
                }
            )
        }
    }

    override fun loadPhoto(photoId: String): Flow<Photo> {
        return flow {
            val result = httpClient.requestPhoto(photoId)
            emit(
                result.toPhoto()
            )
        }
    }
}