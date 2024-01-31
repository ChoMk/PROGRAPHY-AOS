package usecase

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import model.Photo
import source.PhotoRepository

/**
 * Created by MyeongKi.
 */
class LoadPhotosUseCase(private val repository: PhotoRepository) {
    operator fun invoke(): Flow<PagingData<Photo>> {
        return repository.loadPhotos()
    }
}