package com.myeong.prography.domain.source

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import com.myeong.prography.domain.model.Photo

/**
 * Created by MyeongKi.
 */
class PhotoRepositoryImpl(
    private val remoteSource: PhotoDataSource,
    private val localSource: PhotoDataSource
) : PhotoRepository {

    override fun loadPhotos(): Flow<PagingData<Photo>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = {
                LoadPhotosComposPagingSource(remoteSource)
            }
        ).flow
    }

    override fun loadPhoto(photoId: String): Flow<Photo> {
        return remoteSource.loadPhoto(photoId)
    }

    override fun addPhotoBookmark(photo: Photo): Flow<Photo> {
        return localSource.addPhotoBookmark(photo)
    }

    override fun deletePhotoBookmark(photoId: String): Flow<String> {
        return localSource.deletePhotoBookmark(photoId)
    }

    override fun loadPhotoBookmarks(): Flow<List<Photo>> {
        return localSource.loadPhotoBookmarks()
    }
    override fun loadPhotoBookmark(photoId: String): Flow<Photo?> {
        return localSource.loadPhotoBookmark(photoId)
    }
}