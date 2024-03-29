package com.myeong.prography.domain.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.flow.last
import com.myeong.prography.domain.model.Photo
import com.myeong.prography.domain.source.request.LoadPhotosOption

/**
 * Created by MyeongKi.
 */
class LoadPhotosComposPagingSource(
    private val remoteDataSource: PhotoDataSource,
) : PagingSource<Int, Photo>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        try {
            val currentPage = params.key ?: START_PAGE
            val result = remoteDataSource.loadPhotos(
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

}