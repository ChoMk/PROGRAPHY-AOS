package com.myeong.prography.domain.usecase

import com.myeong.prography.domain.model.Photo
import com.myeong.prography.domain.source.PhotoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.zip

/**
 * Created by MyeongKi.
 */
class LoadPhotoDetailUseCase(private val repository: PhotoRepository) {
    operator fun invoke(photoId: String): Flow<Photo> {
        return repository.loadPhoto(photoId)
            .zip(repository.loadPhotoBookmark(photoId)) { photo, photoBookmark ->
                photo.copy(
                    isBookmark = photoBookmark?.isBookmark ?: false
                )
            }
    }
}