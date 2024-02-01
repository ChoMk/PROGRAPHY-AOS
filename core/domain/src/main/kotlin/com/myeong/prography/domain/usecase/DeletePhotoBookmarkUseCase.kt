package com.myeong.prography.domain.usecase

import com.myeong.prography.domain.source.PhotoRepository
import kotlinx.coroutines.flow.Flow

/**
 * Created by MyeongKi.
 */
class DeletePhotoBookmarkUseCase(private val repository: PhotoRepository) {
    operator fun invoke(photoId: String): Flow<String> {
        return repository.deletePhotoBookmark(photoId)
    }
}