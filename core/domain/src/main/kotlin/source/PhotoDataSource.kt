package source

import kotlinx.coroutines.flow.Flow
import model.Photo
import source.request.LoadPhotosOption

/**
 * Created by MyeongKi.
 */
interface PhotoDataSource {
    fun loadPhotos(requestOption: LoadPhotosOption): Flow<List<Photo>>
}