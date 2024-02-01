package com.myeong.prography.database

import app.cash.sqldelight.db.SqlDriver
import com.myeong.prography.database.photo.PhotoEntity
import com.myeong.prography.database.photo.PrographySqlDelightDatabase
import com.myeong.prography.domain.model.Photo
import com.myeong.prography.domain.model.PhotoImageUrl
import com.myeong.prography.domain.source.PhotoDataSource
import com.myeong.prography.domain.source.request.LoadPhotosOption
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Created by MyeongKi.
 */
class PhotoLocalDataSource(driver: SqlDriver) : PhotoDataSource {
    private val queries = PrographySqlDelightDatabase(driver).prographySqlDelightDatabaseQueries
    override fun loadPhotos(requestOption: LoadPhotosOption): Flow<List<Photo>> {
        throw UnsupportedOperationException("미지원")
    }

    override fun loadPhoto(photoId: String): Flow<Photo> {
        throw UnsupportedOperationException("미지원")
    }

    override fun addPhotoBookmark(photo: Photo): Flow<Photo> {
        return flow {
            queries.insertPhoto(
                id = photo.id,
                imageUrl = photo.imageUrl.small,
                imageWidth = photo.imageWidth.toLong(),
                imageHeight = photo.imageHeight.toLong()
            )
            emit(photo)
        }
    }

    override fun deletePhotoBookmark(photoId: String): Flow<String> {
        return flow {
            queries.deletePhoto(photoId)
            emit(photoId)
        }
    }

    override fun loadPhotoBookmarks(): Flow<List<Photo>> {
        return flow {
            emit(
                queries.selectAllPhotos().executeAsList().map { photoEntity: PhotoEntity ->
                    photoEntity.toPhoto()
                }
            )
        }
    }

    override fun loadPhotoBookmark(photoId: String): Flow<Photo?> {
        return flow {
            emit(queries.selectPhotoById(photoId).executeAsOneOrNull()?.toPhoto())
        }
    }

    private fun PhotoEntity.toPhoto():Photo{
        return Photo(
            id = id,
            userName = "",
            imageHeight = imageHeight.toInt(),
            imageWidth = imageWidth.toInt(),
            imageUrl = PhotoImageUrl(
                full = "",
                small = imageUrl
            ),
            title = "",
            description = "",
            tags = emptyList(),
            isBookmark = true
        )
    }
}