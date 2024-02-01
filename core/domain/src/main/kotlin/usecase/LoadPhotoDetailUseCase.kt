package usecase

import kotlinx.coroutines.flow.Flow
import model.Photo
import source.PhotoRepository

/**
 * Created by MyeongKi.
 */
class LoadPhotoDetailUseCase(private val repository: PhotoRepository) {
    operator fun invoke(photoId: String): Flow<Photo> {
        return repository.loadPhoto(photoId)
    }
}