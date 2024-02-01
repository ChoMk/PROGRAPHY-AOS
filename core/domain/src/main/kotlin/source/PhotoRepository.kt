package source

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import model.Photo

/**
 * Created by MyeongKi.
 */
interface PhotoRepository {
    fun loadPhotos(): Flow<PagingData<Photo>>
    fun loadPhoto(photoId: String): Flow<Photo>
}