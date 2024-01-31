package source

import kotlinx.coroutines.flow.Flow
import model.Photo
import source.request.LoadPhotosOption

/**
 * Created by MyeongKi.
 */
class PhotoRepositoryImpl(
    private val remoteSource: PhotoDataSource,
    private val localSource: PhotoDataSource
) : PhotoRepository {

    override fun loadPhotos(requestOption: LoadPhotosOption): Flow<List<Photo>> {
        return remoteSource.loadPhotos(requestOption)
    }
}