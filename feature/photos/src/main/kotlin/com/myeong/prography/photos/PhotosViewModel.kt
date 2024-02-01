package com.myeong.prography.photos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.myeong.prography.photos.PhotosIntent.Companion.toEvent
import com.myeong.prography.ui.event.SheetEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import model.Photo
import usecase.LoadPhotosUseCase

/**
 * Created by MyeongKi.
 */
class PhotosViewModel(
    visibleSheetFlow: MutableSharedFlow<SheetEvent>,
    loadPhotosUseCase: LoadPhotosUseCase
) : ViewModel() {
    val photosPagingFlow: Flow<PagingData<Photo>> = loadPhotosUseCase().distinctUntilChanged().cachedIn(viewModelScope)
    private val intentFlow = MutableSharedFlow<PhotosIntent>()
    val intentInvoker: (PhotosIntent) -> Unit = {
        viewModelScope.launch {
            intentFlow.emit(it)
        }
    }
    private val eventFlow = MutableSharedFlow<PhotosEvent>()
    val eventInvoker: (PhotosEvent) -> Unit = {
        viewModelScope.launch {
            eventFlow.emit(it)
        }
    }
    private val actionFlow = merge(
        eventFlow,
        intentFlow.map { it.toEvent() }
    )
    private val showDetailSheetFlow = actionFlow
        .filterIsInstance<PhotosEvent.ShowDetailSheet>()
        .onEach {
            visibleSheetFlow.emit(SheetEvent.ShowDetailSheet(it.photoId))
        }
    private val notifyOutsideFlow = merge(
        showDetailSheetFlow
    )

    init {
        notifyOutsideFlow.launchIn(viewModelScope)
    }

    companion object {
        fun provideFactory(
            visibleSheetFlow: MutableSharedFlow<SheetEvent>,
            loadPhotosUseCase: LoadPhotosUseCase,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return PhotosViewModel(
                    visibleSheetFlow,
                    loadPhotosUseCase
                ) as T
            }
        }
    }
}
