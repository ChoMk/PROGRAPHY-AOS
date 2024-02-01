package com.myeong.prography.domain.usecase

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import com.myeong.prography.domain.model.Photo
import com.myeong.prography.domain.source.PhotoRepository

/**
 * Created by MyeongKi.
 */
class LoadPhotosUseCase(private val repository: PhotoRepository) {
    operator fun invoke(): Flow<PagingData<Photo>> {
        return repository.loadPhotos()
    }
}