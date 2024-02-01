package com.myeong.prography.domain.source

import com.myeong.prography.domain.model.Photo
import com.myeong.prography.domain.source.request.LoadPhotosOption
import kotlinx.coroutines.flow.Flow

/**
 * Created by MyeongKi.
 */
interface PhotoDataSource {
    fun loadPhotos(requestOption: LoadPhotosOption): Flow<List<Photo>>

    fun loadPhoto(photoId: String): Flow<Photo>
    fun addPhotoBookmark(photo: Photo): Flow<Photo>
    fun deletePhotoBookmark(photoId: String): Flow<String>
    fun loadPhotoBookmarks(): Flow<List<Photo>>
    fun loadPhotoBookmark(photoId: String): Flow<Photo?>
}