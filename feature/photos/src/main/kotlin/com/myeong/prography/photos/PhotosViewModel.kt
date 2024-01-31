package com.myeong.prography.photos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import model.Photo
import usecase.LoadPhotosUseCase

/**
 * Created by MyeongKi.
 */
class PhotosViewModel(
    loadPhotosUseCase: LoadPhotosUseCase
) : ViewModel() {
    val photosPagingFlow: Flow<PagingData<Photo>> = loadPhotosUseCase().distinctUntilChanged().cachedIn(viewModelScope)

    companion object {
        fun provideFactory(
            loadPhotosUseCase: LoadPhotosUseCase,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return PhotosViewModel(
                    loadPhotosUseCase
                ) as T
            }
        }
    }
}
