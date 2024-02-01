package com.myeong.prography.domain.usecase

import com.myeong.prography.domain.model.Photo
import com.myeong.prography.domain.source.PhotoRepository
import kotlinx.coroutines.flow.Flow

/**
 * Created by MyeongKi.
 */
class AddPhotoBookmarkUseCase(private val repository: PhotoRepository) {
    operator fun invoke(photo: Photo): Flow<Photo> {
        return repository.addPhotoBookmark(photo)
    }
}