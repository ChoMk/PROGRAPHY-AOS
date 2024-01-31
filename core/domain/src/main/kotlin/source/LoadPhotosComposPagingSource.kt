package source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.flow.last
import model.Photo
import source.request.LoadPhotosOption

/**
 * Created by MyeongKi.
 */
class LoadPhotosComposPagingSource(
    private val photoRepository: PhotoRepository,
) : PagingSource<Int, Photo>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        try {
            val currentPage = params.key ?: START_PAGE
            val result = photoRepository.loadPhotos(
                requestOption = LoadPhotosOption(
                    page = currentPage,
                    pageSize = PAGE_SIZE
                )
            ).last()
            return if (result.isNotEmpty()) {
                LoadResult.Page(
                    data = result,
                    prevKey = if (currentPage == START_PAGE) null else currentPage.dec(),
                    nextKey = currentPage.inc()
                )
            } else {
                LoadResult.Invalid()
            }
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Photo>): Int {
        return START_PAGE
    }

    companion object {
        private const val START_PAGE = 1
        private const val PAGE_SIZE = 10
    }
}