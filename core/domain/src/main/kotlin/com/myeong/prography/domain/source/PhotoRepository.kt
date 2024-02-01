package com.myeong.prography.domain.source

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import com.myeong.prography.domain.model.Photo

/**
 * Created by MyeongKi.
 */
interface PhotoRepository {
    fun loadPhotos(): Flow<PagingData<Photo>>
    fun loadPhoto(photoId: String): Flow<Photo>
    fun addPhotoBookmark(photo: Photo): Flow<Photo>
    fun deletePhotoBookmark(photoId: String): Flow<String>
    fun loadPhotoBookmarks(): Flow<List<Photo>>
    fun loadPhotoBookmark(photoId: String): Flow<Photo>

}