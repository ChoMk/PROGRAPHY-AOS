package com.myeong.prography.network.photo

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.myeong.prography.domain.model.Photo
import com.myeong.prography.domain.source.PhotoDataSource
import com.myeong.prography.domain.source.request.LoadPhotosOption
import java.lang.UnsupportedOperationException

/**
 * Created by MyeongKi.
 */
class PhotoRemoteDataSource(
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

    override fun addPhotoBookmark(photo: Photo): Flow<Photo> {
        throw UnsupportedOperationException("remote에서는 bookmark 미지원")
    }

    override fun deletePhotoBookmark(photoId: String): Flow<String> {
        throw UnsupportedOperationException("remote에서는 bookmark 미지원")
    }

    override fun loadPhotoBookmarks(): Flow<List<Photo>> {
        throw UnsupportedOperationException("remote에서는 bookmark 미지원")
    }

    override fun loadPhotoBookmark(photoId: String): Flow<Photo> {
        throw UnsupportedOperationException("remote에서는 bookmark 미지원")
    }

    override fun loadRandomPhotos(): Flow<List<Photo>> {
        return flow {
            val result = httpClient.requestRandomPhotos()
            emit(
                result.map {
                    it.toPhoto()
                }
            )
        }
    }
}