package com.myeong.prography.photos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.myeong.prography.domain.ResultData
import com.myeong.prography.domain.asResult
import com.myeong.prography.domain.event.RefreshBookmarkEvent
import com.myeong.prography.domain.model.Photo
import com.myeong.prography.domain.usecase.LoadPhotoBookmarksUseCase
import com.myeong.prography.domain.usecase.LoadPhotosUseCase
import com.myeong.prography.photos.PhotosIntent.Companion.toEvent
import com.myeong.prography.ui.event.SheetEvent
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch

/**
 * Created by MyeongKi.
 */
class PhotosViewModel(
    refreshBookmarkItemFlow: Flow<RefreshBookmarkEvent>,
    visibleSheetFlow: MutableSharedFlow<SheetEvent>,
    loadPhotosUseCase: LoadPhotosUseCase,
    loadPhotoBookmarkUseCase: LoadPhotoBookmarksUseCase
) : ViewModel() {
    private val viewModelState = PhotosViewModelState()
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
        intentFlow.map { it.toEvent() },
        refreshBookmarkItemFlow.map {
            when (it) {
                is RefreshBookmarkEvent.DeleteBookmark -> {
                    PhotosEvent.DeleteBookmarkItem(it.photoId)
                }

                is RefreshBookmarkEvent.AddBookmark -> {
                    PhotosEvent.AddBookmarkItem(it.photo)

                }
            }
        }
    )
    private val showDetailSheetFlow = actionFlow
        .filterIsInstance<PhotosEvent.ShowDetailSheet>()
        .onEach {
            visibleSheetFlow.emit(SheetEvent.ShowDetailSheet(it.photoId))
        }
    private val loadPhotoBookmarkFlow = actionFlow
        .filterIsInstance<PhotosEvent.LoadPhotoBookmarks>()
        .transform { emitAll(loadPhotoBookmarkUseCase().asResult()) }
        .map {
            when (it) {
                is ResultData.Loading, is ResultData.Error -> {
                    viewModelState.bookmarks.clear()
                    viewModelState
                }

                is ResultData.Success -> {
                    viewModelState.bookmarks.clear()
                    viewModelState.bookmarks.addAll(it.data)
                    viewModelState
                }
            }
        }
    private val addBookmarkItemFlow = actionFlow
        .filterIsInstance<PhotosEvent.AddBookmarkItem>()
        .map {
            viewModelState.bookmarks.add(0, it.photo)
            viewModelState
        }
    private val deleteBookmarkItemFlow = actionFlow
        .filterIsInstance<PhotosEvent.DeleteBookmarkItem>()
        .map {event->
            val photoDeleted = viewModelState.bookmarks.find { it.id == event.photoId }
            viewModelState.bookmarks.remove(photoDeleted)
            viewModelState

        }
    val uiState = merge(
        loadPhotoBookmarkFlow,
        addBookmarkItemFlow,
        deleteBookmarkItemFlow
    )
        .map { it.toUiState() }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.toUiState()
        )

    private val notifyOutsideFlow = merge(
        showDetailSheetFlow
    )

    init {
        notifyOutsideFlow.launchIn(viewModelScope)
    }

    private fun PhotosViewModelState.toUiState(): PhotoBookmarksUiState {
        return PhotoBookmarksUiState(
            photos = this.bookmarks.toImmutableList()
        )
    }

    companion object {
        fun provideFactory(
            refreshBookmarkItemFlow: Flow<RefreshBookmarkEvent>,
            visibleSheetFlow: MutableSharedFlow<SheetEvent>,
            loadPhotosUseCase: LoadPhotosUseCase,
            loadPhotoBookmarkUseCase: LoadPhotoBookmarksUseCase

        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return PhotosViewModel(
                    refreshBookmarkItemFlow,
                    visibleSheetFlow,
                    loadPhotosUseCase,
                    loadPhotoBookmarkUseCase
                ) as T
            }
        }
    }
}

private data class PhotosViewModelState(
    val bookmarks: MutableList<Photo> = mutableListOf()
)