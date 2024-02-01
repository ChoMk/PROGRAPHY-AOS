package com.myeong.prography.random

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.myeong.prography.domain.ResultData
import com.myeong.prography.domain.asResult
import com.myeong.prography.domain.event.RefreshBookmarkEvent
import com.myeong.prography.domain.model.Photo
import com.myeong.prography.domain.usecase.AddPhotoBookmarkUseCase
import com.myeong.prography.domain.usecase.LoadRandomPhotosUseCase
import com.myeong.prography.random.RandomIntent.Companion.toEvent
import com.myeong.prography.ui.event.SheetEvent
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch

/**
 * Created by MyeongKi.
 */
class RandomViewModel(
    private val refreshBookmarkItemFlow: MutableSharedFlow<RefreshBookmarkEvent>,
    visibleSheetFlow: MutableSharedFlow<SheetEvent>,
    private val addPhotoBookmarkUseCase: AddPhotoBookmarkUseCase,
    loadRandomPhotosUseCase: LoadRandomPhotosUseCase
) : ViewModel() {
    private val viewModelState = RandomViewModelState()
    private val intentFlow = MutableSharedFlow<RandomIntent>()
    val intentInvoker: (RandomIntent) -> Unit = {
        viewModelScope.launch {
            intentFlow.emit(it)
        }
    }
    private val eventFlow = MutableSharedFlow<RandomEvent>()
    val eventInvoker: (RandomEvent) -> Unit = {
        viewModelScope.launch {
            eventFlow.emit(it)
        }
    }
    private val actionFlow = merge(
        eventFlow,
        intentFlow.map { it.toEvent() }
    )
    private val loadRandomPhotosFlow = actionFlow
        .filterIsInstance<RandomEvent.LoadRandomPhotos>()
        .transform { emitAll(loadRandomPhotosUseCase().asResult()) }
        .map {
            when (it) {
                is ResultData.Loading, is ResultData.Error -> Unit
                is ResultData.Success -> viewModelState.randomPhotos.addAll(it.data)
            }
            viewModelState
        }

    private val popPhotoFlow = actionFlow
        .filterIsInstance<RandomEvent.PopPhoto>()
        .mapNotNull { viewModelState.randomPhotos.firstOrNull() }
        .map {
            if (viewModelState.randomPhotos.isNotEmpty()) {
                viewModelState.randomPhotos.removeAt(0)
            }
            viewModelState
        }
        .onEach {
            if (viewModelState.randomPhotos.size < 3) {
                eventInvoker(RandomEvent.LoadRandomPhotos)
            }
        }
    private val addBookmarkAndPopPhotoFlow = actionFlow
        .filterIsInstance<RandomEvent.AddBookmarkAndPopPhoto>()
        .mapNotNull { viewModelState.randomPhotos.firstOrNull() }
        .onEach {
            addBookmark(it)
        }
        .onEach {
            if (viewModelState.randomPhotos.size < 3) {
                eventInvoker(RandomEvent.LoadRandomPhotos)
            }
        }
    val uiState = merge(
        loadRandomPhotosFlow,
        popPhotoFlow
    )
        .map { it.toUiState() }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.toUiState()
        )
    private val showDetailSheetFlow = actionFlow
        .filterIsInstance<RandomEvent.ShowDetailSheet>()
        .mapNotNull { viewModelState.randomPhotos.firstOrNull() }
        .onEach {
            visibleSheetFlow.emit(SheetEvent.ShowDetailSheet(it.id))
        }
    private val notifyFlow = merge(
        showDetailSheetFlow,
        addBookmarkAndPopPhotoFlow
    )

    init {
        notifyFlow.launchIn(viewModelScope)
    }

    private fun addBookmark(photo: Photo) {
        addPhotoBookmarkUseCase(photo).asResult()
            .onEach {
                when (it) {
                    is ResultData.Success -> {
                        refreshBookmarkItemFlow.emit(RefreshBookmarkEvent.AddBookmark(photo))
                    }

                    else -> Unit
                }
            }
            .launchIn(viewModelScope)
    }

    private fun RandomViewModelState.toUiState(): RandomUiState {
        return RandomUiState(
            photos = this.randomPhotos.toImmutableList()
        )
    }

    companion object {
        fun provideFactory(
            refreshBookmarkItemFlow: MutableSharedFlow<RefreshBookmarkEvent>,
            visibleSheetFlow: MutableSharedFlow<SheetEvent>,
            addPhotoBookmarkUseCase: AddPhotoBookmarkUseCase,
            loadRandomPhotosUseCase: LoadRandomPhotosUseCase

        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return RandomViewModel(
                    refreshBookmarkItemFlow,
                    visibleSheetFlow,
                    addPhotoBookmarkUseCase,
                    loadRandomPhotosUseCase
                ) as T
            }
        }
    }
}


private data class RandomViewModelState(
    val randomPhotos: MutableList<Photo> = mutableListOf()
)