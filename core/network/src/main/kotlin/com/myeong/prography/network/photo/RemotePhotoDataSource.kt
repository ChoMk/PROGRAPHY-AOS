package com.myeong.prography.network.photo

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import model.Photo
import model.PhotoImageUrl
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
                    Photo(
                        userName = it.user.username,
                        imageHeight = it.height,
                        imageWidth = it.width,
                        imageUrl = PhotoImageUrl(
                            full = it.urls.full,
                            small = it.urls.small
                        ),
                        description = it.description,
                        title = it.user.name
                    )
                }
            )
        }
    }
}