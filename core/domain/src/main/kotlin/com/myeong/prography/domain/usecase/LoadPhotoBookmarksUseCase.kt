package com.myeong.prography.domain.usecase

import com.myeong.prography.domain.model.Photo
import com.myeong.prography.domain.source.PhotoRepository
import kotlinx.coroutines.flow.Flow

/**
 * Created by MyeongKi.
 */
class LoadPhotoBookmarksUseCase(private val repository: PhotoRepository) {
    operator fun invoke(): Flow<List<Photo>> {
        return repository.loadPhotoBookmarks()
    }
}