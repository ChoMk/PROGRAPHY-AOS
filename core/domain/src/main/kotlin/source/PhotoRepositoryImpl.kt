package source

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import model.Photo

/**
 * Created by MyeongKi.
 */
class PhotoRepositoryImpl(
    private val remoteSource: PhotoDataSource,
) : PhotoRepository {

    override fun loadPhotos(): Flow<PagingData<Photo>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = {
                LoadPhotosComposPagingSource(remoteSource)
            }
        ).flow
    }
}